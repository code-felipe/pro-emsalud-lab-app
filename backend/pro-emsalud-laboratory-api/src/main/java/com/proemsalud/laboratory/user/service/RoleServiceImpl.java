package com.proemsalud.laboratory.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.user.dto.response.RoleResponse;
import com.proemsalud.laboratory.user.mapper.RoleMapper;
import com.proemsalud.laboratory.user.repository.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<RoleResponse> findAll() {
		return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
	}
	
	
}
