package edu.kh.jmt.admin.service;

import edu.kh.jmt.myPage.dto.Member;

public interface AdminService {

	/**
	 * 관리자 계정 로그인 확인
	 * @param memberEmail
	 * @param memberPw
	 * @return
	 */
	Member adminLogin(String memberEmail, String memberPw);

}
