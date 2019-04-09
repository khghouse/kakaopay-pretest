package com.kakaopay.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kakaopay.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String token = request.getHeader("Authorization");
		
		if(token != null && jwtService.isUsable(token)) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			throw new RuntimeException("로그인 후 이용해주세요.");
		}
	}
}
