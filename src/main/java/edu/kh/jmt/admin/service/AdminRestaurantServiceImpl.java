package edu.kh.jmt.admin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.mapper.AdminRestaurantMapper;
import edu.kh.jmt.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:/config.properties")
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminRestaurantServiceImpl implements AdminRestaurantService{

	private final AdminRestaurantMapper mapper;
	
	
	@Value("${my.restaurant.web-path}") // springframework 로 import
	private String restaurantWebPath; 
	
	@Value("${my.restaurant.folder-path}") 
	private String restaurantFolderPath; 
	
	
	
	////////////////////////////////////////////////
	// 가게 관리 관련 메서드
	
	// 카테고리 리스트 불러오기
	@Override
	public List<Map<String, String>> selectCategoryList() {
		return mapper.selectCategoryList();
	}
	
	// 로케이션 리스트 불러오기
	@Override
	public List<Map<String, String>> selectLocationList() {
		return mapper.selectLocationList();
	}
	
	
	// 검색 아닐 때 가게 정보 가져오기
	@Override
	public Map<String, Object> selectRestaurantList(int cp) {
		
		// 폐업하지 않은 가게 수 조회
		int restaurantListCount = mapper.getRestaurantListCount();
		
		AdminPagination adminPagination = new AdminPagination(cp, restaurantListCount);
		
		int limit = adminPagination.getLimit(); 	// 10
		int offset = (cp - 1) * limit;			// 0
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Restaurant> restaurantList = mapper.selectRestaurantList(rowBounds);
		
		// 4. 목록 조회 결과 + Pagination 객체 Map 으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		map.put(("restaurantList"), restaurantList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	
	@Override
	public Map<String, Object> restaurantSearchList(int cp, Map<String, Object> paramMap) {
	// 1. 지정된 게시판에서 검색 조건이 일치하는 게시글이 
			//    몇 개나 존재하는지 조회
		
			log.debug("aaa : {}", paramMap);
		
			int searchCount = mapper.getSearchCount(paramMap);

			
			// 2. Pagination 객체 생성하기
			AdminPagination adminPagination = new AdminPagination(cp, searchCount);
			
			
			//3. DB에서 cp(조회 하려는 페이지)에 해당하는 행을 조회
			int limit = adminPagination.getLimit(); // 10
			int offset = (cp - 1) * limit;
			RowBounds rowBounds = new RowBounds(offset, limit);
			
			
			// 4. 검색 결과 + Pagination 객체 Map 으로 묶어서 반환
			List<Restaurant> restaurantList = mapper.restaurantSearchList(paramMap, rowBounds);
			
			Map<String, Object> map = new HashMap<>();
			map.put(("restaurantList"), restaurantList);
			map.put(("pagination"), adminPagination);		// 3 줄
			
			return map;
	}
	
	
	// 가게 등록
	@Override
	public int restaurantInsert(
			Restaurant insertRestaurant, 
			List<MultipartFile> restaurantImages,
			List<String> menuNameList,
			List<String> menuPriceList) {
		
		
		// 파일 업로드 확인!!!!
		if (restaurantImages.get(0).isEmpty() || restaurantImages.get(1).isEmpty()) {
			System.out.println("파일 업로드 오류");
			return 0;
		}
		
		// 파일명 변경
		String rename1 = FileUtil.rename(restaurantImages.get(0).getOriginalFilename());
		String rename2 = FileUtil.rename(restaurantImages.get(1).getOriginalFilename());
		
		// 웹 접근 경로(config.properties) + 변경된 파일명 준비
		String url1 = restaurantWebPath + rename1;
		String url2 = restaurantWebPath + rename2;
		
		// insertRestaurant 에 images 세팅
		insertRestaurant.setRestaurantImg1(url1);
		insertRestaurant.setRestaurantImg2(url2);
		
		log.debug("insertRestaurant : {} ", insertRestaurant);
		
		
		// 4) DB UPDATE
		int result1 = mapper.restaurantInsert(insertRestaurant);
		
		int restaurantNo = insertRestaurant.getRestaurantNo();
		
		// 입력된 메뉴 정보를 저장할 list 객체 생성
		List<Menu> menuList = new ArrayList<Menu>();
		
		// 메뉴의 수 만큼 메뉴 정보 menuList 에 삽입
		for (int i = 0; i < menuNameList.size(); i++) {
			
			Menu menu = Menu.builder()
											.menuName(menuNameList.get(i))
											.menuPrice(menuPriceList.get(i))
											.restaurantNo(restaurantNo)
											.build();
			
			log.debug("menu : {}", menu.toString());

			menuList.add(menu);
		}
		
		int result2 = mapper.menuListInsert(menuList);
		
		try {
			// C:/uploadFiles/restaurantImg/  폴더가 없으면 생성
			File folder = new File(restaurantFolderPath);
			if(!folder.exists()) folder.mkdirs();
			
			// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
			restaurantImages.get(0).transferTo(
					new File(restaurantFolderPath + rename1));
			
			restaurantImages.get(1).transferTo(
					new File(restaurantFolderPath + rename2));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("가게 정보 삽입 실패");
		}
		
		
		return 0;
	}
	
	
	
	
	@Override
	public Map<String, Object> restaurantDetail(int restaurantNo) {
		
		// 번호가 일치하는 가게 정보 가져오기
		Restaurant restaurant = mapper.selectRestaurant(restaurantNo);
		
		// 번호가 일치하는 메뉴리스트 가져오기
		List<Menu> menuList = mapper.selectMenuList(restaurantNo);
		
		// 가져온 정보 map 으로 묶기
		Map<String, Object> map = Map.of(
					"restaurant", restaurant,
					"menuList", menuList);
		
		return map;
	}
	
	
	
	// 가게 삭제
	@Override
	public int restaurantDelete(int restaurantNo) {
		return mapper.restaurantDelete(restaurantNo);
	}
	
	
	// 가게 수정화면용 가게 정보 조회
	@Override
	public Map<String, Object> restaurantUpdateView(int restaurantNo) {
		// 번호가 일치하는 가게 정보 가져오기
		Restaurant restaurant = mapper.restaurantUpdateView(restaurantNo);
		
		// 번호가 일치하는 메뉴리스트 가져오기
		List<Menu> menuList = mapper.menuUpdateView(restaurantNo);
		
		// 가져온 정보 map 으로 묶기
		Map<String, Object> map = Map.of(
					"restaurant", restaurant,
					"menuList", menuList);
		
		return map;
	}

	
	
	// 가게 정보 수정
	@Override
	public int restaurantUpdate(
			Restaurant restaurant, 
			List<MultipartFile> restaurantImages, 
			List<String> menuNameList,
	    List<String> menuPriceList) {
		
			// 가게 번호 저장
			int restaurantNo = restaurant.getRestaurantNo();

			String rename1 = null;
			String rename2 = null;
			
			// 첫번째 파일 업로드된
			if (!restaurantImages.get(0).isEmpty()) {
				rename1 = FileUtil.rename(restaurantImages.get(0).getOriginalFilename());
				String url1 = restaurantWebPath + rename1;
				restaurant.setRestaurantImg1(url1);
			}
			
			// 두번째 파일 업로드된 경우
			if (!restaurantImages.get(1).isEmpty()) {
				rename2 = FileUtil.rename(restaurantImages.get(1).getOriginalFilename());
				String url2 = restaurantWebPath + rename2;
				restaurant.setRestaurantImg2(url2);
			}
		

			log.debug("restaurantUpdate : {} ", restaurant);
			
			
			// 4) DB UPDATE
			int result1 = mapper.restaurantUpdate(restaurant);
			
			// 입력된 메뉴 정보를 저장할 list 객체 생성
			List<Menu> menuList = new ArrayList<Menu>();
			
			// 메뉴의 수 만큼 메뉴 정보 menuList 에 삽입
			for (int i = 0; i < menuNameList.size(); i++) {
				
				Menu menu = Menu.builder()
												.menuName(menuNameList.get(i))
												.menuPrice(menuPriceList.get(i))
												.restaurantNo(restaurantNo)
												.build();
				
				log.debug("menu : {}", menu.toString());

				menuList.add(menu);
			}
			
			// 기존 메뉴 정보 삭제
			int result2 = mapper.menuListDelete(restaurantNo);
			
			// 새 메뉴리스트 세팅
			int result3 = mapper.menuListInsert(menuList);
			
			try {
				// C:/uploadFiles/restaurantImg/  폴더가 없으면 생성
				File folder = new File(restaurantFolderPath);
				
				if(!folder.exists()) folder.mkdirs();
				
				// 업로드되어 임시저장된 이미지를 지정된 경로에 옮기기
				// 해당 이미지가 업로드된 경우에만
				if (!restaurantImages.get(0).isEmpty()) {
						restaurantImages.get(0).transferTo(
							new File(restaurantFolderPath + rename1));
					}

				if (!restaurantImages.get(1).isEmpty()) {
						restaurantImages.get(1).transferTo(
							new File(restaurantFolderPath + rename2));
					}
				

			} catch (Exception e) {
				e.printStackTrace();
				throw new Error("가게 정보 수정 실패");
			}
			

		return result3;
		
	}
}
