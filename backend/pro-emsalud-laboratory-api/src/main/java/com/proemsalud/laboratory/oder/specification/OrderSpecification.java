package com.proemsalud.laboratory.oder.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.oder.filter.OrderFilter;

import jakarta.persistence.criteria.Predicate;

public class OrderSpecification {

	public static Specification<Order> withFilters(OrderFilter filter, Long patientId) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (patientId != null) {
				predicates.add(cb.equal(root.get("patient").get("id"), patientId));
			}

			if (hasText(filter.getDoctorName())) {
				predicates.add(cb.like(cb.lower(root.get("doctor").get("firstName")),
						"%" + filter.getDoctorName().trim().toLowerCase() + "%"));
			}

			if (filter.getStatus() != null) {
				predicates.add(cb.equal(root.get("status"), filter.getStatus()));
			}

			if (filter.getCreatedAtAfter() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtAfter()));
			}

			if (filter.getCreatedAtBefore() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getCreatedAtBefore()));
			}

			if (filter.getUpdatedAtAfter() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtAfter()));
			}

			if (filter.getUpdatedAtBefore() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), filter.getUpdatedAtBefore()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}


	private static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}
}
