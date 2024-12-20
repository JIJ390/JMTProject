package edu.kh.jmt.myPage.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;

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

	/** 비밀번호 찾기
	 * @param inputEmail
	 * @return
	 */
	
	/** 회원 탈퇴 기능
	 * @param memberPw
	 * @param loginMember
	 * @return
	 */
	int withdrawal(String memberPw, Member loginMember);

	/** 이름/이미지 변경
	 * @param inputMember
	 * @return result
	 */
	String updateInfo(Member inputMember, MultipartFile profileImg);

	/**
	 * 찜 목록
	 * @param cp
	 * @param memberNo
	 * @return
	 */
	Map<String, Object> selectLikeList(int cp, int memberNo);

	




}
