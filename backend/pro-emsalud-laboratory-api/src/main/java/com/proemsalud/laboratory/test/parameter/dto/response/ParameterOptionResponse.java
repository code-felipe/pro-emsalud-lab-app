package com.proemsalud.laboratory.test.parameter.dto.response;

import java.time.Instant;

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
public class ParameterOptionResponse {
	
    private Long id;

    private String value;

    private Integer displayOrder;

    private Instant createdAt;

    private Instant updatedAt;
}
