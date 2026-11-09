package com.proemsalud.laboratory.test.parameter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proemsalud.laboratory.test.parameter.domain.ParameterOption;

public interface IParameterOptionRepository extends JpaRepository<ParameterOption, Long>, JpaSpecificationExecutor<ParameterOption> {

}
