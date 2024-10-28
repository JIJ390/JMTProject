package edu.kh.jmt.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.jmt.admin.service.AdminRestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryInterceptor implements HandlerInterceptor{

	@Autowired // 의존성 주입
	private	AdminRestaurantService service;
	
	// 후처리
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {

	
		List<Map<String, String>> categoryList = service.selectCategoryList();
			
		// 조회 결과를 request scope 에 세팅
		request.setAttribute("categoryList", categoryList);
		
		log.debug(categoryList.toString());
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
}
