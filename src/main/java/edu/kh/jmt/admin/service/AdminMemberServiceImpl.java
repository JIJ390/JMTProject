package edu.kh.jmt.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.AdminPagination;
import edu.kh.jmt.admin.mapper.AdminMemberMapper;
import edu.kh.jmt.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService{
	
	private final AdminMemberMapper mapper;


	// 회원 정보 모두 조회
	@Override
	public Map<String, Object> selectMemberList(Map<String, String> condition) {
		
		int cp = Integer.parseInt(condition.get("cp")); 
		
		// 검색 조건에 맞는 회원 수 조회
		int searchCount = mapper.getMemberSearchCount(condition);
//		log.debug("count : {}", searchCount);
		
		// 페이지네이션 객체 생성
		AdminPagination adminPagination = new AdminPagination(cp, searchCount);
		
		int limit = adminPagination.getLimit();  // limit  : 한 페이지에 보여질 게시글의 최대 개수
		int offset = (cp - 1) * limit;			// offset : 몇 개의 게시글을 건너뛰고 조회할 건지에 대한 값
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		
		List<Member> memberList = mapper.selectMemberList(condition, rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put(("memberList"), memberList);
		map.put(("pagination"), adminPagination);		
		
		return map;
	}
	
	
	// 회원 현황 조회
	@Override
	public Map<String, String> selectMemberStatus() {
		return mapper.selectMemberStatus();
	}
	
	// 회원 차단 여부 변경
	@Override
	public int changeMemberBlock(int memberNo) {
		return mapper.changeMemberBlock(memberNo);
	}
	
	
	// 회원 탈퇴 여부 변경
	@Override
	public int changeMemberSecession(int memberNo) {
		return mapper.changeMemberSecession(memberNo);
	}
	
	// 회원 차단 기능
	@Override
	public int memberBlock(int memberNo) {
		
		return mapper.memberBlock(memberNo);
	}
	
	
	// 임시로그인
	@Override
	public Member directLogin(int memberNo) {
		return mapper.directLogin(memberNo);
	}
}
