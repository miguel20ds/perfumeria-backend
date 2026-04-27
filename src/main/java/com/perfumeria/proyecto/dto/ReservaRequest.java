package com.perfumeria.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear una reserva")

public class ReservaRequest {

    @NotNull(message = "{validation.perfume.obligatorio}")
    @Schema(description = "ID del perfume a reserva", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long perfumeId;
}
