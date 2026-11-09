package com.proemsalud.laboratory.test.torch.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TorchFilter {
	
	private String search;
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
