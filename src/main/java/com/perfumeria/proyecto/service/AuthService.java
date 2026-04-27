package com.perfumeria.proyecto.service;

import com.perfumeria.proyecto.dto.AuthResponse;
import com.perfumeria.proyecto.dto.LoginRequest;
import com.perfumeria.proyecto.dto.RegistroRequest;
import com.perfumeria.proyecto.exception.CredencialesInvalidasException;
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
    private final MessageService messageService;

    public AuthResponse registro(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())){
            throw new EmailDuplicadoException(messageService.get("auth.email.duplicado"));
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
                .orElseThrow(() -> new CredencialesInvalidasException(messageService.get("auth.usuario.no.encontrado")));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException(messageService.get("auth.password.incorrecta"));
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getNombre(), usuario.getEmail(), usuario.getRol().name());
    }
}