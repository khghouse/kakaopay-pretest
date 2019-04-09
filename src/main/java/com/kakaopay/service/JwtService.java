package com.kakaopay.service;

public interface JwtService {
	
	/**
	 * 토큰 생성
	 * @param key
	 * @param data
	 * @param subject
	 * @return
	 */
	public <T> String createToken(String key, T data, String subject);
	
	/**
	 * 유효한 토큰인지 체크
	 * @param jwt
	 * @return
	 */
	public boolean isUsable(String jwt);
}
