package com.perfumeria.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta devuelta tras registro o login exitoso")

public class AuthResponse {

    @Schema(description = "Token JWT generado para autenticación", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Nombre del ususario autenticado", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Email de usuario autenticado", example = "juan@email.com")
    private String email;

    @Schema(description = "Rol del usuario en el sistema", example = "USER")
    private String rol;
}
