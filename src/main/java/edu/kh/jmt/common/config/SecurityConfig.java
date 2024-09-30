package edu.kh.jmt.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration	// 서버 실행 시 객체로 만들어져서 내부 메서드를 모두 수행
								// -> 서버에 적용될 설정, Bean 생성 시 사용
public class SecurityConfig {
	
	@Bean // 메서드에서 반환된 객체를 Spring Bean으로 등록하는 어노테이션
				// -> 생성된 객체를 Spring이 관리(IOC)
  			// -> 필요한 곳에 의존성 주입(DI) 가능해짐
	
  public BCryptPasswordEncoder bCryptPasswordEncoder()	{
		return new BCryptPasswordEncoder();
  }
}
