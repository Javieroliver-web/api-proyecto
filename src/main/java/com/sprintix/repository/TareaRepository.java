package com.sprintix.repository;

import com.sprintix.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    
    List<Tarea> findByProyectoId(int proyectoId);
    
    List<Tarea> findByUsuariosAsignados_Id(int usuarioId);

    List<Tarea> findByUsuariosFavoritos_Id(int usuarioId);

    // --- NUEVO: Filtrar tareas de un proyecto por estado ---
    List<Tarea> findByProyectoIdAndEstado(int proyectoId, String estado);
}