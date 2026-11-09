package com.proemsalud.laboratory.test.torch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TorchIgG {
	
	@Column(name = "toxoplasma_igg")
	private String toxoplasma;

	@Column(name = "cytomegalovirus_igg")
	private String cytomegalovirus ;

	@Column(name = "rubella_igg")
	private String rubella;
	
	@Column(name = "herpes_igg")
	private String herpes;
}
