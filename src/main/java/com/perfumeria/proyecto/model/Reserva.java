package com.perfumeria.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@Schema(description = "Entidad que representa una reserva de perfume")

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la reserva", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario que realizó la reserva")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    @Schema(description = "Perfume reservado")
    private Perfume perfume;

    @Column(name = "fecha_reserva")
    @Schema(description = "Fecha y hora en que se realizó la reserva", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaReserva = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado actual de la reserva", example = "PENDIENTE")
    private Estado estado = Estado.PENDIENTE;

    public enum Estado {
        PENDIENTE, ENTREGADO
    }
}
