package com.proemsalud.laboratory.test.simpleschemistry.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.catalog.domain.Test;
import com.proemsalud.laboratory.catalog.domain.TestCategory;
import com.proemsalud.laboratory.test.simpleschemistry.domain.SimpleChemistry;
import com.proemsalud.laboratory.test.simpleschemistry.filter.SimpleChemistryFilter;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class SimpleChemistrySpecification {
	
	public static Specification<SimpleChemistry> withFilters(
			SimpleChemistryFilter filter, Long orderId,
			Long patientId) {
		
		return (root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();
	        
	    	if (orderId != null) {
				predicates.add(cb.equal(root.get("order").get("id"), orderId));
			}
	    	
	    	if (patientId != null) {
	    	    predicates.add(cb.equal(root.get("order").get("patient").get("id"), patientId));
	    	}
	        // joins: SimpleChemistry -> Test -> TestCategory
	        Join<SimpleChemistry, Test> testJoin = root.join("test", JoinType.INNER);
	        Join<Test, TestCategory> categoryJoin = testJoin.join("category", JoinType.INNER);

	        if (hasText(filter.getSearch())) {
	            String value = "%" + filter.getSearch().toLowerCase() + "%";

	            Predicate searchPredicate = cb.or(
	                cb.like(cb.lower(testJoin.get("name")), value),
	                cb.like(cb.lower(categoryJoin.get("name")), value)
	            );

	            predicates.add(searchPredicate);
	        }
	        
	        if (filter.getPriceMin() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("unitPrice"), filter.getPriceMin()));
			}

			if (filter.getPriceMax() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("unitPrice"), filter.getPriceMax()));
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
	        // solo tests activos
	        predicates.add(cb.isTrue(testJoin.get("active")));
	        
	        return cb.and(predicates.toArray(new Predicate[0]));
	    };
	}

	
	
	private static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}
}
