package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.model.Usuario;
import com.perfumeria.proyecto.repository.UsuarioRepository;
import com.perfumeria.proyecto.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Administración", description = "Endpoint exclusivos para administrador")
@SecurityRequirement(name = "Bearer")
public class AdminController {

    private final ReservaService reservaService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usurios", description = "Devuelve todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado - solo ADMIN")
    })
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/reservas")
    @Operation(summary = "Listar todas las reservas", description = "Devuelve todas ls reservas del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado - solo ADMIN")
    })
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PutMapping("/reservas/{id}/entregar")
    @Operation(summary = "Marcar reserva como entregada", description = "Cambia el estado de una reserva a ENTREGADO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva marcada como entregada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado - solo ADMIN")
    })
    public ResponseEntity<Reserva> marcarEntregado(
            @Parameter(description = "ID de la reserva a entregar", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.marcarEntregado(id));
    }

    @DeleteMapping("/reservas/{id}")
    @Operation(summary = "Eliminar reserva como administrador", description = "elimina cualquier reserva del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<Void> eliminarReserva(
            @Parameter(description = "ID de la reserva a eliminar", required = true) @PathVariable Long id) {
        reservaService.eliminarComoAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
