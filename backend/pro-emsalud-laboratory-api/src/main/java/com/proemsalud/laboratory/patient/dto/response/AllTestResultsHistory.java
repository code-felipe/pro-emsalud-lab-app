package com.proemsalud.laboratory.patient.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public interface AllTestResultsHistory {
	
	   Long getId();

	    Long getTestId();

	    String getTestName();

	    BigDecimal getUnitPrice();

	    Long getOrderId();

	    Instant getOrderCreatedAt();

	    Instant getOrderUpdatedAt();

	    String getTestType();
}
