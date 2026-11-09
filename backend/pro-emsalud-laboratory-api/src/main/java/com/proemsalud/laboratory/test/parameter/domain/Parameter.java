package com.proemsalud.laboratory.test.parameter.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.proemsalud.laboratory.test.parameter.enumerate.ExamType;
import com.proemsalud.laboratory.test.parameter.enumerate.InputType;
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
@Table(name = "parameters")
public class Parameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(name = "exam_type")
    @Enumerated(EnumType.STRING)
	private ExamType examType;
	
	@Column(name = "input_type")
    @Enumerated(EnumType.STRING)
	private InputType inputType;
	
	@Column(name = "display_order")
	private Integer displayOrder;
	
	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "updated_at")
	private Instant updatedAt;
	
	@Builder.Default
	@OneToMany(
			mappedBy = "parameter", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ParameterOption> options = new ArrayList<>(); 
	
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
}
