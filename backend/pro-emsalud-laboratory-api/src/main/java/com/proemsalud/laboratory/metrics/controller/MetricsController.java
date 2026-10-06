package com.proemsalud.laboratory.metrics.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proemsalud.laboratory.metrics.dto.DashboardSummaryResponse;
import com.proemsalud.laboratory.metrics.dto.TestResultMonthlyCountProjection;
import com.proemsalud.laboratory.metrics.dto.TopRequestedTestProjection;
import com.proemsalud.laboratory.metrics.service.IDashboardService;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
	
	@Autowired
	private IDashboardService dashboardService;
	
	@GetMapping("/summary")
	public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
		return ResponseEntity.ok(dashboardService.getDashboardSummay());
	}
	
	
	@GetMapping("/top-five-test-results")
	public ResponseEntity<List<TopRequestedTestProjection>> getMostTop5RequestedTestResults() {
		return ResponseEntity.ok(dashboardService.findMostTop5RequestedTestResults());
	}
	
	
	@GetMapping("/test-result-sale-trend")
	public ResponseEntity<List<TestResultMonthlyCountProjection>> getTestResultSaleTrend(
			@RequestParam Instant startDate,
			@RequestParam Instant endDate
			) {
		return ResponseEntity.ok(dashboardService.getTestResultSaleTrend(startDate, endDate));
	}
	

}
