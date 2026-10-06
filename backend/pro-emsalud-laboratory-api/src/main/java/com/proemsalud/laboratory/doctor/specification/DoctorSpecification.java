package com.proemsalud.laboratory.doctor.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.doctor.filter.DoctorFilter;

import jakarta.persistence.criteria.Predicate;

public class DoctorSpecification {
	
	 public static Specification<Doctor> withFilters(DoctorFilter filter) {

	        return (root, query, cb) -> {

	            List<Predicate> predicates = new ArrayList<>();

	            if (hasText(filter.getFirstName())) {
	                predicates.add(
	                    cb.like(
	                        cb.lower(root.get("firstName")),
	                        "%" + filter.getFirstName().trim().toLowerCase() + "%"
	                    )
	                );
	            }

	            if (filter.getCreatedAtAfter() != null) {
	                predicates.add(
	                    cb.greaterThanOrEqualTo(
	                        root.get("createdAt"),
	                        filter.getCreatedAtAfter()
	                    )
	                );
	            }

	            if (filter.getCreatedAtBefore() != null) {
	                predicates.add(
	                    cb.lessThanOrEqualTo(
	                        root.get("createdAt"),
	                        filter.getCreatedAtBefore()
	                    )
	                );
	            }

	            if (filter.getUpdatedAtAfter() != null) {
	                predicates.add(
	                    cb.greaterThanOrEqualTo(
	                        root.get("updatedAt"),
	                        filter.getUpdatedAtAfter()
	                    )
	                );
	            }

	            if (filter.getUpdatedAtBefore() != null) {
	                predicates.add(
	                    cb.lessThanOrEqualTo(
	                        root.get("updatedAt"),
	                        filter.getUpdatedAtBefore()
	                    )
	                );
	            }

	            return cb.and(predicates.toArray(new Predicate[0]));
	        };
	    }

	    private static boolean hasText(String value) {
	        return value != null && !value.trim().isEmpty();
	    }
}
