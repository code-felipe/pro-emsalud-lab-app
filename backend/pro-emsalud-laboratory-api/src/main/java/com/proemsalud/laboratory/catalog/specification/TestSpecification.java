package com.proemsalud.laboratory.catalog.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.filter.TestFilter;

import jakarta.persistence.criteria.Predicate;

public class TestSpecification {

	public static Specification<Test> withFilters(TestFilter filter) {

		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (hasText(filter.getName())) {
				predicates.add(
					cb.like(
						cb.lower(root.get("name")),
						"%" + filter.getName().trim().toLowerCase() + "%"
					)
				);
			}

			if (filter.getActive() != null) {
				predicates.add(cb.equal(root.get("active"), filter.getActive()));
			}

			if (filter.getPriceMin() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getPriceMin()));
			}

			if (filter.getPriceMax() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getPriceMax()));
			}

			if (hasText(filter.getTestCategoryName())) {
				predicates.add(
					cb.like(
						cb.lower(root.get("category").get("name")),
						"%" + filter.getTestCategoryName().trim().toLowerCase() + "%"
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