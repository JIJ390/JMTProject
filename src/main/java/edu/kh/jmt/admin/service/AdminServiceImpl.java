package edu.kh.jmt.admin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.mapper.AdminMapper;
import edu.kh.jmt.myPage.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final AdminMapper mapper;
	private final BCryptPasswordEncoder encoder;	
	
	// 관리자 로그인
	@Override
	public Member adminLogin(String memberEmail, String memberPw) {
		
		//    (비밀번호 포함!)
		Member loginMember = mapper.adminLogin(memberEmail);
		
		if (loginMember == null) {
			return null;
		}
		
		if (!encoder.matches(memberPw, loginMember.getMemberPw())) {
			// 비밀 번호 불일치 시
			return null;
		}
		
		// 4 로그인 성공 / 결과 반환
		return loginMember;
	}
}
