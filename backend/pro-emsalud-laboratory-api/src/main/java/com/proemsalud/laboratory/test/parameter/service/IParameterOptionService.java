package com.proemsalud.laboratory.test.parameter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterOptionResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterOptionRequest;


public interface IParameterOptionService {
	
	ParameterOptionResponse create(CreateParameterOptionRequest option);
	
	Page<ParameterOptionResponse> findAll(Pageable pageable);

	ParameterOptionResponse update(Long id, UpdateParameterOptionRequest parameter);

	ParameterOptionResponse findById(Long id);
	
	void delete(Long id);
}
