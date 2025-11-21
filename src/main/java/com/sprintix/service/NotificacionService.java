package com.sprintix.service;

import com.sprintix.entity.Notificacion;
import com.sprintix.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> listarPorUsuario(int usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    public List<Notificacion> listarNoLeidas(int usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
    }

    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }
    
    public void marcarComoLeida(int id) {
        notificacionRepository.findById(id).ifPresent(n -> {
            n.setLeida(true);
            notificacionRepository.save(n);
        });
    }

    // --- NUEVOS MÉTODOS ---
    
    public void marcarTodasComoLeidas(int usuarioId) {
        List<Notificacion> noLeidas = notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);
        noLeidas.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(noLeidas);
    }

    public void eliminar(int id) {
        notificacionRepository.deleteById(id);
    }
}