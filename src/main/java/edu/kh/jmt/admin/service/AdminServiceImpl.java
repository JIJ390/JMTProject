package edu.kh.jmt.admin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Member;
import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Pagination;
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
	
	/////////////////////////////////////////////////////////////////////////
	//회원 관리 페이지
	
	// 회원 정보 모두 조회
	@Override
	public Map<String, Object> selectMemberList(Map<String, String> condition) {
		
		int cp = Integer.parseInt(condition.get("cp")); 
		
		// 검색 조건에 맞는 회원 수 조회
		int searchCount = mapper.getSearchCount(condition);
//		log.debug("count : {}", searchCount);
		
		// 페이지네이션 객체 생성
		Pagination pagination = new Pagination(cp, searchCount);
		
		int limit = pagination.getLimit();  // limit  : 한 페이지에 보여질 게시글의 최대 개수
		int offset = (cp - 1) * limit;			// offset : 몇 개의 게시글을 건너뛰고 조회할 건지에 대한 값
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Member> memberList = mapper.selectMemberList(condition, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("memberList"), memberList);
		map.put(("pagination"), pagination);		
		
		return map;
	}
	
	
	// 회원 현황 조회
	@Override
	public Map<String, String> selectMemberStatus() {
		return mapper.selectMemberStatus();
	}
	
	// 회원 차단 여부 변경
	@Override
	public int changeMemberBlock(int memberNo) {
		return mapper.changeMemberBlock(memberNo);
	}
	
	
	// 회원 탈퇴 여부 변경
	@Override
	public int changeMemberSecession(int memberNo) {
		return mapper.changeMemberSecession(memberNo);
	}
	
	
	// 임시로그인
	@Override
	public Member directLogin(int memberNo) {
		return mapper.directLogin(memberNo);
	}
}
