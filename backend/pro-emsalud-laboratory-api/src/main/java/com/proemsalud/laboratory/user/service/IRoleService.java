package com.proemsalud.laboratory.user.service;

import java.util.List;

import com.proemsalud.laboratory.user.dto.response.RoleResponse;

public interface IRoleService {
	
	List<RoleResponse> findAll();
}
