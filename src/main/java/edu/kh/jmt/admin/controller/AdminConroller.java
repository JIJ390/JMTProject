package edu.kh.jmt.admin.controller;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.model.Model;
import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@Slf4j
public class AdminConroller {
	
	private final AdminService service;

	/**
	 * 가게 등록 페이지 연결
	 * @return
	 */
	@GetMapping("restaurant/regist")
	public String restaurantRegist() {
		
		return "admin/restaurantRegist";
	}
	
	
	@GetMapping("restaurant/manage")
	public String restaurantManage(
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
			Model model) {
		
		
		return "admin/restaurantManage";
	}
	
	/**
	 * 가게 정보 등록 버튼 클릭
	 * @param insertRestaurant
	 * @return 
	 */
	@PostMapping("restaurant/insert")
	public String restaurantInsert(
			@ModelAttribute Restaurant insertRestaurant,
			@RequestParam("restaurantImages") List<MultipartFile> restaurantImages,
			@RequestParam("menuName") List<String> menuNameList,
			@RequestParam("menuPrice") List<String> menuPriceList
			) {
		
//		log.debug(" insertRestaurant : {}", insertRestaurant);
//		log.debug(" menuNamelist : {}", menuNamelist);
//		log.debug(" menuPricelist : {}", menuPricelist);
		
		int result = service.restaurantInsert(insertRestaurant, restaurantImages, menuNameList, menuPriceList);
		
		return "admin/restaurantManage";
	}
	
	
	
	///////////////////////////////////////////////////////
	/**
	 * 회원 관리 페이지 연결
	 * @return
	 */
	@GetMapping("member/manage")
	public String memberManage() {
		
		return "admin/memberManage";
	}
	
	
	/**
	 * 추후 검색 조건 추가해야 함!!
	 * 비동기로 회원 정보 모두 가져오기
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("/selectMemberList")
	@ResponseBody
	public List<Member> selectMemberList(
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model) {
		
		// 페이지 네이션 관련 클래스 생성 후 cp 사용 예정
		
		return service.selectMemberList();
	}

}
