package com.proemsalud.laboratory.user.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.exception.FileUploadException;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.user.domain.Role;
import com.proemsalud.laboratory.user.domain.User;
import com.proemsalud.laboratory.user.dto.create.CreateUserRequest;
import com.proemsalud.laboratory.user.dto.response.UserResponse;
import com.proemsalud.laboratory.user.dto.update.UpdateUserRequest;
import com.proemsalud.laboratory.user.mapper.UserMapper;
import com.proemsalud.laboratory.user.repository.IRoleRepository;
import com.proemsalud.laboratory.user.repository.IUserRepository;
import com.proemsalud.laboratory.util.upload.IUploadFileService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUploadFileService uploadFileService;

	@Override
	@Transactional
	public UserResponse create(CreateUserRequest request) {

		User user = userMapper.toEntity(request);

		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(findRolesByIds(request.getRoleIds()));

		User saved = userRepository.save(user);

		return userMapper.toResponse(saved);
	}

	@Override
	@Transactional
	public UserResponse update(Long id, UpdateUserRequest request) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		user.setUsername(request.getUsername());
		user.setEnabled(request.getEnabled());

		// Si no se envía contraseña, se conserva la existente.
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		// Reemplaza los roles anteriores por los recibidos.
		user.setRoles(findRolesByIds(request.getRoleIds()));

		User updated = userRepository.save(user);

		return userMapper.toResponse(updated);
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse findById(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		return userMapper.toResponse(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponse> findAll() {

		return userRepository.findAll().stream().map(userMapper::toResponse).toList();

	}

	@Override
	@Transactional
	public void delete(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		String previusPicture = user.getPicture();

		uploadFileService.delete(previusPicture);

		userRepository.delete(user);
	}

	private List<Role> findRolesByIds(List<Long> roleIds) {

		if (roleIds == null || roleIds.isEmpty()) {
			throw new IllegalArgumentException("Debe asignar al menos un rol");
		}

		List<Long> uniqueIds = roleIds.stream().distinct().toList();

		List<Role> roles = roleRepository.findAllById(uniqueIds);

		if (roles.size() != uniqueIds.size()) {
			throw new IllegalArgumentException("Uno o más roles no existen");
		}

		return roles;
	}

	@Override
	public void toggleStatus(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		user.setEnabled(!Boolean.TRUE.equals(user.getEnabled()));

		userRepository.save(user);
	}

	@Override
	public UserResponse uploadPicture(Long id, MultipartFile file) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		if (!file.isEmpty()) {
			// Unique picture
			String fileName = null;

			try {
				fileName = uploadFileService.copy(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new FileUploadException(
						"" + "Ocurrio un error durante la subida de la imagen " + e.getCause().getMessage());
			}

			// If picture already exists - delete previous
			String previousPicture = user.getPicture();
			uploadFileService.delete(previousPicture);

			user.setPicture(fileName);
		}

		return userMapper.toResponse(userRepository.save(user));
	}

	@Override
	public Resource viewPicture(String fileName) {

		Resource resource = null;

		try {
			resource = uploadFileService.load(fileName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resource;
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse findByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		return userMapper.toResponse(user);
	}

}
