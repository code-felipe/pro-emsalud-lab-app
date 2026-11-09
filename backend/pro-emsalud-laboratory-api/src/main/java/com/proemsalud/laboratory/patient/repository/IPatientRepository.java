package com.proemsalud.laboratory.patient.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.patient.dto.response.AllTestResultsHistory;

public interface IPatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

	@Query("""
			    SELECT COUNT(p.id)
			    FROM Patient p
			""")
	public Integer totalPatients();

	@Query(value = """
			SELECT
			    r.id AS id,
			    r.testId AS testId,
			    r.testName AS testName,
			    r.unitPrice AS unitPrice,
			    r.orderId AS orderId,
			    r.orderCreatedAt AS orderCreatedAt,
			    r.orderUpdatedAt AS orderUpdatedAt,
			    r.testType AS testType
			FROM (

			    SELECT
			        sc.id AS id,
			        sc.test_id AS testId,
			        t.name AS testName,
			        sc.unit_price AS unitPrice,
			        o.id AS orderId,
			        o.created_at AS orderCreatedAt,
			        o.updated_at AS orderUpdatedAt,
			        'SIMPLE_CHEMISTRY' AS testType
			    FROM simple_chemistry sc
			    INNER JOIN test t ON t.id = sc.test_id
			    INNER JOIN orders o ON o.id = sc.order_id
			    WHERE o.patient_id = :patientId

			    UNION ALL

			    SELECT
			        s.id,
			        s.test_id,
			        t.name,
			        s.unit_price,
			        o.id,
			        o.created_at,
			        o.updated_at,
			        'STOOL'
			    FROM stools s
			    INNER JOIN test t ON t.id = s.test_id
			    INNER JOIN orders o ON o.id = s.order_id
			    WHERE o.patient_id = :patientId

			    UNION ALL

			    SELECT
			        tor.id,
			        tor.test_id,
			        t.name,
			        tor.unit_price,
			        o.id,
			        o.created_at,
			        o.updated_at,
			        'TORCH'
			    FROM torchs tor
			    INNER JOIN test t ON t.id = tor.test_id
			    INNER JOIN orders o ON o.id = tor.order_id
			    WHERE o.patient_id = :patientId

			    UNION ALL

			    SELECT
			        u.id,
			        u.test_id,
			        t.name,
			        u.unit_price,
			        o.id,
			        o.created_at,
			        o.updated_at,
			        'URINALYSIS'
			    FROM urinalysis u
			    INNER JOIN test t ON t.id = u.test_id
			    INNER JOIN orders o ON o.id = u.order_id
			    WHERE o.patient_id = :patientId

			    UNION ALL

			    SELECT
			        w.id,
			        w.test_id,
			        t.name,
			        w.unit_price,
			        o.id,
			        o.created_at,
			        o.updated_at,
			        'WIDAL'
			    FROM widal_reaction w
			    INNER JOIN test t ON t.id = w.test_id
			    INNER JOIN orders o ON o.id = w.order_id
			    WHERE o.patient_id = :patientId

			) r

			ORDER BY
			    r.orderCreatedAt DESC,
			    r.testName ASC,
			    r.testType ASC,
			    r.id DESC
			""",

			countQuery = """
					SELECT
					    (SELECT COUNT(*)
					     FROM simple_chemistry sc
					     INNER JOIN orders o ON o.id = sc.order_id
					     WHERE o.patient_id = :patientId)

					    +

					    (SELECT COUNT(*)
					     FROM stools s
					     INNER JOIN orders o ON o.id = s.order_id
					     WHERE o.patient_id = :patientId)

					    +

					    (SELECT COUNT(*)
					     FROM torchs tor
					     INNER JOIN orders o ON o.id = tor.order_id
					     WHERE o.patient_id = :patientId)

					    +

					    (SELECT COUNT(*)
					     FROM urinalysis u
					     INNER JOIN orders o ON o.id = u.order_id
					     WHERE o.patient_id = :patientId)

					    +

					    (SELECT COUNT(*)
					     FROM widal_reaction w
					     INNER JOIN orders o ON o.id = w.order_id
					     WHERE o.patient_id = :patientId)
					""",

			nativeQuery = true)
	Page<AllTestResultsHistory> findAllTestResultsByPatient(@Param("patientId") Long patientId, Pageable pageable);

}
