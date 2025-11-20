package com.sprintix.repository;

import com.sprintix.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Método mágico: Spring crea la consulta SQL automáticamente por el nombre
    Optional<Usuario> findByEmail(String email);
    
    // Para verificar si existe al registrarse
    boolean existsByEmail(String email);
}