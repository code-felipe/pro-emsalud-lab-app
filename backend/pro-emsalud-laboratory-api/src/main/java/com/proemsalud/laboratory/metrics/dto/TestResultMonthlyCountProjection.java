package com.proemsalud.laboratory.metrics.dto;

import java.math.BigDecimal;


public interface TestResultMonthlyCountProjection {
	
    Integer getMonth();

    String getMonthName();

    BigDecimal getSale();

    Long getTotal();
}
