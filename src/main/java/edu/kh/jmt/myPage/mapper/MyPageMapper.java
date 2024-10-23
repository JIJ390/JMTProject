package edu.kh.jmt.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.jmt.myPage.dto.Mypage;

@Mapper
public interface MyPageMapper {

	/** loginEmail이 일치하는 회원 정보 조회
	 * @param loginEmail
	 * @return loginMember or null
	 */
	Mypage login(String loginEmail);

	
	/** 회원 가입 기능
	 * @param inputMember
	 * @return result
	 */
	int signUp(Mypage inputMember);


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
	 * @param memberNo
	 * @param encPw
	 * @return result
	 */
	int passwordChange(
			@Param("memberNo") int memberNo, 
			@Param("encPw") String encPw);


	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 */
	int withdrawal(int memberNo);


	/** 이름 변경
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Mypage inputMember);
}
