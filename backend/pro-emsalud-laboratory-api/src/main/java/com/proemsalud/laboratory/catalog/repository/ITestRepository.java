package com.proemsalud.laboratory.catalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proemsalud.laboratory.catalog.domain.Test;

public interface ITestRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {
	
//	List<Test> findByNameContainingIgnoreCaseAndActiveTrue(String name);//case 1: search for test.name
//		
//	List<Test> findByCategoryNameContainingIgnoreCaseAndActiveTrue(String categoryName);// case 2: search for category.name
//
//	List<Test> findByNameContainingIgnoreCaseAndActiveTrueAndCategoryName(String name, String categoryName);// case 3: search for both
//
	
	@Query("""
		    SELECT t
		    FROM Test t
		    WHERE t.active = true
		      AND (
		          LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))
		          OR LOWER(t.category.name) LIKE LOWER(CONCAT('%', :query, '%'))
		      )
		""")
		List<Test> searchActiveTests(@Param("query") String query);

	// NO IN USE
	List<Test> findByNameContainingIgnoreCaseAndActiveTrueAndCategoryId(String name, Long panelId);
	

}
