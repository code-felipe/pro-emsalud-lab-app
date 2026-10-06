package com.proemsalud.laboratory.catalog.filter;


import java.time.Instant;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCategoryFilter {
	
	private String name;
	
	private Boolean active;
	
    private Instant createdAtAfter;

    private Instant createdAtBefore;

    private Instant updatedAtAfter;

    private Instant updatedAtBefore;
}
