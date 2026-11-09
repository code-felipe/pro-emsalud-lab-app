package com.proemsalud.laboratory.test.simpleschemistry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.test.simpleschemistry.dto.create.CreateSimpleChemistryRequest;
import com.proemsalud.laboratory.test.simpleschemistry.dto.response.SimpleChemistryResponse;
import com.proemsalud.laboratory.test.simpleschemistry.dto.update.UpdateSimpleChemistryRequest;
import com.proemsalud.laboratory.test.simpleschemistry.filter.SimpleChemistryFilter;

public interface ISimpleChemistryService {
	
	SimpleChemistryResponse create(CreateSimpleChemistryRequest request, Long orderId, Long patientId);
	
	Page<SimpleChemistryResponse> findAll(SimpleChemistryFilter filter, Pageable pageable, Long orderId, Long patientId);

	SimpleChemistryResponse update(Long id, UpdateSimpleChemistryRequest request);

	SimpleChemistryResponse findById(Long id);
	
	SimpleChemistryResponse findByIdAndOrderIdAndPatientId(Long id, Long orderId, Long patientId);
	
	Page<SimpleChemistryResponse>findByPatientId(SimpleChemistryFilter filter, Long patientId, Pageable pageable);
	
	void delete(Long id);
}
