package edu.kh.jmt.admin.service;

import java.util.Map;

import edu.kh.jmt.myPage.dto.Member;


public interface AdminMemberService {

	
	/////////////////////////////////////////////////////
	
	/**
	 * 회원 정보 모두 불러오기
	 * @param condition 
	 * @return
	 */
	Map<String, Object> selectMemberList(Map<String, String> condition);

	
	/**
	 * 회원 현황 조회
	 * @return
	 */
	Map<String, String> selectMemberStatus();

	
	
	/**
	 * 회원 차단 여부 변경
	 * @param memberNo
	 * @return
	 */
	int changeMemberBlock(int memberNo);

	
	/**
	 * 회원 탈퇴 여부 변경
	 * @param memberNo
	 * @return
	 */
	int changeMemberSecession(int memberNo);


	/**
	 * 회원 차단 기능
	 * @param memberNo
	 * @return
	 */
	int memberBlock(int memberNo);

	
	
	/**
	 * 임시 로그인
	 * @param memberNo
	 * @return
	 */
	Member directLogin(int memberNo);



}
