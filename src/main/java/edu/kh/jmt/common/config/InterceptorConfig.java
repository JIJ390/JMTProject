package edu.kh.jmt.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.jmt.common.interceptor.CategoryInterceptor;
import edu.kh.jmt.common.interceptor.LocationInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Bean
	public CategoryInterceptor categoryInterceptor() {
		return new CategoryInterceptor();
	}
	
	@Bean
	public LocationInterceptor selectLocationList() {
		return new LocationInterceptor();
	}

	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(categoryInterceptor())
				.addPathPatterns("/admin/restaurant/regist")
				.addPathPatterns("/admin/restaurant/updateView");
		
		registry.addInterceptor(selectLocationList())
				.addPathPatterns("/admin/restaurant/regist")
				.addPathPatterns("/admin/restaurant/updateView")
				.addPathPatterns("/**") 
				.excludePathPatterns(
						"/css/**",
						"/js/**,",
						"images/**",
						"/favicon.ico");

	}
	
	

	
	
	
}
