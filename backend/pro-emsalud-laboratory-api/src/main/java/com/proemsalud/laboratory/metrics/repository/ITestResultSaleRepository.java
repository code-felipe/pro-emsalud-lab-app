package com.proemsalud.laboratory.metrics.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.proemsalud.laboratory.metrics.dto.TestResultMonthlyCountProjection;
import com.proemsalud.laboratory.metrics.dto.TopRequestedTestProjection;
import com.proemsalud.laboratory.result.domain.TestResult;

public interface ITestResultSaleRepository
		extends JpaRepository<TestResult, Long>, JpaSpecificationExecutor<TestResult> {

	@Query("""
			    SELECT
			        tr.test.id AS testId,
			        tr.test.name AS testName,
			        COUNT(tr.id) AS total,
			        COALESCE(SUM(tr.unitPrice), 0) AS totalSales
			    FROM TestResult tr
			    GROUP BY tr.test.id, tr.test.name
			    ORDER BY COUNT(tr.id) DESC
			""")
	List<TopRequestedTestProjection> findMostTop5RequestedTestResults(Pageable pageable);

	@Query("""
			    SELECT COALESCE(SUM(tr.unitPrice), 0)
			    FROM TestResult tr
			""")
	BigDecimal totalSales();

	@Query("""
			    SELECT COALESCE(SUM(tr.unitPrice), 0)
			    FROM TestResult tr
			    WHERE tr.createdAt >= :startOfDay
			      AND tr.createdAt < :startOfNextDay
			""")
	BigDecimal currentTotalSales(@Param("startOfDay") Instant startOfDay,
			@Param("startOfNextDay") Instant startOfNextDay);

	@Query("""
			    SELECT COUNT(tr.id)
			    FROM TestResult tr
			""")
	Long totalTestResults();

	@Query(value = """
			    	  SELECT
			    MONTH(tr.created_at) AS month,
			    MONTHNAME(tr.created_at) AS monthName,
			    t.id AS testId,
			    t.name AS testName,
			    COALESCE(SUM(tr.unit_price), 0) AS sale,
			    COUNT(tr.id) AS total
			FROM test_results tr
			INNER JOIN test t
			    ON t.id = tr.test_id
			WHERE tr.created_at >= :startDate
			  AND tr.created_at < :endDate
			GROUP BY
			    MONTH(tr.created_at),
			    MONTHNAME(tr.created_at),
			    t.id,
			    t.name
			ORDER BY
			    MONTH(tr.created_at),
			    t.name
			    	    """, nativeQuery = true)
	List<TestResultMonthlyCountProjection> getTestResultSaleTrend(@Param("startDate") Instant startDate,
			@Param("endDate") Instant endDate);

	/*
	 * January CBC $1,200 30 Glucose $800 20 Cholesterol $600 15
	 * 
	 * February CBC $1,500 35 Glucose $900 22 Cholesterol $700 18
	 * 
	 */

}
