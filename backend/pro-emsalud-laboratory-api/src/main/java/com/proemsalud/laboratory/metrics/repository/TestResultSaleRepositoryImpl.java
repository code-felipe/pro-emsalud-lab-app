
package com.proemsalud.laboratory.metrics.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.proemsalud.laboratory.metrics.dto.TestResultMonthlyCountProjection;
import com.proemsalud.laboratory.metrics.dto.TopRequestedTestProjection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class TestResultSaleRepositoryImpl
        implements ITestResultSaleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // =====================================================
    // TOP 5 EXAMENES MAS SOLICITADOS
    // =====================================================

    @Override
    public List<TopRequestedTestProjection> findMostTop5RequestedTestResults(
            Pageable pageable) {

        String sql = """
            SELECT
                r.testId AS testId,
                r.testName AS testName,
                COUNT(*) AS total,
                COALESCE(SUM(r.unitPrice), 0) AS totalSales
            FROM (

                SELECT
                    sc.test_id AS testId,
                    t.name AS testName,
                    sc.unit_price AS unitPrice
                FROM simple_chemistry sc
                INNER JOIN test t ON t.id = sc.test_id

                UNION ALL

                SELECT
                    s.test_id AS testId,
                    t.name AS testName,
                    s.unit_price AS unitPrice
                FROM stools s
                INNER JOIN test t ON t.id = s.test_id

                UNION ALL

                SELECT
                    tor.test_id AS testId,
                    t.name AS testName,
                    tor.unit_price AS unitPrice
                FROM torchs tor
                INNER JOIN test t ON t.id = tor.test_id

                UNION ALL

                SELECT
                    u.test_id AS testId,
                    t.name AS testName,
                    u.unit_price AS unitPrice
                FROM urinalysis u
                INNER JOIN test t ON t.id = u.test_id

                UNION ALL

                SELECT
                    w.test_id AS testId,
                    t.name AS testName,
                    w.unit_price AS unitPrice
                FROM widal_reaction w
                INNER JOIN test t ON t.id = w.test_id

            ) r

            GROUP BY
                r.testId,
                r.testName

            ORDER BY
                total DESC
            """;

        Query query = entityManager.createNativeQuery(sql);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        List<TopRequestedTestProjection> result = rows.stream()
            .<TopRequestedTestProjection>map(row ->
                new TopRequestedTestProjection() {

                    @Override
                    public Long getTestId() {
                        return toLong(row[0]);
                    }

                    @Override
                    public String getTestName() {
                        return (String) row[1];
                    }

                    @Override
                    public Long getTotal() {
                        return toLong(row[2]);
                    }

                    @Override
                    public BigDecimal getTotalSales() {
                        return toBigDecimal(row[3]);
                    }
                }
            )
            .toList();

        return result;
    }

    // =====================================================
    // VENTAS TOTALES
    // =====================================================

    @Override
    public BigDecimal totalSales() {

        String sql = """
            SELECT
                COALESCE((SELECT SUM(unit_price)
                    FROM simple_chemistry), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM stools), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM torchs), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM urinalysis), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM widal_reaction), 0)
            AS totalSales
            """;

        Object result = entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return toBigDecimal(result);
    }

    // =====================================================
    // VENTAS DEL DIA ACTUAL
    // =====================================================

    @Override
    public BigDecimal currentTotalSales(
            Instant startOfDay,
            Instant startOfNextDay) {

        String sql = """
            SELECT
                COALESCE((SELECT SUM(unit_price)
                    FROM simple_chemistry
                    WHERE created_at >= :startOfDay
                      AND created_at < :startOfNextDay), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM stools
                    WHERE created_at >= :startOfDay
                      AND created_at < :startOfNextDay), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM torchs
                    WHERE created_at >= :startOfDay
                      AND created_at < :startOfNextDay), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM urinalysis
                    WHERE created_at >= :startOfDay
                      AND created_at < :startOfNextDay), 0)
                +
                COALESCE((SELECT SUM(unit_price)
                    FROM widal_reaction
                    WHERE created_at >= :startOfDay
                      AND created_at < :startOfNextDay), 0)
            AS totalSales
            """;

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("startOfDay", startOfDay);
        query.setParameter("startOfNextDay", startOfNextDay);

        Object result = query.getSingleResult();

        return toBigDecimal(result);
    }

    // =====================================================
    // TOTAL DE RESULTADOS
    // =====================================================

    @Override
    public Long totalTestResults() {

        String sql = """
            SELECT
                (SELECT COUNT(*) FROM simple_chemistry) +
                (SELECT COUNT(*) FROM stools) +
                (SELECT COUNT(*) FROM torchs) +
                (SELECT COUNT(*) FROM urinalysis) +
                (SELECT COUNT(*) FROM widal_reaction)
            AS total
            """;

        Object result = entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return toLong(result);
    }

    // =====================================================
    // TENDENCIA MENSUAL DE VENTAS
    // =====================================================

    @Override
    public List<TestResultMonthlyCountProjection> getTestResultSaleTrend(
            Instant startDate,
            Instant endDate) {

        String sql = """
            SELECT
                MONTH(r.created_at) AS month,
                MONTHNAME(r.created_at) AS monthName,
                r.testId AS testId,
                r.testName AS testName,
                COALESCE(SUM(r.unitPrice), 0) AS sale,
                COUNT(*) AS total

            FROM (

                SELECT
                    sc.created_at,
                    sc.test_id AS testId,
                    t.name AS testName,
                    sc.unit_price AS unitPrice
                FROM simple_chemistry sc
                INNER JOIN test t ON t.id = sc.test_id
                WHERE sc.created_at >= :startDate
                  AND sc.created_at < :endDate

                UNION ALL

                SELECT
                    s.created_at,
                    s.test_id AS testId,
                    t.name AS testName,
                    s.unit_price AS unitPrice
                FROM stools s
                INNER JOIN test t ON t.id = s.test_id
                WHERE s.created_at >= :startDate
                  AND s.created_at < :endDate

                UNION ALL

                SELECT
                    tor.created_at,
                    tor.test_id AS testId,
                    t.name AS testName,
                    tor.unit_price AS unitPrice
                FROM torchs tor
                INNER JOIN test t ON t.id = tor.test_id
                WHERE tor.created_at >= :startDate
                  AND tor.created_at < :endDate

                UNION ALL

                SELECT
                    u.created_at,
                    u.test_id AS testId,
                    t.name AS testName,
                    u.unit_price AS unitPrice
                FROM urinalysis u
                INNER JOIN test t ON t.id = u.test_id
                WHERE u.created_at >= :startDate
                  AND u.created_at < :endDate

                UNION ALL

                SELECT
                    w.created_at,
                    w.test_id AS testId,
                    t.name AS testName,
                    w.unit_price AS unitPrice
                FROM widal_reaction w
                INNER JOIN test t ON t.id = w.test_id
                WHERE w.created_at >= :startDate
                  AND w.created_at < :endDate

            ) r

            GROUP BY
                MONTH(r.created_at),
                MONTHNAME(r.created_at),
                r.testId,
                r.testName

            ORDER BY
                MONTH(r.created_at),
                r.testName
            """;

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        List<TestResultMonthlyCountProjection> result = rows.stream()
            .<TestResultMonthlyCountProjection>map(row ->
                new TestResultMonthlyCountProjection() {

                    @Override
                    public Integer getMonth() {
                        return toInteger(row[0]);
                    }

                    @Override
                    public String getMonthName() {
                        return (String) row[1];
                    }

                    @Override
                    public BigDecimal getSale() {
                        return toBigDecimal(row[4]);
                    }

                    @Override
                    public Long getTotal() {
                        return toLong(row[5]);
                    }
                }
            )
            .toList();

        return result;
    }

    // =====================================================
    // CONVERSIONES DE TIPOS
    // =====================================================

    private BigDecimal toBigDecimal(Object value) {

        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof Number) {
            return BigDecimal.valueOf(
                    ((Number) value).doubleValue());
        }

        return new BigDecimal(value.toString());
    }

    private Long toLong(Object value) {

        if (value == null) {
            return 0L;
        }

        return ((Number) value).longValue();
    }

    private Integer toInteger(Object value) {

        if (value == null) {
            return 0;
        }

        return ((Number) value).intValue();
    }
}
