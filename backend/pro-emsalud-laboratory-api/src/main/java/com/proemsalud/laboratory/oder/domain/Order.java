package com.proemsalud.laboratory.oder.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.proemsalud.laboratory.doctor.domain.Doctor;
import com.proemsalud.laboratory.oder.enumerate.OrderStatus;
import com.proemsalud.laboratory.patient.domain.Patient;
import com.proemsalud.laboratory.result.domain.TestResult;
import com.proemsalud.laboratory.test.simpleschemistry.domain.SimpleChemistry;
import com.proemsalud.laboratory.test.stool.domain.Stool;
import com.proemsalud.laboratory.test.torch.domain.Torch;
import com.proemsalud.laboratory.test.urinalysis.domain.Urinalysis;
import com.proemsalud.laboratory.test.widal.domain.WidalReaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus status;
	
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;
	
//	@Builder.Default 
//	@OneToMany(
//			mappedBy = "order", fetch = FetchType.LAZY,
//			cascade = CascadeType.ALL, orphanRemoval = true)//
//	private List<TestResult> results = new ArrayList<>(); 
//	
	@Builder.Default //Avoid un-expected error when creates and  calculate the total price
	@OneToMany(
			mappedBy = "order", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SimpleChemistry> simpleChemistries = new ArrayList<>(); 
	

	@Builder.Default //Avoid un-expected error when creates and  calculate the total price
	@OneToMany(
			mappedBy = "order", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WidalReaction> widalReactions = new ArrayList<>(); 
	
	@Builder.Default //Avoid un-expected error when creates and  calculate the total price
	@OneToMany(
			mappedBy = "order", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Urinalysis> urinalysis = new ArrayList<>(); 
	
	@Builder.Default //Avoid un-expected error when creates and  calculate the total price
	@OneToMany(
			mappedBy = "order", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Stool> stools = new ArrayList<>();
	
	@Builder.Default //Avoid un-expected error when creates and  calculate the total price
	@OneToMany(
			mappedBy = "order", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Torch> torchs = new ArrayList<>(); 
	
	
	
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
	
	public BigDecimal calcTotalPriceSimpleChemistry() {
	    return simpleChemistries.stream()
	            .map(result -> result.getUnitPrice())
	            .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
	}
	
	public BigDecimal calcTotalPriceWidalReaction() {
	    return widalReactions.stream()
	            .map(result -> result.getUnitPrice())
	            .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
	}
	
	public BigDecimal calcTotalUrinalysis() {
		return urinalysis.stream()
	            .map(result -> result.getUnitPrice())
	            .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
	}
	
	public BigDecimal calcTotalStool() {
		return stools.stream()
	            .map(result -> result.getUnitPrice())
	            .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
	}
	
	public BigDecimal calcTotalTorch() {
		return torchs.stream()
	            .map(result -> result.getUnitPrice())
	            .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
	}
}
