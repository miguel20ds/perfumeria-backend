package com.perfumeria.proyecto.controller;

import com.perfumeria.proyecto.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Endpoints para generación de reportes PDF")
@SecurityRequirement(name = "Bearer")

public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/perfumes")
    @Operation(summary = "Reporte de perfumes", description = "Genera un PDF con todos los perfumes del catálogo")
    public ResponseEntity<byte[]> reportePerfumes() throws JRException {
        byte[] pdf = reporteService.generarReportePerfumes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_perfumes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/reservas")
    @Operation(summary = "Reporte de reservas", description = "Genera un PDF con todas las reservas del sistema")
    public ResponseEntity<byte[]> reporteReservas() throws JRException {
        byte[] pdf = reporteService.generarReporteReservas();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_reservas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Reporte de usuarios", description = "Genera un PDF con todos los usuarios registrados")
    public ResponseEntity<byte[]> reporteUsuarios() throws JRException {
        byte[] pdf = reporteService.generarReporteUsuarios();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_usuarios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
