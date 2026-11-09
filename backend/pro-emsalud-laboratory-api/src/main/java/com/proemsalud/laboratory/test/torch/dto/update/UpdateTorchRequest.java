package com.proemsalud.laboratory.test.torch.dto.update;

import com.proemsalud.laboratory.test.torch.domain.TorchIgG;
import com.proemsalud.laboratory.test.torch.domain.TorchIgM;

import jakarta.validation.constraints.NotNull;
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
public class UpdateTorchRequest {
	
	@NotNull(message = "El test es requerido")
	private Long testId;
	
	private TorchIgG torchIgG;
	
	private TorchIgM torchIgM;
}
