package com.kakaopay.service;

import java.util.Map;

import com.kakaopay.model.User;

public interface UserService {
	
	/**
	 * 회원가입
	 * @param user
	 * @return User
	 */
	public Map<String, String> signUp(User user);
	
	/**
	 * 로그인
	 * @param id
	 * @param password
	 * @return User
	 */
	public User signIn(User user);
}
