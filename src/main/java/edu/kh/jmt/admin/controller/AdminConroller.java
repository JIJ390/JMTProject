package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Member;
import edu.kh.jmt.admin.dto.Pagination;
import edu.kh.jmt.admin.dto.Restaurant;
import edu.kh.jmt.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@SessionAttributes("loginMember")
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
	
	

	/**
	 * 가게 관리 페이지 연결
	 * @param cp			 : 현재 페이지 번호
	 * @param model		 : 가게 리스트 담을 객체 
	 * @param paramMap : 검색 조건
	 * @return
	 */
	@GetMapping("restaurant")
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
		
		return "redirect:/admin/restaurant";
	}
	
	
	@GetMapping("restaurant/{restaurantNo:[0-9]+}")
	@ResponseBody
	public Map<String, Object> restaurantDetail(
			@PathVariable("restaurantNo") int restaurantNo)	 {
		
		Map<String, Object> map = service.restaurantDetail(restaurantNo);
		
		log.debug("detailmap : {}", map);
		
		return map;
		
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
	 * 비동기로 회원 정보 모두 가져오기
	 * @param condition	: 검색 조건 담긴 객체
	 * @param model
	 * @return
	 */
	@PostMapping("selectMemberList")
	@ResponseBody
	public Map<String, Object> selectMemberList(
		@RequestBody Map<String, String> condition) {
		
		
//		log.debug("condition : {}", condition);
		
		
		return service.selectMemberList(condition);
	}

	
	/**
	 * 회원 현황 
	 * @return
	 */
	@GetMapping("selectMemberStatus")
	@ResponseBody
	public Map<String, String> selectMemberStatus() {

		
		
		Map<String, String> map = service.selectMemberStatus();
		
//		log.debug("map : {}", map);
		
		return map;
	}
	
	
	/**
	 * 회원 차단 여부 변경
	 * @param memberNo
	 * @return
	 */
	@PutMapping("memberBlock")
	@ResponseBody
	public int changeMemberBlock(
			@RequestBody int memberNo) {
		
		return service.changeMemberBlock(memberNo);
		
	}
	
	
	/**
	 * 회원 탈퇴 상태 변경
	 * @param memberNo
	 * @return
	 */
	@PutMapping("memberSecession")
	@ResponseBody
	public int changeMemberSecession (
			@RequestBody int memberNo) {
		
		
		return service.changeMemberSecession(memberNo);
	}
	
	
	
	/**
	 * 다이렉트 로그인//// 임시 삭제 예정
	 * @param memberNo
	 * @param model
	 * @return
	 */
	@PostMapping("directLogin")
	public String directLogin(
			@RequestParam("memberNo") int memberNo,
			Model model
			) {
		Member loginMember = service.directLogin(memberNo);
		
//		log.debug("member : {}", loginMember);
		
		// @SessionAttributes({"loginMember"})
		// 로그인된 회원 정보를 session 에 추가
		model.addAttribute("loginMember", loginMember);
		
		return "redirect:/admin/member/manage";
	}
	
}
