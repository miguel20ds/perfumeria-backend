package com.perfumeria.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "perfumes")
@Data
@Schema(description = "Entidad que representa un perfume en el catálogo")

public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del perfume", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del perfume", example = "Bleu de Chanel", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Marca del perfume", example = "Chanel", requiredMode = Schema.RequiredMode.REQUIRED)
    private String marca;

    @Column(length = 1000)
    @Schema(description = "Descripción detallada del perfume", example = "Fragancia fresca y amaderada para hombre")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Precio del perfume en pesos colombianos", example = "150000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precio;

    @Schema(description = "URL de la imagen del perfume", example = "http:ejemplo.com/imagen.jpg")
    private String imagenUrl;

    @Schema(description = "Cantidad disponible en stock", example = "10")
    private Integer stock = 0;
}
