package com.perfumeria.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para registrar un nuevo usuario")

public class RegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Email(message = "Email inválido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Correo electrónico del usuario", example = "juan@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del usuario, mínimo 6 caracteres", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
