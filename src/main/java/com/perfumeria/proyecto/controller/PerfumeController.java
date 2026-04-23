package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.model.Perfume;
import com.perfumeria.proyecto.service.FileStorageService;
import com.perfumeria.proyecto.service.PerfumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name =  "Perfumes", description = "Endpoints paea gestión del catálogo de perfumes")

public class PerfumeController {

    private final PerfumeService perfumeService;
    private final FileStorageService fileStorageService;

    @GetMapping
    @Operation(summary = "Listar perfumes", description = "Devuelve todos los pefumes del catálog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfume obtenida exitosamente")
    })
    public ResponseEntity<List<Perfume>> listar() {
        return ResponseEntity.ok(perfumeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar perfume por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfume encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfume no encontrado")
    })
    public ResponseEntity<Perfume> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfumeService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar perfumes por nombre")
    public ResponseEntity<List<Perfume>> buscarPorNombre(
            @Parameter(description = "Nombre a buscar", required = true) @RequestParam String nombre) {
        return ResponseEntity.ok(perfumeService.buscarPorNombre(nombre));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Crear perfume", description = " Solo accesible para admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfume creado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public ResponseEntity<Perfume> crear(@RequestBody Perfume perfume) {
        return ResponseEntity.ok(perfumeService.crear(perfume));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Actualizar perfume", description = "Solo accesible por ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfume actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfume no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<Perfume> actualizar(
            @Parameter(description = "ID del perfume a actualizar", required = true)@PathVariable Long id,@RequestBody Perfume perfume) {
        return ResponseEntity.ok(perfumeService.actualizar(id, perfume));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Eliminar perfume", description = "Solo accesible para ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfume eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Perfume no encontrado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del perfume a eliminar", required = true)@PathVariable Long id) {
        perfumeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Subir imagen", description = "Sube una imagen local para un perfume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen subida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Eror al subir la imagen"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public ResponseEntity<Map<String, String>> uploadImagen(
            @Parameter(description = "Archivo de imagen a subir", required = true)
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
