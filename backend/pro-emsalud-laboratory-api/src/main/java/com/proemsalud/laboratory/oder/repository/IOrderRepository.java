package com.proemsalud.laboratory.oder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.proemsalud.laboratory.oder.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	
	public Optional<Order> findByIdAndPatientId(Long id, Long patientId);
	
	public Page<Order> findAllByPatientId(Long patientId, Pageable pageable);
	
	boolean existsByDoctorId(Long doctorId);
}
