package edu.kh.jmt.admin.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.jmt.admin.service.AdminMemberService;
import edu.kh.jmt.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin/member")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
@Slf4j
public class AdminMemberController {
	
	private final AdminMemberService service;

	
	///////////////////////////////////////////////////////
	/**
	* 회원 관리 페이지 연결
	* @return
	*/
	@GetMapping("manage")
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
	
	
	//log.debug("condition : {}", condition);
	
	
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
	
	//log.debug("map : {}", map);
	
	return map;
	}
	
	
	/**
	* 회원 차단 여부 변경
	* @param memberNo
	* @return
	*/
	@PutMapping("changeBlock")
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
	@PutMapping("secession")
	@ResponseBody
	public int changeMemberSecession (
	@RequestBody int memberNo) {
	
	
	return service.changeMemberSecession(memberNo);
	}
	
	
	/**
	 * 차단만 가능 복구 불가
	 * @param memberNo
	 * @return
	 */
	@PutMapping("block")
	@ResponseBody
	public int memberBlock(
	@RequestBody int memberNo) {
		
	int i = 	service.memberBlock(memberNo);
	
	log.debug("aaaa : {}", i);
	log.debug("aaaa : {}", i);
	log.debug("aaaa : {}", i);
	log.debug("aaaa : {}", i);
	
	
	return service.memberBlock(memberNo);
	
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
	
	//log.debug("member : {}", loginMember);
	
	// @SessionAttributes({"loginMember"})
	// 로그인된 회원 정보를 session 에 추가
	model.addAttribute("loginMember", loginMember);
	
	return "redirect:/admin/member/manage";
	}

	
}
