package com.perfumeria.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ReporteUsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private String fechaRegistro;
}
