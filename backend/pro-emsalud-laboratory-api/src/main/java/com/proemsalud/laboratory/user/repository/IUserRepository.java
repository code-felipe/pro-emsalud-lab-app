package com.proemsalud.laboratory.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.user.domain.User;

public interface IUserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	public Optional<User> findByUsername(String username);
}
