package com.sprintix.repository;

import com.sprintix.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    
    List<Proyecto> findByCreadorId(int creadorId);
    
    List<Proyecto> findByEstado(String estado);

    // --- NUEVO: Búsqueda por nombre (ignora mayúsculas/minúsculas) ---
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);
}