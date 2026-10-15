package com.proemsalud.laboratory.user.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.proemsalud.laboratory.user.dto.create.CreateUserRequest;
import com.proemsalud.laboratory.user.dto.response.UserResponse;
import com.proemsalud.laboratory.user.dto.update.UpdateUserRequest;

public interface IUserService {
	
	UserResponse create(CreateUserRequest request);

	UserResponse update(Long id, UpdateUserRequest request);

	UserResponse findById(Long id);

	List<UserResponse> findAll();
	
	void delete(Long id);
	
	public UserResponse  uploadPicture(Long id, MultipartFile file);
	 
	public Resource viewPicture(String fileName);
}
