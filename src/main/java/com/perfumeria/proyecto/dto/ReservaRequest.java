package com.perfumeria.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class ReservaRequest {

    @NotNull(message = "El perfume es obligatorio")
    private Long perfumeId;
}
