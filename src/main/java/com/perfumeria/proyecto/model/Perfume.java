package com.perfumeria.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "perfumes")
@Data

public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String marca;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private String imagenUrl;

    private Integer stock = 0;
}
