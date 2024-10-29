package edu.kh.jmt.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.jmt.common.filter.AdminFilter;

@Configuration
public class FilterConfig {

	@Bean	// 반환된 객체를 Bean으로 등록
	public FilterRegistrationBean<AdminFilter> adminFilter() {
		
		FilterRegistrationBean<AdminFilter> filter 
				= new FilterRegistrationBean<>();
		
		AdminFilter adminFilter = new AdminFilter();
		
		filter.setFilter(adminFilter); // 필터 등록
		
		// 필터가 동작할 요청 경로 패턴 지정
		String[] filteringUrl = {"/admin/member/*", "/admin/restaurant/*", "/admin/report/*", "/admin/notice/*"};
		
//		String[] exceptUrl = {"/admin/*"};
		
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		// 필터 이름 지정
		filter.setName("adminFilter");
		
		// 필터 순서 지정
		filter.setOrder(1);
		
		return filter;
	}
	
}
