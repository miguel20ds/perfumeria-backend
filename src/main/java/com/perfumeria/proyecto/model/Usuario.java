package com.perfumeria.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@Schema(description = "Entidad que representa un ususario en el sistema")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del usuario", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico del usuario", example = "juan@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contrasea encriptada del usuario", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Rol del ususario en el sistema", example = "USER")
    private Rol rol = Rol.USER;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha y hora de registro del usuario", example = "2024-01-15T10:30:30")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public enum Rol {
        USER, ADMIN
    }
}
