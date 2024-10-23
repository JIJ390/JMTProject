package edu.kh.jmt.email.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송 서비스
	 * @param htmlName
	 * @param email
	 * @return
	 */
	int sendEmail(String htmlName, String email);

	
	/** 인증번호 확인
	 * @param map
	 * @return 
	 */
	boolean checkAuthKey(Map<String, String> map);

}
