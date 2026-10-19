package com.proemsalud.laboratory.user.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
		user.setRole(findRoleById(request.getRoleId()));

		return userMapper.toResponse(userRepository.save(user));
	}

	@Override
	@Transactional
	public UserResponse update(Long id, UpdateUserRequest request) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		user.setUsername(request.getUsername());

		// Si no se envía contraseña, se conserva la existente.
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		// Solo un ADMIN puede cambiar el rol o el estado. Un USER que se edita
		// a sí mismo no puede promoverse ni reactivarse.
		if (isCurrentUserAdmin()) {
			user.setRole(findRoleById(request.getRoleId()));
		}

		return userMapper.toResponse(userRepository.save(user));
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

		uploadFileService.delete(user.getPicture());

		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void toggleStatus(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		userRepository.save(user);
	}

	@Override
	@Transactional
	public UserResponse uploadPicture(Long id, MultipartFile file) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El usuario no existe con id: " + id));

		if (!file.isEmpty()) {

			String fileName;

			try {
				fileName = uploadFileService.copy(file);
			} catch (IOException e) {
				throw new FileUploadException("Ocurrió un error durante la subida de la imagen: " + e.getMessage());
			}

			// Si ya había una foto, se borra la anterior.
			uploadFileService.delete(user.getPicture());

			user.setPicture(fileName);
		}

		return userMapper.toResponse(userRepository.save(user));
	}

	@Override
	public Resource viewPicture(String fileName) {

		try {
			return uploadFileService.load(fileName);
		} catch (MalformedURLException e) {
			throw new FileUploadException("No se pudo cargar la imagen: " + fileName);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse findByUsername(String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		return userMapper.toResponse(user);
	}

	// ───────── helpers ─────────

	private Role findRoleById(Long roleId) {

		if (roleId == null) {
			throw new IllegalArgumentException("Debe asignar un rol");
		}

		return roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("El rol no existe con id: " + roleId));
	}

	private boolean isCurrentUserAdmin() {

		var authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication != null
				&& authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	}
}