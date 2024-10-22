package edu.kh.jmt.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.admin.dto.Member;
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
	@PostMapping("selectMemberList")
	@ResponseBody
	public Map<String, Object> selectMemberList(
		@RequestBody Map<String, String> condition) {
		
		
		log.debug("condition : {}", condition);
		
		// 페이지 네이션 관련 클래스 생성 후 cp 사용 예정
		
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
		
		log.debug("member : {}", loginMember);
		
		// @SessionAttributes({"loginMember"})
		// 로그인된 회원 정보를 session 에 추가
		model.addAttribute("loginMember", loginMember);
		
		return "redirect:/admin/member/manage";
	}
	
}
