package com.proemsalud.laboratory.metrics.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proemsalud.laboratory.metrics.dto.DashboardSummaryResponse;
import com.proemsalud.laboratory.metrics.dto.TestResultMonthlyCountProjection;
import com.proemsalud.laboratory.metrics.dto.TopRequestedTestProjection;
import com.proemsalud.laboratory.metrics.repository.ITestResultSaleRepository;
import com.proemsalud.laboratory.patient.repository.IPatientRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class DashboardServiceImpl implements IDashboardService {

	private static final ZoneId GUATEMALA_ZONE = ZoneId.of("America/Guatemala");

	@Autowired
	private IPatientRepository patientRepository;

	@Autowired
	private ITestResultSaleRepository testResultSaleRepository;

	@Override
	public List<TopRequestedTestProjection> findMostTop5RequestedTestResults() {
		
		Pageable pageable = PageRequest.of(0, 5);

		return testResultSaleRepository.findMostTop5RequestedTestResults(pageable);
	}

	@Override
	public List<TestResultMonthlyCountProjection> getTestResultSaleTrend(Instant startDate, Instant endDate) {

		return testResultSaleRepository.getTestResultSaleTrend(startDate, endDate);
	}

	@Override
	public DashboardSummaryResponse getDashboardSummay() {

		LocalDate today = LocalDate.now(GUATEMALA_ZONE);

		Instant startOfDay = today.atStartOfDay(GUATEMALA_ZONE).toInstant();

		Instant startOfNextDay = today.plusDays(1).atStartOfDay(GUATEMALA_ZONE).toInstant();

		BigDecimal totalSales = testResultSaleRepository.totalSales();

		Long totalTestResults = testResultSaleRepository.totalTestResults();

		Integer totalPatients = patientRepository.totalPatients();

		BigDecimal currentTotalSales = testResultSaleRepository.currentTotalSales(startOfDay, startOfNextDay);

		DashboardSummaryResponse response = new DashboardSummaryResponse();

		response.setTotalSales(totalSales);
		response.setTotalTestResults(totalTestResults);
		response.setTotalPatients(totalPatients);
		response.setCurrentTotalSales(currentTotalSales);

		return response;
	}
}
