package edu.kh.jmt.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.myPage.dto.Mypage;
import lombok.RequiredArgsConstructor;

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

}
