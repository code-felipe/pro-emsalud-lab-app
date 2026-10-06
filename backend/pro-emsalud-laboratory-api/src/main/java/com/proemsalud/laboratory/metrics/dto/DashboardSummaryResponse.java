package com.proemsalud.laboratory.metrics.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummaryResponse {
	
    private BigDecimal totalSales;
    
    private BigDecimal currentTotalSales;

    private Long totalTestResults;

    private Integer totalPatients;
    
}
