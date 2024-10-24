package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Menu;
import edu.kh.jmt.admin.dto.Pagination;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.service.AdminRestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin/restaurant")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
@Slf4j
public class AdminRestaurantConroller {
	
	private final AdminRestaurantService service;

	/**
	 * 가게 등록 페이지 연결
	 * @return
	 */
	@GetMapping("regist")
	public String restaurantRegist() {
		
		return "admin/restaurantRegist";
	}
	
	

	/**
	 * 가게 관리 페이지 연결
	 * @param cp			 : 현재 페이지 번호
	 * @param model		 : 가게 리스트 담을 객체 
	 * @param paramMap : 검색 조건
	 * @return
	 */
	@GetMapping("")
	public String restaurantManage(
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap) {
		
		// 페이지 네이션, 가게 정보 묶어서 담을 객체
		Map<String, Object> map = null;
		
		
		if (paramMap.get("key") == null) {	
			map = service.selectRestaurantList(cp);
			
		} else { // 검색한 경우
			
			// paramMap 에 key, query 담겨 있음
			map = service.restaurantSearchList(cp, paramMap);
		}
		
//		log.debug("map : {}", map);
		
		List<Restaurant> restaurantList = (List<Restaurant>)map.get("restaurantList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		
		model.addAttribute("restaurantList", restaurantList);
		model.addAttribute("pagination", pagination);
		
		return "admin/restaurantManage";
	}
	
	/**
	 * 가게 정보 등록 버튼 클릭
	 * @param insertRestaurant
	 * @return 
	 */
	@PostMapping("insert")
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
		
		return "redirect:/admin/restaurant";
	}
	
	
	/**
	 * 가게 정보 상세 조회 (팝업)
	 * @param restaurantNo
	 * @return
	 */
	@GetMapping("{restaurantNo:[0-9]+}")
	@ResponseBody
	public Map<String, Object> restaurantDetail(
			@PathVariable("restaurantNo") int restaurantNo)	 {
		
		Map<String, Object> map = service.restaurantDetail(restaurantNo);
		
		log.debug("detailmap : {}", map);
		
		return map;
		
	}
	
	
	/**
	 * 가게 삭제(폐점)
	 * @param restaurantNo
	 * @param referer
	 * @return
	 */
	@PostMapping("delete")
	public String restaurantDelete(
			@RequestParam("restaurantNo") int restaurantNo
			) {
		
		int result = service.restaurantDelete(restaurantNo);
		
		return "redirect:/admin/restaurant";
	}
	
	
	
	@PostMapping("updateView")
	public String restaurantUpdateView(
			@RequestParam("restaurantNo") int restaurantNo,
			Model model
			) {
		
		Map<String, Object> map = service.restaurantUpdateView(restaurantNo);
		
		Restaurant restaurant = (Restaurant) map.get("restaurant");
		List<Menu> menuList = (List<Menu>) map.get("menuList");
		
		String[] arr = restaurant.getRestaurantAddress().split(",");
		
		// 가게 주소 잘라내기
		model.addAttribute("postcode"     , arr[0]);
		model.addAttribute("address"      , arr[1]);
		model.addAttribute("detailAddress", arr[2]);
			
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("menuList", menuList);
		
		return "admin/restaurantUpdate";
	}
	
	
	/**
	 * 가게 정보 수정
	 * @param restaurantNo
	 * @param insertRestaurant
	 * @param restaurantImages	: 변화 없을 시 null 이 담김
	 * @param menuNameList
	 * @param menuPriceList
	 * @return
	 */
	@PostMapping("update")
	public String restaurantUpdate(
			@ModelAttribute Restaurant restaurant,
			@RequestParam(value="restaurantImages", required = false, defaultValue = "1") List<MultipartFile> restaurantImages,
			@RequestParam("menuName") List<String> menuNameList,
			@RequestParam("menuPrice") List<String> menuPriceList) {
		
		int result = service.restaurantUpdate(restaurant, restaurantImages, menuNameList, menuPriceList);
		
		return "redirect:/admin/restaurant";
		
	}

}
