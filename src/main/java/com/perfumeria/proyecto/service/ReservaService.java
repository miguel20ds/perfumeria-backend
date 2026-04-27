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
    private final MessageService messageService;

    public Reserva crear(Long perfumeId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(messageService.get("auth.usuario.no.encontrado")));

        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new RuntimeException(messageService.get("perfume.no.encontrado")));

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setPerfume(perfume);
        reserva.setEstado(Reserva.Estado.PENDIENTE);

        return reservaRepository.save(reserva);
    }

    public List<Reserva> misReservas(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(messageService.get("auth.usuario.no.encontrado")));
        return reservaRepository.findByUsuario(usuario);
    }

    public void  eliminar(Long id, String email) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageService.get("reserva.no.encontrada")));

        if (!reserva.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException(messageService.get("reserva.sin.permiso"));
        }

        reservaRepository.deleteById(id);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva marcarEntregado(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageService.get("reserva.no.encontrada")));
        reserva.setEstado((Reserva.Estado.ENTREGADO));
        return  reservaRepository.save(reserva);
    }

    public void eliminarComoAdmin(Long id) {
        reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageService.get("reserva.no.encontrada")));
        reservaRepository.deleteById(id);
    }
}
