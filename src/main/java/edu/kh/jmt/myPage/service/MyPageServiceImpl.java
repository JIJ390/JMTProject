package edu.kh.jmt.myPage.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.jmt.common.util.FileUtil;
import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.myPage.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{
	
	@Autowired
	private MyPageMapper mapper;
	
	@Autowired // BCrypt 암호화 객체 의존성 주입 받기
	private BCryptPasswordEncoder encoder;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // 웹 접근 경로
	
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // 이미지 저장 서버 경로

	// 로그인 기능
	@Override
		public Member login(String loginEmail, String loginPW) {
			
		// 암호화 테스트
//		log.debug("loginPw : {}", loginPW);
//		log.debug("암호화된 loginPw : {}", encoder.encode(loginPW));
		
		// 1. loginEmail이 일치하는 회원의 정보를 DB에서 조회(비밀번호 포함!)
		Member loginMember = mapper.login(loginEmail);
		
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
	public int signUp(Member inputMember) {
		
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
	public int passwordChange(String currentPw, String newPw, Member loginMember) {
		
		if(encoder.matches(currentPw, loginMember.getMemberPw()) == false) { // 비밀번호가 일치하지 않을때
			return 0;
		} 
		
		String encPw = encoder.encode(newPw);
		
		loginMember.setMemberPw(encPw);
		return mapper.passwordChange(loginMember.getMemberNo(), encPw);
	}
	
	

	// 회원 탈퇴 기능
	@Override
	public int withdrawal(String memberPw, Member loginMember) {
	// 1) 비밀번호 일치 검사
		if(encoder.matches(memberPw, loginMember.getMemberPw()) == false) {
			return 0; // 다를 경우 0 반환
		}
		
		// 2) 회원 탈퇴 Mapper 호출(update)
		return mapper.withdrawal(loginMember.getMemberNo());
		}
	
	
	// 이름/프로필 이미지 수정 기능
	@Override
	public String updateInfo(Member inputMember, MultipartFile profileImg) {
		
//		UUID uuid = UUID.randomUUID();
//		
//		//1. 저장할 파일 이름 얻기
//		String fileName = uuid.toString();
//		
//		//1.1 파일이름에 서버 경로  같이 넣어서 꺼내올때 쉽게 하기
//		String Path = null;
		
		int result = 0;
		
		// 1) 파일 업로드 확인
		if(profileImg.isEmpty()) {
			
			// 제출된 파일이 없음 
			// 이름만 변경
			result =  mapper.updateInfo(inputMember);
			
			return null;
			}
		
		// 파일명 변경
		String rename = FileUtil.rename(profileImg.getOriginalFilename());
		
		String url = profileWebPath + rename;
		
		inputMember.setProfileImg(url);
		
		result =  mapper.updateInfo(inputMember);
		//3. 파일 저장하기
		//3.1 저장할 경로 가져오기
		//3.2 경로에 저장하기
		try {
			// C:/uploadFiles/profile/ 폴더가 없으면 생성
			File folder = new File(profileFolderPath);
			if(!folder.exists()) folder.mkdirs();
			
			
			// 업로드되어 임시 저장된 이미지를 지정된 경로에 옮기기
			profileImg.transferTo(new File(profileFolderPath + rename));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("프로필 이미지 수정 실패");
		}
		
		return url;
		
		
				
		//4. 저장한 파일 이름 (1.) 회원 정보에 UPDATE 하기
		
		// 끋
	}
	
	
	
}
