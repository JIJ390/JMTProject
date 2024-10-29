package edu.kh.jmt.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.admin.service.AdminService;
import edu.kh.jmt.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	
	private final AdminService service;
	
	@GetMapping("")
	public String adminLoginView() {
		return "admin/adminLogin";
	}
	
	@PostMapping("login")
	public String adminLogin (		
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberPw")    String memberPw,
			Model model,
			RedirectAttributes ra) {
		
		
		Member loginMember = service.adminLogin(memberEmail, memberPw);
		
		String path = null;
		
		if (loginMember == null) {
			ra.addFlashAttribute("message", "이메일 또는 비밀번호가 일치하지 않습니다");
			path = "redirect:/admin";
			
		} else {	
			model.addAttribute("loginMember", loginMember);
			path = "redirect:/admin/member/manage";
		}
		
		return path;

	}
}
