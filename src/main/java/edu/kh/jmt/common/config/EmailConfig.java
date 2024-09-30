package edu.kh.jmt.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration

//@PropertySource : properties 파일의 내용을 이용하겠다는 어노테이션
@PropertySource("classpath:/config.properties")
public class EmailConfig {
	
	// @Value : properties에 작성된 내용 중 키가 일치하는 값을 얻어와 필드에 대입
	@Value("${spring.mail.username}")
	private String userName;
	
	@Value("${spring.mail.password}")
	private String password;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.debug", "true");
		prop.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
		prop.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
		
		
		mailSender.setUsername(userName);
		mailSender.setPassword(password);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setJavaMailProperties(prop);
		
		return mailSender;
	}
}

