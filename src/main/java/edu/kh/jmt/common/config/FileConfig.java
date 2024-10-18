package edu.kh.jmt.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration // 서버 실행 시 해당 클래스 내부 메서드를 모두 실행
							 // (메서드에 서버 설정 내용 작성)
@PropertySource("classpath:/config.properties")
// -> config.propertiew 에 작성된 내용을 얻어와서 사용하곘다!!
public class FileConfig implements WebMvcConfigurer{
	
	// config.properties 에서 ${key} 가 일치하는 값을 얻어와 변수에 대입
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;	// 임계값
	
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize; // 요청당 파일 최대 크기
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize; // 개별 파일당 최대 크기
	
	@Value("${spring.servlet.multipart.location}")
	private String location; // 임계값 초과 시 임시 저장 폴더 경로
	
	//-----------------------------------------
	
	// test 관련 값
	@Value("${my.test.resource-handler}")
	private String testResourceHandler;			// 테스트 이미지 요청 주소
	
	@Value("${my.test.resource-location}")
	private String testResourceLocation;		// 테스트 이미지 요청 시 
																					// 연결될 서버 폴더 경로
	
	
	//---------------------------------------------------------------
	// profile 관련 값
	//프로필 이미지 요청 경로 + 서버 연결 폴더
	
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler;			// 프로필 이미지 요청 주소
	
	@Value("${my.profile.resource-location}")
	private String profileResourceLocation; 		// 프로필 이미지 요청 시 
																							// 연결될 서버 폴더 경로
	
	

	
	
	
	
	//---------------------------------------------------------------------------
	

	/* MultipartResolver 설정 */
	@Bean
	public MultipartConfigElement configElement() {
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));		// 개별 파일 최대 크기
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));	// 요청 파일 최대 크기
		
		factory.setLocation(location);
		
		
		return factory.createMultipartConfig();
	}
	
	
	// MultipartResolver 객체를 bean으로 추가
	// -> 추가 후 위에서 만든 MultipartConfig를 자동으로 이용
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver
			= new StandardServletMultipartResolver();
		
		return multipartResolver;
	}
	

	// 인터넷(웹) 으로 특정 형태의 요청(js, css, image)이 있을 경우
	// 서버 컴퓨터의 특정 폴더와 연결하는 설정을 작성하는 메서드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler(testResourceHandler)			// /images/test/**
		.addResourceLocations(testResourceLocation);  // file:///C:/uploadFiles/test/
		
		//  /images/test/ 로 시작하는 주소로 요청이 있을 경우
		//  서버 컴퓨터에 C:/uploadFiles/test/ 폴더로 연결
		
		
		registry
		.addResourceHandler(profileResourceHandler)					// /images/profile/**
		.addResourceLocations(profileResourceLocation);			// file:///C:/uploadFiles/profile/
		
		//  /images/profile/ 로 시작하는 주소로 요청이 있을 경우
		//  서버 컴퓨터에 C:/uploadFiles/profile/ 폴더로 연결
	}
	

}
