package com.sprintix.controller;

import com.sprintix.dto.AuthResponseDTO;
import com.sprintix.dto.LoginDTO;
import com.sprintix.entity.Usuario;
import com.sprintix.service.AuthService;
import com.sprintix.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody Usuario usuario) {
        // Nota: Idealmente recibirías UsuarioCreateDTO y lo convertirías a Usuario aquí
        try {
            Usuario nuevoUsuario = authService.registrar(usuario);
            // Generar token inmediato al registrar
            String token = jwtUtil.generateToken(nuevoUsuario.getEmail(), nuevoUsuario.getRol());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponseDTO(true, "Registro exitoso", token, nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponseDTO(false, "Error al registrar: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = authService.login(loginDTO.getEmail(), loginDTO.getPassword());

        if (usuario != null) {
            String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());
            return ResponseEntity.ok(new AuthResponseDTO(true, "Login exitoso", token, usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(false, "Credenciales inválidas"));
        }
    }
}