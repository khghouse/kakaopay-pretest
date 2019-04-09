package com.kakaopay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.model.User;
import com.kakaopay.service.JwtService;
import com.kakaopay.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	/**
	 * 		API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을	
	 * 		개발하고 각 API 호출 시에 HTTP Header 에 발급받은 토큰을 가지고 호출하세요.
	 * 
	 * 		추가 제약사항 1. signup 계정생성 API: 입력으로 ID, PW 받아 내부 DB 에 계정 저장
	 * 		@param user
	 * 		@return json
	 */
	@PostMapping("/signup")
	@ResponseBody
	public Map<String, String> signUp(@RequestBody User user) {
		
		// 회원 가입 결과
		Map<String, String> map = userService.signUp(user);
		
		return map;
	}
	
	/**
	 * 		추가 제약사항 2. signin 로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
	 * 		@param user
	 * 		@param response
	 * 		@return json
	 */
	@PostMapping("/signin")
	@ResponseBody
	public Map<String, String> signIn(@RequestBody User user, HttpServletResponse response) {
		
		User loginUser = userService.signIn(user);
		
		// 토큰 생성
		String token = jwtService.createToken("user", loginUser, "userToken");
		
		// 응답 해더에 등록
		response.setHeader("Authorization", token);
		
		// 응답 해더에 등록했지만 출력으로 토큰을 보여주기 위해 리턴 객체 생성
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("login_id", loginUser.getId());
		map.put("token", token);
		
		return map;
	}
}
