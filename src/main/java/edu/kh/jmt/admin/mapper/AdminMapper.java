package edu.kh.jmt.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.jmt.myPage.dto.Member;

@Mapper
public interface AdminMapper {

	/**
	 * 로그인 회원 정보 가져오기
	 * @param memberEmail
	 * @return
	 */
	Member adminLogin(String memberEmail);
	
	
	
}
