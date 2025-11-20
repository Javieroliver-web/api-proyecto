package com.sprintix.repository;

import com.sprintix.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    
    // Obtener todas las tareas de un proyecto
    List<Tarea> findByProyectoId(int proyectoId);
    
    // Obtener tareas asignadas a un usuario específico
    // Nota: Como es una relación ManyToMany, usamos el nombre de la propiedad en Tarea
    List<Tarea> findByUsuariosAsignados_Id(int usuarioId);
}