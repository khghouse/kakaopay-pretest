package com.kakaopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findById(String id);
}
