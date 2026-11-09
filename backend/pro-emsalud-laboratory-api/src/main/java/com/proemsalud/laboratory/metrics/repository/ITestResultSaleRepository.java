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

public interface ITestResultSaleRepository  {
	
	List<TopRequestedTestProjection> findMostTop5RequestedTestResults(
            Pageable pageable);

    BigDecimal totalSales();

    BigDecimal currentTotalSales(
            Instant startOfDay,
            Instant startOfNextDay);

    Long totalTestResults();

    List<TestResultMonthlyCountProjection> getTestResultSaleTrend(
            Instant startDate,
            Instant endDate);

//	@Query(value = """
//		    SELECT
//		        r.testId AS testId,
//		        r.testName AS testName,
//		        COUNT(*) AS total,
//		        COALESCE(SUM(r.unitPrice), 0) AS totalSales
//		    FROM (
//
//		        SELECT
//		            sc.test_id AS testId,
//		            t.name AS testName,
//		            sc.unit_price AS unitPrice
//		        FROM simple_chemistry sc
//		        INNER JOIN test t ON t.id = sc.test_id
//
//		        UNION ALL
//
//		        SELECT
//		            s.test_id AS testId,
//		            t.name AS testName,
//		            s.unit_price AS unitPrice
//		        FROM stools s
//		        INNER JOIN test t ON t.id = s.test_id
//
//		        UNION ALL
//
//		        SELECT
//		            tor.test_id AS testId,
//		            t.name AS testName,
//		            tor.unit_price AS unitPrice
//		        FROM torchs tor
//		        INNER JOIN test t ON t.id = tor.test_id
//
//		        UNION ALL
//
//		        SELECT
//		            u.test_id AS testId,
//		            t.name AS testName,
//		            u.unit_price AS unitPrice
//		        FROM urinalysis u
//		        INNER JOIN test t ON t.id = u.test_id
//
//		        UNION ALL
//
//		        SELECT
//		            w.test_id AS testId,
//		            t.name AS testName,
//		            w.unit_price AS unitPrice
//		        FROM widal_reaction w
//		        INNER JOIN test t ON t.id = w.test_id
//
//		    ) r
//
//		    GROUP BY
//		        r.testId,
//		        r.testName
//
//		    ORDER BY
//		        total DESC
//
//		    """, nativeQuery = true)
//		List<TopRequestedTestProjection> findMostTop5RequestedTestResults(Pageable pageable);
//
//	@Query(value = """
//		    SELECT
//		        COALESCE((SELECT SUM(unit_price) FROM simple_chemistry), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price) FROM stools), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price) FROM torchs), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price) FROM urinalysis), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price) FROM widal_reaction), 0)
//		    AS totalSales
//		    """, nativeQuery = true)
//		BigDecimal totalSales();
//	
//	
//	@Query(value = """
//		    SELECT
//		        COALESCE((SELECT SUM(unit_price)
//		                  FROM simple_chemistry
//		                  WHERE created_at >= :startOfDay
//		                    AND created_at < :startOfNextDay), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price)
//		                  FROM stools
//		                  WHERE created_at >= :startOfDay
//		                    AND created_at < :startOfNextDay), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price)
//		                  FROM torchs
//		                  WHERE created_at >= :startOfDay
//		                    AND created_at < :startOfNextDay), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price)
//		                  FROM urinalysis
//		                  WHERE created_at >= :startOfDay
//		                    AND created_at < :startOfNextDay), 0)
//		        +
//		        COALESCE((SELECT SUM(unit_price)
//		                  FROM widal_reaction
//		                  WHERE created_at >= :startOfDay
//		                    AND created_at < :startOfNextDay), 0)
//		    AS totalSales
//		    """, nativeQuery = true)
//		BigDecimal currentTotalSales(
//		    @Param("startOfDay") Instant startOfDay,
//		    @Param("startOfNextDay") Instant startOfNextDay
//		);
//
//	@Query(value = """
//		    SELECT
//		        (SELECT COUNT(*) FROM simple_chemistry) +
//		        (SELECT COUNT(*) FROM stools) +
//		        (SELECT COUNT(*) FROM torchs) +
//		        (SELECT COUNT(*) FROM urinalysis) +
//		        (SELECT COUNT(*) FROM widal_reaction)
//		    AS total
//		    """, nativeQuery = true)
//		Long totalTestResults();
//
//		
//	
//	@Query(value = """
//		    SELECT
//		        MONTH(r.created_at) AS month,
//		        MONTHNAME(r.created_at) AS monthName,
//		        r.testId AS testId,
//		        r.testName AS testName,
//		        COALESCE(SUM(r.unitPrice), 0) AS sale,
//		        COUNT(*) AS total
//
//		    FROM (
//
//		        SELECT
//		            sc.created_at,
//		            sc.test_id AS testId,
//		            t.name AS testName,
//		            sc.unit_price AS unitPrice
//		        FROM simple_chemistry sc
//		        INNER JOIN test t ON t.id = sc.test_id
//		        WHERE sc.created_at >= :startDate
//		          AND sc.created_at < :endDate
//
//		        UNION ALL
//
//		        SELECT
//		            s.created_at,
//		            s.test_id AS testId,
//		            t.name AS testName,
//		            s.unit_price AS unitPrice
//		        FROM stools s
//		        INNER JOIN test t ON t.id = s.test_id
//		        WHERE s.created_at >= :startDate
//		          AND s.created_at < :endDate
//
//		        UNION ALL
//
//		        SELECT
//		            tor.created_at,
//		            tor.test_id AS testId,
//		            t.name AS testName,
//		            tor.unit_price AS unitPrice
//		        FROM torchs tor
//		        INNER JOIN test t ON t.id = tor.test_id
//		        WHERE tor.created_at >= :startDate
//		          AND tor.created_at < :endDate
//
//		        UNION ALL
//
//		        SELECT
//		            u.created_at,
//		            u.test_id AS testId,
//		            t.name AS testName,
//		            u.unit_price AS unitPrice
//		        FROM urinalysis u
//		        INNER JOIN test t ON t.id = u.test_id
//		        WHERE u.created_at >= :startDate
//		          AND u.created_at < :endDate
//
//		        UNION ALL
//
//		        SELECT
//		            w.created_at,
//		            w.test_id AS testId,
//		            t.name AS testName,
//		            w.unit_price AS unitPrice
//		        FROM widal_reaction w
//		        INNER JOIN test t ON t.id = w.test_id
//		        WHERE w.created_at >= :startDate
//		          AND w.created_at < :endDate
//
//		    ) r
//
//		    GROUP BY
//		        MONTH(r.created_at),
//		        MONTHNAME(r.created_at),
//		        r.testId,
//		        r.testName
//
//		    ORDER BY
//		        MONTH(r.created_at),
//		        r.testName
//
//		    """, nativeQuery = true)
//		List<TestResultMonthlyCountProjection> getTestResultSaleTrend(
//		        @Param("startDate") Instant startDate,
//		        @Param("endDate") Instant endDate
//		);
//	
//	
//===OLD QUERIES===
//	@Query("""
//    SELECT COALESCE(SUM(tr.unitPrice), 0)
//    FROM TestResult tr
//    WHERE tr.createdAt >= :startOfDay
//      AND tr.createdAt < :startOfNextDay
//""")
//BigDecimal currentTotalSales(@Param("startOfDay") Instant startOfDay,
//@Param("startOfNextDay") Instant startOfNextDay);
	

//	@Query(value = """
//			    	  SELECT
//			    MONTH(tr.created_at) AS month,
//			    MONTHNAME(tr.created_at) AS monthName,
//			    t.id AS testId,
//			    t.name AS testName,
//			    COALESCE(SUM(tr.unit_price), 0) AS sale,
//			    COUNT(tr.id) AS total
//			FROM test_results tr
//			INNER JOIN test t
//			    ON t.id = tr.test_id
//			WHERE tr.created_at >= :startDate
//			  AND tr.created_at < :endDate
//			GROUP BY
//			    MONTH(tr.created_at),
//			    MONTHNAME(tr.created_at),
//			    t.id,
//			    t.name
//			ORDER BY
//			    MONTH(tr.created_at),
//			    t.name
//			    	    """, nativeQuery = true)
//	List<TestResultMonthlyCountProjection> getTestResultSaleTrend(@Param("startDate") Instant startDate,
//			@Param("endDate") Instant endDate);

	
//	@Query("""
//    SELECT COUNT(tr.id)
//    FROM TestResult tr
//""")
//Long totalTestResults();
	
//	@Query("""
//    SELECT COALESCE(SUM(tr.unitPrice), 0)
//    FROM TestResult tr
//""")
//BigDecimal totalSales();


	//
//	@Query("""
//			    SELECT
//			        tr.test.id AS testId,
//			        tr.test.name AS testName,
//			        COUNT(tr.id) AS total,
//			        COALESCE(SUM(tr.unitPrice), 0) AS totalSales
//			    FROM TestResult tr
//			    GROUP BY tr.test.id, tr.test.name
//			    ORDER BY COUNT(tr.id) DESC
//			""")
//	List<TopRequestedTestProjection> findMostTop5RequestedTestResults(Pageable pageable);
	/*
	 * January CBC $1,200 30 Glucose $800 20 Cholesterol $600 15
	 * 
	 * February CBC $1,500 35 Glucose $900 22 Cholesterol $700 18
	 * 
	 */

}
