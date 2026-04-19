package com.perfumeria.proyecto.service;

import com.perfumeria.proyecto.model.Perfume;
import com.perfumeria.proyecto.model.Reserva;
import com.perfumeria.proyecto.model.Usuario;
import com.perfumeria.proyecto.repository.PerfumeRepository;
import com.perfumeria.proyecto.repository.ReservaRepository;
import com.perfumeria.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfumeRepository perfumeRepository;

    public Reserva crear(Long perfumeId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado"));

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado"));

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setPerfume(perfume);
        reserva.setEstado(Reserva.Estado.PENDIENTE);

        return reservaRepository.save(reserva);
    }

    public List<Reserva> misReservas(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return reservaRepository.findByUsuario(usuario);
    }

    public void  eliminar(Long id, String email) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para eliminar esta reserva");
        }

        reservaRepository.deleteById(id);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva marcarEntregado(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado((Reserva.Estado.ENTREGADO));
        return  reservaRepository.save(reserva);
    }

    public void eliminarComoAdmin(Long id) {
        reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reservaRepository.deleteById(id);
    }
}
