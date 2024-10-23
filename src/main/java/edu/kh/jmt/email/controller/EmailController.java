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
