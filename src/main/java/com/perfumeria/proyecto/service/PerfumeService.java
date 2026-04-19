package com.perfumeria.proyecto.service;

import com.perfumeria.proyecto.model.Perfume;
import com.perfumeria.proyecto.repository.PerfumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PerfumeService {

    private final PerfumeRepository perfumeRepository;

    public List<Perfume> listarTodos() {
        return perfumeRepository.findAll();
    }

    public Perfume buscarPorId(Long id) {
        return perfumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado"));
    }

    public List<Perfume> buscarPorNombre(String nombre) {
        return perfumeRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public  Perfume crear(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    public Perfume actualizar(Long id, Perfume datos) {
        Perfume perfume = buscarPorId(id);
        perfume.setNombre(datos.getNombre());
        perfume.setMarca(datos.getMarca());
        perfume.setDescripcion(datos.getDescripcion());
        perfume.setPrecio(datos.getPrecio());
        perfume.setImagenUrl(datos.getImagenUrl());
        perfume.setStock(datos.getStock());
        return perfumeRepository.save(perfume);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        perfumeRepository.deleteById(id);
    }
}
