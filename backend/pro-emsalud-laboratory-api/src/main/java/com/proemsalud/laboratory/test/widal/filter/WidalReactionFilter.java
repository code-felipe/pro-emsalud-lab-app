package com.proemsalud.laboratory.test.widal.filter;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WidalReactionFilter {
	
	
	
	private Instant createdAtAfter;

	private Instant createdAtBefore;

	private Instant updatedAtAfter;

	private Instant updatedAtBefore;
}
