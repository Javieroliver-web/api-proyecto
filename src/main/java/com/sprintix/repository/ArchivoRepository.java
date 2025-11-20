package com.sprintix.repository;

import com.sprintix.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Integer> {
    
    // Listar archivos de un proyecto
    List<Archivo> findByProyectoId(int proyectoId);
}