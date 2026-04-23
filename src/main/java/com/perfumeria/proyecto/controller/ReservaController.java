package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.dto.ReservaRequest;
import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Endpoints para gestión de reservas de usuarios")
@SecurityRequirement(name = "Bearer")

public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @Operation(summary = "Crear reserva", description = "Crear una nueva reserva para el usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfume no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaRequest request,
                                         Authentication authentication) {
        String email = authentication.getName();
        return  ResponseEntity.ok(reservaService.crear(request.getPerfumeId(), email));
    }

    @GetMapping("/mis-reservas")
    @Operation(summary = "Ver mis reservas", description = "Devuelve las reservas del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas obtenidas exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public  ResponseEntity<List<Reserva>> misReservas(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservaService.misReservas(email));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Cancela una reserva del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva canceada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "403", description = "No tienes permiso para eliminar esta reserva")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la reserva a eliminar", required = true) @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        reservaService.eliminar(id, email);
        return ResponseEntity.noContent().build();
    }
}
