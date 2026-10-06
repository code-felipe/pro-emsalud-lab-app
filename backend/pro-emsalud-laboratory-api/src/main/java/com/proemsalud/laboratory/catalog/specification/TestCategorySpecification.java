package com.proemsalud.laboratory.catalog.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.catalog.filter.TestCategoryFilter;

import jakarta.persistence.criteria.Predicate;

public class TestCategorySpecification {
	
	public static Specification<TestCategory> withFilters(TestCategoryFilter filter) {

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
