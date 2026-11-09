package com.proemsalud.laboratory.test.parameter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proemsalud.laboratory.test.parameter.domain.Parameter;
import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;

public interface IParameterRepository extends JpaRepository<Parameter, Long>, JpaSpecificationExecutor<Parameter> {

	@Query("""
			    SELECT DISTINCT p
			    FROM Parameter p
			    LEFT JOIN FETCH p.options
			    WHERE p.examType = :examType
			    ORDER BY p.displayOrder ASC
			""")
	List<Parameter> findAllByExamTypeWithOptions(@Param("examType") ExamType examType);
}
