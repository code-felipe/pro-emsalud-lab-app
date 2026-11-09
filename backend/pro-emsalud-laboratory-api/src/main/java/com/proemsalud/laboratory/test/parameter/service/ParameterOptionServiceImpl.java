package com.proemsalud.laboratory.test.parameter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proemsalud.laboratory.exception.ResourceNotFoundException;
import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.domain.ParameterOption;
import com.proemsalud.laboratory.test.parameter.dto.create.CreateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.dto.response.ParameterOptionResponse;
import com.proemsalud.laboratory.test.parameter.dto.update.UpdateParameterOptionRequest;
import com.proemsalud.laboratory.test.parameter.mapper.ParameterOptionMapper;
import com.proemsalud.laboratory.test.parameter.repository.IParameterOptionRepository;
import com.proemsalud.laboratory.test.parameter.repository.IParameterRepository;

@Service
public class ParameterOptionServiceImpl implements IParameterOptionService {
	
	@Autowired
	private IParameterOptionRepository optionRepository;
	
	@Autowired
    private IParameterRepository parameterRepository;
	
	@Autowired
    private ParameterOptionMapper optionMapper;

    @Transactional
    @Override
    public ParameterOptionResponse create(CreateParameterOptionRequest request) {
    	
        Parameter parameter = parameterRepository.findById(request.getParameterId())
                .orElseThrow(() -> new ResourceNotFoundException("Parameter not found"));

        ParameterOption option = optionMapper.toEntity(request, parameter);

        return optionMapper.toResponse(optionRepository.save(option));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ParameterOptionResponse> findAll(Pageable pageable) {

        return optionRepository.findAll(pageable)
                .map(optionMapper::toResponse);
    }

    @Transactional
    @Override
    public ParameterOptionResponse update(Long id, UpdateParameterOptionRequest request) {

        ParameterOption option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ParameterOption not found with id: " + id));
        
        option.setValue(request.getValue());
        option.setDisplayOrder(request.getDisplayOrder());
    

        return optionMapper.toResponse(option);
    }

    @Transactional(readOnly = true)
    @Override
    public ParameterOptionResponse findById(Long id) {

        ParameterOption option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ParameterOption not found with id: " + id));

        return optionMapper.toResponse(option);
    }

    @Transactional
    @Override
    public void delete(Long id) {

        ParameterOption option = optionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ParameterOption not found with id: " + id));

        optionRepository.delete(option);
    }
}
