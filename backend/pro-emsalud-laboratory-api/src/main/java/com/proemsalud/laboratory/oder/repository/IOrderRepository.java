package com.proemsalud.laboratory.oder.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.dto.response.PendingOrderNotificationResponse;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	
	public Optional<Order> findByIdAndPatientId(Long id, Long patientId);
	
	public Page<Order> findAllByPatientId(Long patientId, Pageable pageable);
	
	public boolean existsByDoctorId(Long doctorId);
	
    long countByStatusIn(Collection<OrderStatus> statuses);
    
    @Query("""
            SELECT new com.proemsalud.laboratory.oder.dto.response.PendingOrderNotificationResponse(
                p.id,
                CONCAT(p.firstName, ' ', p.fatherLastName),
                COUNT(o)
            )
            FROM Order o
            JOIN o.patient p
            WHERE o.status IN :statuses
            GROUP BY p.id, p.firstName, p.fatherLastName
            ORDER BY MAX(o.createdAt) DESC
            """)
    List<PendingOrderNotificationResponse> findOrderNotifications(
            @Param("statuses") Collection<OrderStatus> statuses
    );

}
