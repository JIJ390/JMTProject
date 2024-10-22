package edu.kh.jmt.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.myPage.dto.Mypage;
import edu.kh.jmt.myPage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {
	
	private final MyPageService service;
	
	/** 로그인 페이지로 이동
	 */
	@GetMapping("loginPage")
	public String login() {
		return "myPage/loginPage";
	}
	
	
	/** 로그인 기능
	 * @param loginEmail : 제출된 이메일
	 * @param loginPW : 제출된 비밀번호
	 * @param loginPW : 리다이렉트 시 request scope로 값 전달
	 * @param model : 데이터 전달용 객체 (기본값 : request scope)
	 * @return
	 */
	@PostMapping("loginPage")
	public String login(
			@RequestParam("loginEmail") String loginEmail,
			@RequestParam("loginPW") String loginPW,
			RedirectAttributes ra,
			Model model
			) {
		
//		log.debug("loginEmail : {}", loginEmail);
//		log.debug("loginPW : {}", loginPW);
		
		Mypage loginMember = service.login(loginEmail, loginPW);
		
		
		if(loginMember == null) { // 로그인 실패
			ra.addFlashAttribute("message", "이메일 또는 비밀번호가 일치하지 않습니다");
			return "redirect:/myPage/loginPage";
		} else { // 로그인 성공
			model.addAttribute("loginMember", loginMember);
			
		}
		
		
		return "redirect:/";
	}
	
	
	/** 회원가입 페이지로 이동
	 * 
	 */
	@GetMapping("signUp")
	public String signUp() {
	return "myPage/signUp";
	}
	
	/** 회원 가입
	 * @param inputMember : 입력값이 저장된 mypage 객체(커맨드 객체)
	 * @param ra : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(
			@ModelAttribute Mypage inputMember,
			RedirectAttributes ra) {
		int result = service.signUp(inputMember);
		
		// 서비스 결과에 따른 응답 제어
		String path = null;
		String message = null;
		
		if(result > 0){
			path = "/";
			message = inputMember.getMemberName() + "님의 가입을 환영합니다";
		} else {
			path = "signUp";
			message = "회원 가입 실패";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
		
	}
	
	
	/** 이메일 중복 검사 (비동기)
	 * @param eamil : 입력된 이메일
	 * @return 0 : 중복 X, 1: 중복 O
	 */
	@ResponseBody
	@GetMapping("emailCheck")
	public int emailCheck(
			@RequestParam("email") String email) {
		return service.emailCheck(email);
	}
	
	
	
		
	}

