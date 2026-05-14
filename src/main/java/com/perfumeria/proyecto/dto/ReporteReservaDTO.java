package com.perfumeria.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ReporteReservaDTO {

    private Long id;
    private String usuarioNombre;
    private String usuarioEmail;
    private String perfumeNombre;
    private String perfumeMarca;
    private String fechaReserva;
    private String estado;
}
