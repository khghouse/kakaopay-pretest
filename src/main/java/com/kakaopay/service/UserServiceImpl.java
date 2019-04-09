package com.kakaopay.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.model.User;
import com.kakaopay.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Map<String, String> signUp(User user) {
		
		Map<String, String> resultMap = new HashMap<String, String>();
		User checkUser = userRepository.findById(user.getId());
		
		if(checkUser == null) {
			encodingPassword(user);
			userRepository.save(user);
			resultMap.put("resultMsg", "회원 가입 되었습니다!!");
		} else {
			resultMap.put("resultMsg", "이미 가입된 회원입니다.");
		}
		
		return resultMap;
	}
	
	public User signIn(User user) {
		
		User loginUser = userRepository.findById(user.getId());
		Objects.requireNonNull(loginUser, "등록된 아이디가 아닙니다."); // null check
		
		// param(original, encode)
		if(!this.isAccordPassword(user.getPassword(), loginUser)) { 
			throw new IllegalStateException("비밀번호가 틀립니다.");
		}
		
		return loginUser;
	}
	
	private void encodingPassword(User user) {
		
		String password = user.getPassword();
		
		// password + salt -> DIGEST
		// 단방향 해시 함수를 보완
		String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(encodedPassword);
	}
	
	private boolean isAccordPassword(String password, User user) {
		
		String encodedPassword = user.getPassword();
		
		// return boolean (original, encode)
		return BCrypt.checkpw(password, encodedPassword);
	}
}
