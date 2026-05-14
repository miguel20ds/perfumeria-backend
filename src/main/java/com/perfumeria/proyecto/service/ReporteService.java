package com.perfumeria.proyecto.service;

import com.perfumeria.proyecto.dto.ReportePerfumeDTO;
import com.perfumeria.proyecto.dto.ReporteReservaDTO;
import com.perfumeria.proyecto.dto.ReporteUsuarioDTO;
import com.perfumeria.proyecto.repository.PerfumeRepository;
import com.perfumeria.proyecto.repository.ReservaRepository;
import com.perfumeria.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReporteService {

    private final PerfumeRepository perfumeRepository;
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarReportePerfumes() throws JRException {
        List<ReportePerfumeDTO> datos = perfumeRepository.findAll()
                .stream()
                .map(p -> new ReportePerfumeDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getMarca(),
                        p.getDescripcion(),
                        p.getPrecio(),
                        p.getStock()
                ))
                .collect(Collectors.toList());

        return generarPDF("reportes/reporte_perfumes.jrxml", datos);
    }

    public byte[] generarReporteReservas() throws JRException {
        List<ReporteReservaDTO> datos = reservaRepository.findAll()
                .stream()
                .map(r -> new ReporteReservaDTO(
                        r.getId(),
                        r.getUsuario().getNombre(),
                        r.getUsuario().getEmail(),
                        r.getPerfume().getNombre(),
                        r.getPerfume().getMarca(),
                        r.getFechaReserva().format(FORMATTER),
                        r.getEstado().name()
                ))
                .collect(Collectors.toList());

        return generarPDF("reportes/reporte_reservas.jrxml", datos);
    }

    public byte[] generarReporteUsuarios() throws JRException {
        List<ReporteUsuarioDTO> datos = usuarioRepository.findAll()
                .stream()
                .map(u -> new ReporteUsuarioDTO(
                        u.getId(),
                        u.getNombre(),
                        u.getEmail(),
                        u.getRol().name(),
                        u.getFechaRegistro().format(FORMATTER)
                ))
                .collect(Collectors.toList());

        return generarPDF("reportes/reporte_usuarios.jrxml", datos);
    }

    private byte[] generarPDF(String rutaPlantilla, List<?> datos) throws JRException {
        try {
            InputStream inputStream = new ClassPathResource(rutaPlantilla).getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
            Map<String, Object> parametros = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new JRException("Error al generar el reporte: " + e.getMessage());
        }
    }
}
