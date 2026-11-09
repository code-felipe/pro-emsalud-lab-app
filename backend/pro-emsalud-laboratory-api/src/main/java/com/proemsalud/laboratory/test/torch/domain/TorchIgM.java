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
public class TorchIgM {
	
	@Column(name = "toxoplasma_igm")
	private String toxoplasma;

	@Column(name = "cytomegalovirus_igm")
	private String cytomegalovirus ;

	@Column(name = "rubella_igm")
	private String rubella;
	
	@Column(name = "herpes_igm")
	private String herpes;
}
