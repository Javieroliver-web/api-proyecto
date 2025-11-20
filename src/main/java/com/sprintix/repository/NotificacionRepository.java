package com.sprintix.repository;

import com.sprintix.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    
    // Todas las notificaciones de un usuario ordenadas por fecha (más reciente primero)
    List<Notificacion> findByUsuarioIdOrderByFechaDesc(int usuarioId);
    
    // Solo las no leídas (para el numerito rojo en la campana)
    List<Notificacion> findByUsuarioIdAndLeidaFalse(int usuarioId);
}