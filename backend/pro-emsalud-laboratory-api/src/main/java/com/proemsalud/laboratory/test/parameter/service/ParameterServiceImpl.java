package com.proemsalud.laboratory.test.parameter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterRequest;
import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.mapper.ParameterMapper;
import com.proemsalud.laboratory.test.parameter.repository.IParameterRepository;

import jakarta.transaction.Transactional;

@Service
public class ParameterServiceImpl implements IParameterService{
	
	@Autowired
    private IParameterRepository parameterRepository;
    
	@Autowired
    private ParameterMapper parameterMapper;
	
	@Transactional
	@Override
	public ParameterResponse create(CreateParameterRequest request) {
		
		Parameter parameter = parameterMapper.toEntity(request);

	    Parameter saved = parameterRepository.save(parameter);

	    return parameterMapper.toResponse(saved);
	}

	@Override
	public Page<ParameterResponse> findAll(Pageable pageable) {
		  return parameterRepository.findAll(pageable)
	                .map(parameterMapper::toResponse);
	}

	@Override
	public ParameterResponse update(Long id, UpdateParameterRequest request) {
		
	     Parameter parameter = parameterRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found with id: " + id));
	     
	     
	     parameter.setName(request.getName());
	     parameter.setExamType(request.getExamType());
	     parameter.setInputType(request.getInputType());
	     parameter.setDisplayOrder(request.getDisplayOrder());
	     
	     return parameterMapper.toResponse(parameter);
	 	
	}

	@Override
	public ParameterResponse findById(Long id) {
		
	     Parameter parameter = parameterRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found with id: " + id));
	     
	     return parameterMapper.toResponse(parameter);
	}

	@Override
	public void delete(Long id) {
	
		Parameter parameter = parameterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found with id: " + id));
     
		parameterRepository.delete(parameter);
	}


    public List<ParameterResponse> getParametersByExamType(ExamType examType) {

        return parameterRepository
                .findAllByExamTypeWithOptions(examType)
                .stream()
                .map(parameterMapper::toResponse)
                .toList();
    }
}
