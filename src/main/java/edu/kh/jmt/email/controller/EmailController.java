package edu.kh.jmt.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.jmt.common.util.RedisUtil;
import edu.kh.jmt.email.service.EmailService;

@Controller
@RequestMapping("email")
public class EmailController {

	@Autowired
	public RedisUtil reditUtil;
	
	@Autowired
	public EmailService service;
	
	
	
	/** 인증 번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공 1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey")
	public int sendAuthKey(
			@RequestBody String email) {
		
		return service.sendEmail("signUp", email);
	}
	
	/** 인증 번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공 1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey2")
	public int sendAuthKey2(
			@RequestBody String email) {
		
		return service.sendEmail("pwFind", email);
	}
	
	/** 임시 비밀번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공 1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey3")
	public String findPw(
			@RequestBody String email,
			RedirectAttributes ra) {
		
		int findPw = service.findPw("tempPw", email);

		String message = null;
		String path = null;
		
		if(findPw == 1) {
			message = "해당 이메일을 가진 사용자를 찾을 수 없습니다.";
			path = "pwFind";
		} else {
			message = "해당 이메일로 임시비밀번호를 발송드렸습니다";
			path = "loginPage";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	/** 인증 번호확인
	 * @param map 입력받은 값이 저장된 map
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public boolean checkAuthKey(
			@RequestBody Map<String, String> map) {
		
		return service.checkAuthKey(map);
	}
	
	
	
	
}
