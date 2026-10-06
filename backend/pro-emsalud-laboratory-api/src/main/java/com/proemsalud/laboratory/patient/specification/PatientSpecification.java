package com.proemsalud.laboratory.patient.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.patient.filter.PatientFilter;

import jakarta.persistence.criteria.Predicate;

public class PatientSpecification {
	
	public static Specification<Patient> withFilters(PatientFilter filter) {
		
		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if(hasText(filter.getSearch())) {
				String value = "%" + filter.getSearch().toLowerCase() + "%";
				
				Predicate searchPredicate = cb.or(
				cb.like(cb.lower(root.get("code")), value),
				cb.like(cb.lower(root.get("city")), value),
				cb.like(cb.lower(root.get("firstName")), value),
				cb.like(cb.lower(root.get("middleName")), value),
				cb.like(cb.lower(root.get("fatherLastName")), value),
				cb.like(cb.lower(root.get("motherLastName")), value),
				cb.like(cb.lower(root.get("primaryPhoneNumber")), value));
				
				predicates.add(searchPredicate);
			}
			
			if (filter.getGender() != null) {
				predicates.add(cb.equal(root.get("gender"), filter.getGender()));
			}
			
			if (filter.getDateOfBirth() != null) {
			    predicates.add(cb.equal(root.get("dateOfBirth"), filter.getDateOfBirth()));
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
