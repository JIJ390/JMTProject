package edu.kh.jmt.common.filter;

import java.io.IOException;

import edu.kh.jmt.myPage.dto.Member;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AdminFilter implements Filter{

	
	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain)
	    throws IOException, ServletException {
		// HTTP 통신이 가능한 형태로 request, response 다운 캐스팅
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		// 세션 객체 얻어오기
		HttpSession session = req.getSession();

		
		Member member = (Member)session.getAttribute("loginMember");
		
		// 로그인이 되어있지 않은 경우
		if ((member == null) || (member.getMemberAuth() != 2) ) {
			resp.sendRedirect("/admin"); // 메인 페이지로 리다이렉트
			
		} else { // 로그인이 된 경우
			chain.doFilter(request, response); // 다음 필터로 이동
			
		}
		
	}
}
