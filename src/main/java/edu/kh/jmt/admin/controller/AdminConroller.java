package edu.kh.jmt.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	@GetMapping("restaurant/regist")
	public String restaurantRegist() {
		
		return "admin/restaurantRegist";
	}
	
	
	@GetMapping("restaurant/manage")
	public String restaurantManage() {
		
		return "admin/restaurantManage";
	}
	
	/**
	 * 가게 정보 등록
	 * @param insertRestaurant
	 * @return
	 */
	@PostMapping("restaurant/insert")
	public String restaurantInsert(
			@ModelAttribute Restaurant insertRestaurant,
			@RequestParam("restaurantImages") List<MultipartFile> restaurantImages,
			@RequestParam("menuName") List<String> menuNamelist,
			@RequestParam("menuPrice") List<String> menuPricelist
			) {
		
//		log.debug(" insertRestaurant : {}", insertRestaurant);
//		log.debug(" menuNamelist : {}", menuNamelist);
//		log.debug(" menuPricelist : {}", menuPricelist);
		
		// 입력된 메뉴 정보를 저장할 list 객체 생성
		List<Map<String, String>> menuList = new ArrayList<Map<String, String>>();
		
		// 메뉴의 수 만큼 메뉴 정보 menuList 에 삽입
		for (int i = 0; i < menuNamelist.size(); i++) {
			menuList.add(Map.of(menuNamelist.get(i), menuPricelist.get(i)));
		}
		
//		log.debug(" menuList : {}", menuList);
		
		int result = service.restaurantInsert(insertRestaurant, restaurantImages, menuList);
		
		return "admin/restaurantManage";
	}
}
