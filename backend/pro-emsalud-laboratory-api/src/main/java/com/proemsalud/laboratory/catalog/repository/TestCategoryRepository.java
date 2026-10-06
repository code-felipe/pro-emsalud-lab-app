package com.proemsalud.laboratory.catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.catalog.domain.TestCategory;

public interface TestCategoryRepository extends JpaRepository<TestCategory, Long>, JpaSpecificationExecutor<TestCategory> {
	
	List<TestCategory> findByNameContainingIgnoreCaseAndActiveTrue(String name);
}
