package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.dto.ReservaRequest;
import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor

public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> crear(@Valid @RequestBody ReservaRequest request,
                                         Authentication authentication) {
        String email = authentication.getName();
        return  ResponseEntity.ok(reservaService.crear(request.getPerfumeId(), email));
    }

    @GetMapping("/mis-reservas")
    public  ResponseEntity<List<Reserva>> misReservas(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservaService.misReservas(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id,
                                          Authentication authentication) {
        String email = authentication.getName();
        reservaService.eliminar(id, email);
        return ResponseEntity.noContent().build();
    }
}
