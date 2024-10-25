package edu.kh.jmt.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.jmt.myPage.dto.Member;


@Mapper
public interface AdminMemberMapper {


	/** 
	* 검색 조건에 맞는 회원 정보 모두 조회
	* @param condition 
	* @param rowBounds 
	* @return
	*/
	List<Member> selectMemberList(Map<String, String> condition, RowBounds rowBounds);
	
	
	
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
	* 회원 탈퇴 상태 변경
	* @param memberNo
	* @return
	*/
	int changeMemberSecession(int memberNo);
	
	
	/**
	* 검색 조건에 맞는 총 회원 수 조회
	* @param condition
	* @return
	*/
	int getMemberSearchCount(Map<String, String> condition);
	
	
	/**
	* 임시 로그인 삭제 예정
	* @param memberNo
	* @return
	*/
	Member directLogin(int memberNo);
	
	
	




}
