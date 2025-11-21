package com.sprintix.controller;

import com.sprintix.dto.AuthResponseDTO;
import com.sprintix.dto.LoginDTO;
import com.sprintix.dto.UsuarioCreateDTO;
import com.sprintix.entity.Usuario;
import com.sprintix.service.AuthService;
import com.sprintix.service.UsuarioService; // IMPORTANTE
import com.sprintix.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService; // Inyectamos esto para /me

    @Autowired
    private JWTUtil jwtUtil;

    // --- REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UsuarioCreateDTO createDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(createDTO.getNombre());
            usuario.setApellido(createDTO.getApellido());
            usuario.setEmail(createDTO.getEmail());
            usuario.setPassword(createDTO.getPassword());
            usuario.setRol("usuario"); 

            Usuario nuevoUsuario = authService.registrar(usuario);
            String token = jwtUtil.generateToken(nuevoUsuario.getEmail(), nuevoUsuario.getRol());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponseDTO(true, "Registro exitoso", token, nuevoUsuario));

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponseDTO(false, "Error: " + e.getMessage()));
        }
    }

    // --- LOGIN ---
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

    // --- NUEVO: OBTENER USUARIO ACTUAL (/me) ---
    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioActual(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorEmail(email);
        
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- NUEVO: LOGOUT (Simbólico en JWT) ---
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new AuthResponseDTO(true, "Sesión cerrada correctamente"));
    }
}