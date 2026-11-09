package com.proemsalud.laboratory.test.widal.domain;

import com.proemsalud.laboratory.test.widal.enumerate.WidalTitulation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class WidalResult {
	
	private static final String REQUIRED_MESSAGE =
            "Debes marcar un valor: Positivo o Negativo";

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_1_20", nullable = false)
    private Boolean salmonellaTyphiO_1_20;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_1_40", nullable = false)
    private Boolean salmonellaTyphiO_1_40;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_1_80", nullable = false)
    private Boolean salmonellaTyphiO_1_80;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_1_160", nullable = false)
    private Boolean salmonellaTyphiO_1_160;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_1_320", nullable = false)
    private Boolean salmonellaTyphiO_1_320;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_1_20", nullable = false)
    private Boolean salmonellaTyphiH_1_20;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_1_40", nullable = false)
    private Boolean salmonellaTyphiH_1_40;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_1_80", nullable = false)
    private Boolean salmonellaTyphiH_1_80;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_1_160", nullable = false)
    private Boolean salmonellaTyphiH_1_160;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_1_320", nullable = false)
    private Boolean salmonellaTyphiH_1_320;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_o_result", nullable = false)
    private Boolean salmonellaTyphiOResult;

    @NotNull(message = REQUIRED_MESSAGE)
    @Column(name = "salmonella_typhi_h_result", nullable = false)
    private Boolean salmonellaTyphiHResult;

    @Enumerated(EnumType.STRING)
    @Column(name = "salmonella_typhi_o_result_titulation", nullable = false)
    private WidalTitulation salmonellaTyphiOResultTitulation;

    @Enumerated(EnumType.STRING)
    @Column(name = "salmonella_typhi_h_result_titulation", nullable = false)
    private WidalTitulation salmonellaTyphiHResultTitulation;

    @Column(name = "observation", length = 255, nullable = true)
    private String observation;
}
