package com.kakaopay.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {
	
	private static final String SALT = "kakaopaySecret";
	
	public <T> String createToken(String key, T data, String subject)
	{
		Date now = new Date();
		Date expiration = new Date(now.getTime() + 1800000); // 토큰 유효시간 30분
		// Date expiration = new Date(now.getTime() + 180000); // 3분 테스트
		
		String jwt = Jwts.builder().setHeaderParam("typ", "JWT")
										.setHeaderParam("regDate", System.currentTimeMillis())
										.setSubject(subject)
										.claim(key, data)
										.signWith(SignatureAlgorithm.HS256, this.getSecretKey())
										.setExpiration(expiration)
										.compact();
		
		return jwt;
	}
	
	public boolean isUsable(String jwt) {
		
		try {
			// 유효한 토큰인지 체크
			Jws<Claims> claim = Jwts.parser().setSigningKey(this.getSecretKey()).parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private byte[] getSecretKey()
	{
		byte[] key = null;
		
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return key;
	}
}
