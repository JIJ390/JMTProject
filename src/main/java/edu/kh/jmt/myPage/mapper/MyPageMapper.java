package edu.kh.jmt.myPage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;


import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.restaurant.dto.RestaurantDto;

@Mapper
public interface MyPageMapper {

	/** loginEmail이 일치하는 회원 정보 조회
	 * @param loginEmail
	 * @return loginMember or null
	 */
	Member login(String loginEmail);

	
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


	/** 이름/이미지 변경
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

  int findMemberNoByEmail(String email); // 이메일로 회원 번호 조회
  
  int updatePassword(
  		@Param("memberNo") int memberNo, 
  		@Param("encPw") String encPw); // 비밀번호 업데이트


  
  /**
   * 전체 찜 목록 개수 조회
   * @param memberNo 
   * @return
   */
	int getLikeListCount(int memberNo);


	/**
	 * 전체 찜목록 조회하기
	 * @param memberNo 
	 * @param rowBounds
	 * @return
	 */
	List<RestaurantDto> selectLikeList(int memberNo, RowBounds rowBounds);




}
