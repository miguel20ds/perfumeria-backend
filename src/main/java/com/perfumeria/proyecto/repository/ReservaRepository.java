package com.perfumeria.proyecto.repository;

import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuario(Usuario usuario);
    List<Reserva> findByUsuarioId(Long usuarioId);
}
