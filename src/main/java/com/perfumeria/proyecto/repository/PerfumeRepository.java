package com.perfumeria.proyecto.repository;

import com.perfumeria.proyecto.model.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<Perfume> findByNombreContainingIgnoreCase(String nombre);
    List<Perfume> findByMarcaIgnoreCase(String marca);
}
