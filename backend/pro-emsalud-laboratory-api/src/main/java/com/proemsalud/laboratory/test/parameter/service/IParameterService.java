package com.proemsalud.laboratory.test.parameter.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterRequest;
import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;


public interface IParameterService {
	
	ParameterResponse create(CreateParameterRequest parameter);
	
	Page<ParameterResponse> findAll(Pageable pageable);

	ParameterResponse update(Long id, UpdateParameterRequest parameter);

	ParameterResponse findById(Long id);
	
	void delete(Long id);
	
	List<ParameterResponse> getParametersByExamType(ExamType examType);
}
