package com.sprintix.controller;

// 1. IMPORTS CORRECTOS (Verifica que coincidan con tus paquetes)
import com.sprintix.dto.AuthResponseDTO;
import com.sprintix.dto.LoginDTO;
import com.sprintix.dto.UsuarioCreateDTO;
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

    // --- REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UsuarioCreateDTO createDTO) {
        try {
            // 2. CONVERSIÓN MANUAL (El arreglo clave)
            // El servicio espera 'Usuario', pero recibimos 'UsuarioCreateDTO'.
            // Creamos la entidad y pasamos los datos:
            Usuario usuario = new Usuario();
            usuario.setNombre(createDTO.getNombre());
            usuario.setApellido(createDTO.getApellido());
            usuario.setEmail(createDTO.getEmail());
            usuario.setPassword(createDTO.getPassword());
            usuario.setRol("usuario"); // Rol por defecto si no viene en el DTO

            // 3. LLAMADA AL SERVICIO (Usando la entidad 'usuario')
            Usuario nuevoUsuario = authService.registrar(usuario);
            
            // 4. GENERAR TOKEN
            String token = jwtUtil.generateToken(nuevoUsuario.getEmail(), nuevoUsuario.getRol());
            
            // 5. RESPUESTA
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
}