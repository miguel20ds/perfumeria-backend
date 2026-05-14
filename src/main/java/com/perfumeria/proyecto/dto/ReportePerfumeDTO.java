package com.perfumeria.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ReportePerfumeDTO {

    private Long id;
    private String nombre;
    private String marca;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
