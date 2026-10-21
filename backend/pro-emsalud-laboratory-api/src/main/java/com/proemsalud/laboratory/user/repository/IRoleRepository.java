package com.proemsalud.laboratory.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.user.domain.Role;


public interface IRoleRepository  extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	
	Optional<Role> findByAuthority(String authority);
}
