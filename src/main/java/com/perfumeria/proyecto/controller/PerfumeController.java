package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.model.Perfume;
import com.perfumeria.proyecto.service.FileStorageService;
import com.perfumeria.proyecto.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/perfumes")
@RequiredArgsConstructor

public class PerfumeController {

    private final PerfumeService perfumeService;
    private final FileStorageService fileStorageService;

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

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadImagen(
            @RequestParam("file")MultipartFile file) {
        try {
            String nombreArchivo = fileStorageService.guardarImagen(file);
            String url = "/uploads/" + nombreArchivo;
            return  ResponseEntity.ok(Map.of("url", url));
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir la imagen"));
        }
    }


}
