package edu.kh.jmt.myPage.service;

import edu.kh.jmt.myPage.dto.Member;

public interface MyPageService{

	/** 로그인 기능
	 * @param loginEmail
	 * @param loginPW
	 * @return loginMember 또는 null(email, Pw 틀릴경우)
	 */
	Member login(String loginEmail, String loginPW);

	/** 회원 가입 기능
	 * @param inputMember
	 * @return result
	 */
	int signUp(Member inputMember);

	
	/** 이메일 중복 검사
	 * @param email
	 * @return count
	 */
	int emailCheck(String email);

	/** 이름 중복 검사
	 * @param memberName
	 * @return count
	 */
	int nameCheck(String memberName);

	
	/** 비밀번호 변경
	 * @param currentPw
	 * @param newPw
	 * @param loginMember
	 * @return result
	 */
	int passwordChange(String currentPw, String newPw, Member loginMember);

	
	/** 회원 탈퇴 기능
	 * @param memberPw
	 * @param loginMember
	 * @return
	 */
	int withdrawal(String memberPw, Member loginMember);

	/** 이름 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

}
