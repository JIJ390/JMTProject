package edu.kh.jmt.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.myPage.service.MyPageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
	 * @param saveEamil : 이메일 저장 여부
	 * @param resp : 응답 방법을 제공하는 객체
	 * @return
	 */
	@PostMapping("loginPage")
	public String login(
			@RequestParam("loginEmail") String loginEmail,
			@RequestParam("loginPW") String loginPW,
			@RequestParam(name ="saveEmail", required = false) String saveEmail,
			RedirectAttributes ra,
			Model model,
			HttpServletResponse resp
			) {
		
//		log.debug("loginEmail : {}", loginEmail);
//		log.debug("loginPW : {}", loginPW);
		
		Member loginMember = service.login(loginEmail, loginPW);
		
		
		if(loginMember == null) { // 로그인 실패
			ra.addFlashAttribute("message", "이메일 또는 비밀번호가 일치하지 않습니다");
			return "redirect:/myPage/loginPage";
		} else { // 로그인 성공
			model.addAttribute("loginMember", loginMember);
			// 이메일 저장 기능
			Cookie cookie = new Cookie("saveEmail", loginEmail);
			
			cookie.setPath("/");
			
			if(saveEmail == null) { // 체크가 안되어있을때
				cookie.setMaxAge(0);
			} else { // 체크가 되었을때
				cookie.setMaxAge(60*60*24*30); // 30일 유지
			}
			resp.addCookie(cookie);
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
			@ModelAttribute Member inputMember,
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
	
	
	/** 이름 중복 검사 (비동기)
	 * @param memberName
	 * @return 0 : 중복 X, 1 : 중복 O
	 */
	@ResponseBody
	@GetMapping("nameCheck")
	public int nameCheck(
			@RequestParam("memberName")  String memberName) {
		
		
		return service.nameCheck(memberName);
	}
	
	/** 비밀번호 변경 페이지 호출
	 * @return
	 */
	@GetMapping("passwordChange")
	public String pwChange() {
		
		return "myPage/passwordChange";
	}
	
	
	/** 비밀번호 변경 기능
	 * @param currentPw : 현재 비밀번호
	 * @param newPw : 새로운 비밀번호
	 * @param loginMember : 세션에서 얻어온 로그인 회원 정보
	 * @param ra : 리다이렉트 시 request scope로 데이터 전달하는 객체
	 * @return result
	 */
	@PostMapping("passwordChange")
	public String passwordChange(
			@RequestParam("currentPw") String currentPw,
			@RequestParam("newPw") String newPw,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra){
		
		int result = service.passwordChange(currentPw, newPw, loginMember);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "비밀번호가 변경 되었습니다";
			path = "myPage"; // 마이페이지로 리다이렉트
		} else {
			message = "현재 비밀번호가 일치하지 않습니다";
			path = "passwordChange"; // 비밀번호 변경페이지로 리다이렉트
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	/** 비밀번호 찾기 페이지 호출
	 * @return
	 */
	@GetMapping("pwFind")
	public String pwFind() {
		
		return "myPage/pwFind";
	}
	
	
	/** 비밀번호 찾기
	 * @param memberNo
	 * @return
	 */
	
	
	
	/** 마이페이지 페이지 호출
	 * @return
	 */
	@GetMapping("myPage")
	public String myPage() {
		
		return "myPage/myPage";
	}
	
	
	/** 회원 탈퇴 페이지 호출
	 * @return
	 */
	@GetMapping("withdrawal")
	public String withdrawal() {
		
		return "myPage/withdrawal";
	}
	
	
	/** 회원 탈퇴 기능
	 * @param memberPw
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("withdrawal")
	public String withdrawal(
			@RequestParam("memberPw") String memberPw,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			SessionStatus status){
		
		int result = service.withdrawal(memberPw, loginMember);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			message = "탈퇴 되었습니다";
			path = "/";
			status.setComplete(); // 세션 만료 -> 로그아웃
		} else {
			message = "비밀번호가 일치하지 않습니다";
			path = "withdrawal";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
		
	
	/** 내 정보 수정 페이지 불러오기
	 * @return
	 */
	@GetMapping("updateInfo")
	public String updateInfo() {
		return "myPage/updateInfo";
	}
	
	/** 이름 수정 기능
	 * @param inputMember : 수정할 이름 주소
	 * @param loginMember : 현재 로그인된 회원 정보
	 * @param ra
	 * @return
	 */
	@PostMapping("updateInfo")
	public String updateInfo(
			@RequestParam("updateProfileImg") MultipartFile profileImg,
			@ModelAttribute Member inputMember,
			@SessionAttribute ("loginMember") Member loginMember,
			RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		String filePath = service.updateInfo(inputMember, profileImg);
		
		if (filePath != null) {
			// DB, Session에 저장된 프로필 이미지 정보 동기화
			loginMember.setProfileImg(filePath);
		}
		
		// 세션에 이름 동기화
		loginMember.setMemberName(inputMember.getMemberName());;
		
		String message = null;
		

		
		ra.addFlashAttribute("message", message);
		
		
		
		
		return "redirect:myPage";
	}
	
	
		
	}

