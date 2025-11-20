package com.sprintix.service;

import com.sprintix.entity.Usuario;
import com.sprintix.repository.UsuarioRepository;
// Asegúrate de importar tus utilidades antiguas aquí:
import util.PasswordUtil; 
// import util.JWTUtil; // Si vas a generar el token aquí

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(Usuario usuario) {
        // Lógica de hash de contraseña (adaptada de tu código antiguo)
        String salt = PasswordUtil.getSalt(30);
        String hashedPassword = PasswordUtil.generateSecurePassword(usuario.getPassword(), salt);
        usuario.setPassword(salt + ":" + hashedPassword);
        
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar password
            boolean passwordMatch = PasswordUtil.verifyUserPassword(
                password, 
                usuario.getPassword(), 
                "" // El salt ya va incluido en la cadena guardada en tu implementación
            );
            
            if (passwordMatch) {
                return usuario;
            }
        }
        return null;
    }
}