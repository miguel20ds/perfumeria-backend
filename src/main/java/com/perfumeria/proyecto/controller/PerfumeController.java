package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.model.Perfume;
import com.perfumeria.proyecto.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfumes")
@RequiredArgsConstructor

public class PerfumeController {

    private final PerfumeService perfumeService;

    @GetMapping
    public ResponseEntity<List<Perfume>> listar() {
        return ResponseEntity.ok(perfumeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfumeService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Perfume>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(perfumeService.buscarPorNombre(nombre));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Perfume> crear(@RequestBody Perfume perfume) {
        return ResponseEntity.ok(perfumeService.crear(perfume));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Perfume> actualizar(@PathVariable Long id,@RequestBody Perfume perfume) {
        return ResponseEntity.ok(perfumeService.actualizar(id, perfume));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfumeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
