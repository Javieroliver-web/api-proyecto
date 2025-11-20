package com.sprintix.repository;

import com.sprintix.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    
    // Buscar todos los proyectos creados por un usuario específico
    List<Proyecto> findByCreadorId(int creadorId);
    
    // Buscar proyectos por estado (ej. "Activo", "Completado")
    List<Proyecto> findByEstado(String estado);
}