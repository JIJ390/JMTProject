package edu.kh.jmt.admin.service;

import java.io.File;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.mapper.AdminMapper;
import edu.kh.jmt.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;
	
	
	@Value("${my.restaurant.web-path}") // springframework 로 import
	private String restaurantWebPath; 
	
	@Value("${my.restaurant.folder-path}") 
	private String restaurantFolderPath; 
	
	
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
	
	
	
	@Override
	public int restaurantInsert(
			Restaurant insertRestaurant, 
			List<MultipartFile> restaurantImages,
			List<String> menuNameList,
			List<String> menuPriceList) {
		
		// 파일 업로드 확인!!!!
		if (restaurantImages.isEmpty()) {
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
	
	
	
	// 회원 정보 모두 조회
	@Override
	public List<Member> selectMemberList() {
		return mapper.selectMemberList();
	}
	
	
	@Override
	public Map<String, String> selectMemberStatus() {
		return mapper.selectMemberStatus();
	}
}
