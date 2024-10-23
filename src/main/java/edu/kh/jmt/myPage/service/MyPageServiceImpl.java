package edu.kh.jmt.myPage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.jmt.admin.dto.Member;
import edu.kh.jmt.myPage.dto.Mypage;
import edu.kh.jmt.myPage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MyPageServiceImpl implements MyPageService{
	
	@Autowired
	private MyPageMapper mapper;
	
	@Autowired // BCrypt 암호화 객체 의존성 주입 받기
	private BCryptPasswordEncoder encoder;
	

	// 로그인 기능
	@Override
		public Mypage login(String loginEmail, String loginPW) {
			
		// 암호화 테스트
//		log.debug("loginPw : {}", loginPW);
//		log.debug("암호화된 loginPw : {}", encoder.encode(loginPW));
		
		// 1. loginEmail이 일치하는 회원의 정보를 DB에서 조회(비밀번호 포함!)
		Mypage loginMember = mapper.login(loginEmail);
		
		// 2. 이메일이 일치하는 회원 정보가 없을 경우
		if(loginMember == null) return null;
		
		
		
		// 3. DB에서 조회된 비밀번호와, 입력 받은 비밀번호가 같은지 확인
//		log.debug("비밀번호 일치? : {}", encoder.matches(loginPW, loginMember.getMemberPw()));
		
		// 입력 받은 비밀번호와 DB에서 조회된 비밀번호가 일치하지 않는다면
		if(!encoder.matches(loginPW, loginMember.getMemberPw()) ) {
			return null;
		}
		
		// 4. 로그인 결과 반환
		return loginMember;
		
		}
	
	
	// 회원 가입 기능
	@Override
	public int signUp(Mypage inputMember) {
		
		// 비밀번호 암호화(BCrypt)
		String encPw = encoder.encode(inputMember.getMemberPw());
		log.debug("원본 : {}", inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		log.debug("암호 : {}", encPw);
		
		return mapper.signUp(inputMember);
	}
	
	
	// 이메일 중복 검사
	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}
	

	// 이름 중복 검사
	@Override
	public int nameCheck(String memberName) {
		return mapper.nameCheck(memberName);
	}
	

	// 비밀번호 변경
	@Override
	public int passwordChange(String currentPw, String newPw, Mypage loginMember) {
		
		if(encoder.matches(currentPw, loginMember.getMemberPw()) == false) { // 비밀번호가 일치하지 않을때
			return 0;
		} 
		
		String encPw = encoder.encode(newPw);
		
		loginMember.setMemberPw(encPw);
		return mapper.passwordChange(loginMember.getMemberNo(), encPw);
	}
	

	// 회원 탈퇴 기능
	@Override
	public int withdrawal(String memberPw, Mypage loginMember) {
	// 1) 비밀번호 일치 검사
		if(encoder.matches(memberPw, loginMember.getMemberPw()) == false) {
			return 0; // 다를 경우 0 반환
		}
		
		// 2) 회원 탈퇴 Mapper 호출(update)
		return mapper.withdrawal(loginMember.getMemberNo());
		}
	
	
	// 이름 수정 기능
	@Override
	public int updateInfo(Mypage inputMember) {
		return mapper.updateInfo(inputMember);
	}
	
}
