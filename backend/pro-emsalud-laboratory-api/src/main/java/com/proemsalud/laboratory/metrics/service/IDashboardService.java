package com.proemsalud.laboratory.metrics.service;

import java.time.Instant;
import java.util.List;

import com.proemsalud.laboratory.metrics.dto.DashboardSummaryResponse;
import com.proemsalud.laboratory.metrics.dto.TestResultMonthlyCountProjection;
import com.proemsalud.laboratory.metrics.dto.TopRequestedTestProjection;
import org.springframework.data.domain.Pageable;

public interface IDashboardService {
	
	public List<TopRequestedTestProjection> findMostTop5RequestedTestResults();
	
	public List<TestResultMonthlyCountProjection> getTestResultSaleTrend(Instant startDate, Instant endDate );
	
	public DashboardSummaryResponse getDashboardSummay();
	
}
