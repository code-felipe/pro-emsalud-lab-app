package com.proemsalud.laboratory.metrics.dto;

import java.math.BigDecimal;

public interface TopRequestedTestProjection {
	
    Long getTestId();

    String getTestName();

    Long getTotal();

    BigDecimal getTotalSales();
}
