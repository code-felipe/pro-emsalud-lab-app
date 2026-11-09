package com.proemsalud.laboratory.test.stool.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.stool.filter.StoolFilter;
import com.proemsalud.laboratory.test.urinalysis.domain.Urinalysis;
import com.proemsalud.laboratory.test.urinalysis.filter.UrinalysisFilter;

import jakarta.persistence.criteria.Predicate;

public class StoolSpecification {

	public static Specification<Stool> withFilters(StoolFilter filter, Long orderId, Long patientId) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (orderId != null) {
				predicates.add(cb.equal(root.get("order").get("id"), orderId));
			}

			if (patientId != null) {
				predicates.add(cb.equal(root.get("order").get("patient").get("id"), patientId));
			}

			// BUSCADOR POR COLOR
			if (hasText(filter.getSearch())) {

			    String search = "%" + filter.getSearch().trim().toLowerCase() + "%";

			    predicates.add(
			        cb.or(
			            cb.like(cb.lower(root.get("urinalysisResult").get("color")), search),
			            cb.like(cb.lower(root.get("urinalysisResult").get("consistency")), search)
			        )
			    );
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
