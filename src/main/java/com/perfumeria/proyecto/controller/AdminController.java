package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.model.Usuario;
import com.perfumeria.proyecto.repository.UsuarioRepository;
import com.perfumeria.proyecto.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor

public class AdminController {

    private final ReservaService reservaService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarTodas());
    }

    @PutMapping("/reservas/{id}/entregar")
    public ResponseEntity<Reserva> marcarEntregado(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.marcarEntregado(id));
    }

    @DeleteMapping("/reservas/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarComoAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
