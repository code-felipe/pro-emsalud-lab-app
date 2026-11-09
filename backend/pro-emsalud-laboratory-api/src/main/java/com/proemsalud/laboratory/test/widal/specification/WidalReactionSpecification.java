package com.proemsalud.laboratory.test.widal.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.test.widal.domain.WidalReaction;
import com.proemsalud.laboratory.test.widal.filter.WidalReactionFilter;

import jakarta.persistence.criteria.Predicate;

public class WidalReactionSpecification {
	
	public static Specification<WidalReaction> withFilters(
			WidalReactionFilter filter, Long orderId,
			Long patientId) {
		
		return (root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();
	        
	    	if (orderId != null) {
				predicates.add(cb.equal(root.get("order").get("id"), orderId));
			}
	    	
	    	if (patientId != null) {
	    	    predicates.add(cb.equal(root.get("order").get("patient").get("id"), patientId));
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
