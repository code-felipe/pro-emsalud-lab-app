package com.proemsalud.laboratory.patient.domain;



import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.proemsalud.laboratory.oder.domain.Order;
import com.proemsalud.laboratory.patient.enumerate.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "father_last_name")
	private String fatherLastName;
	
	@Column(name = "mother_last_name")
	private String motherLastName;
	
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "primary_phone_number", nullable = false)
	private String primaryPhoneNumber;
	
	@Column(name = "optional_phone_number")
	private String optionalPhoneNumber;
	
	private String city;
	
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
	
	@PrePersist
	protected void prePersist() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	protected void preUpdate() {

		updatedAt = Instant.now();
	}
	
	public String getFullName() {
		return this.firstName + " " + fatherLastName;
	}
	
	public String calculateAgeDisplay() {
		
	    Period period = Period.between(this.dateOfBirth, LocalDate.now());

	    if (period.getYears() < 1) {
	        int totalMonths = period.getYears() * 12 + period.getMonths();
	        return totalMonths + (totalMonths == 1 ? " mes" : " meses");
	    }

	    return period.getYears() + (period.getYears() == 1 ? " año" : " años");
	}
}
