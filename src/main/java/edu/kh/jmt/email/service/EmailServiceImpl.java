package edu.kh.jmt.email.service;

import java.util.Map;
import java.util.Random;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.jmt.common.util.FileUtil;
import edu.kh.jmt.common.util.RedisUtil;
import edu.kh.jmt.myPage.dto.Member;
import edu.kh.jmt.myPage.mapper.MyPageMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * 이메일 관련 비즈니스 로직 처리 클래스
 */
@RequiredArgsConstructor
@Service

public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	private final RedisUtil redisUtil; 
	private final SpringTemplateEngine templateEngine;
	
	private final BCryptPasswordEncoder encoder; // BCrypt 암호화 객체 의존성 주입 받기
	
	private final MyPageMapper mapper;
	
	// 이메일 발송
	@Override
	public int sendEmail(String htmlName, String email) {
		
		try {
			
			String emailTitle = null; // 발송되는 이메일 제목
			String authKey = createAuthKey(); // 생성된 인증 번호
			
			switch(htmlName) {
			case "signUp" :
				emailTitle = "[JMT] 회원 가입 인증번호 입니다.";
				break;
				
			case "pwFind" :
				emailTitle = "[JMT] 비밀번호 찾기 인증번호 입니다.";
				break;
			}
			
			/* 메일 발송 */
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(email); // 받는 사람 이메일 세팅
			helper.setSubject(emailTitle); // 이메일 제목 세팅
			
			helper.setText(loadHtml(authKey, htmlName), true); // 이메일 내용 세팅

			// CID(Content-ID)를 이용해 메일에 이미지 첨부
			helper.addInline("logo", new ClassPathResource("static/images/logo.png"));
			
			// 메일 발송하기
			mailSender.send(mimeMessage);
			
			// 이메일, 인증번호 저장 (5분 후 만료)
			redisUtil.setValue(email, authKey, 60 * 5);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 예외 발생 == 실패 == 0 반환
		}
		
		
		return 1; // 성공 == 1 반환
	}
	
	/** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
   * @return authKey
   */
  public String createAuthKey() {
  	String key = "";
    for(int i=0 ; i< 6 ; i++) {
        
      int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
      
      if(sel1 == 0) {
        
        int num = (int)(Math.random() * 10); // 0~9
        key += num;
          
      }else {
        
        char ch = (char)(Math.random() * 26 + 65); // A~Z
        
        int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자
        
        if(sel2 == 0) {
            ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
        }
        
        key += ch;
      }
    }
    return key;
  }
	
  
	// HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
	public String loadHtml(String authKey, String htmlName) {
		
		// org.tyhmeleaf.Context 선택!!
		Context context = new Context();
		
		//타임리프가 적용된 HTML에서 사용할 값 추가
		context.setVariable("authKey", authKey);
		
		// templates/email 폴더에서 htmlName과 같은 
		// .html 파일 내용을 읽어와 String으로 변환
		return templateEngine.process("email/" + htmlName, context);
		
	}
	
	
	// 인증 번호 확인
	@Override
	public boolean checkAuthKey(Map<String, String> map) {
		
		// map에 저장된 값 꺼내오기
		String email = map.get("email");
		String authKey = map.get("authKey");
		
		// Redis에 key가 입력된 email과 같은 데이터가 있는지 확인
		if(redisUtil.hasKey(email) == false ) { // 없을 경우
			return false;
		}
		
		// Redis에 같은 key가 있다면 value를 얻어와 입력 받은 인증 번호와 비교
		
		return redisUtil.getValue(email).equals(authKey);
	}
	
	
	// 임시 비밀번호 생성 (영어 대문자 + 소문자 + 특수문자+ 숫자 8자리)
	public String tempPw() {
    String key = "";
    String specialChars = "!@#$%&";
    Random random = new Random();

    // 총 8자리로 설정
    for (int i = 0; i < 8; i++) {
        int sel1 = random.nextInt(4); // 0: 숫자 / 1: 대문자 / 2: 소문자 / 3: 특수기호

        if (sel1 == 0) { // 숫자
            int num = random.nextInt(10); // 0~9
            key += num;
        } else if (sel1 == 1) { // 대문자
            char upper = (char) (random.nextInt(26) + 'A'); // A~Z
            key += upper;
        } else if (sel1 == 2) { // 소문자
            char lower = (char) (random.nextInt(26) + 'a'); // a~z
            key += lower;
        } else { // 특수기호
            char special = specialChars.charAt(random.nextInt(specialChars.length()));
            key += special;
        }
    }
    return key;
	}
	
	
		//임시 비밀번호 발송
		@Override
		public int findPw(String htmlName, String email) {
	    try {
	        // 이메일 제목 설정
	        String emailTitle = "[JMT] 임시 비밀번호 발송 드립니다.";
	        
	        // 임시 비밀번호 생성 (한 번만 호출)
	        String tempPW = tempPw(); 

	        /* 메일 발송 */
	        MimeMessage mimeMessage = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

	        helper.setTo(email); // 받는 사람 이메일 세팅
	        helper.setSubject(emailTitle); // 이메일 제목 세팅
	        helper.setText(loadHtml(tempPW, htmlName), true); // 이메일 내용 세팅

	        // CID(Content-ID)를 이용해 메일에 이미지 첨부
	        helper.addInline("logo", new ClassPathResource("static/images/logo.png"));

	        // 메일 발송
	        mailSender.send(mimeMessage);

	        // 비밀번호 암호화(BCrypt)
	        String encPw = encoder.encode(tempPW);

	        // 이메일을 통해 회원의 memberNo 조회
	        int memberNo = mapper.findMemberNoByEmail(email);
	        if (memberNo == 0) {
	            throw new Exception("해당 이메일을 가진 사용자를 찾을 수 없습니다.");
	        } 
	        

	        // DB에 암호화된 비밀번호 업데이트
	        return mapper.updatePassword(memberNo, encPw);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0; // 예외 발생 == 실패 == 0 반환
	    }
	    
	    
	    
	}

	
	
}
