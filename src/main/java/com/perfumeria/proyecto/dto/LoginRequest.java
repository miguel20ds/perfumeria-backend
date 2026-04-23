package com.perfumeria.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requieridos para iniciar sesión")

public class LoginRequest {

    @Email
    @NotBlank
    @Schema(description = "Correo electrónico registrado", example = "juan@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
