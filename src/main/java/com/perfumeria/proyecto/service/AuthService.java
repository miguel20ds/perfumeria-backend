package com.perfumeria.proyecto.service;

import com.perfumeria.proyecto.dto.AuthResponse;
import com.perfumeria.proyecto.dto.LoginRequest;
import com.perfumeria.proyecto.dto.RegistroRequest;
import com.perfumeria.proyecto.exception.EmailDuplicadoException;
import com.perfumeria.proyecto.model.Usuario;
import com.perfumeria.proyecto.repository.UsuarioRepository;
import com.perfumeria.proyecto.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse registro(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())){
            throw new EmailDuplicadoException("El email ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Usuario.Rol.USER);

        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getEmail(),usuario.getRol().name());
        return new AuthResponse(token, usuario.getNombre(), usuario.getEmail(), usuario.getRol().name());
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorecta");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getNombre(), usuario.getEmail(), usuario.getRol().name());
    }
}