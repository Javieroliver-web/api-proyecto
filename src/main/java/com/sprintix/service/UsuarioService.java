package com.sprintix.service;

import com.sprintix.dto.DashboardDTO;
import com.sprintix.entity.Notificacion;
import com.sprintix.entity.Proyecto;
import com.sprintix.entity.Tarea;
import com.sprintix.entity.Usuario;
import com.sprintix.repository.NotificacionRepository;
import com.sprintix.repository.ProyectoRepository;
import com.sprintix.repository.TareaRepository;
import com.sprintix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Inyectamos los otros repositorios para el Dashboard
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void eliminar(int id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // --- NUEVO: GENERAR DASHBOARD ---
    public DashboardDTO obtenerDashboard(int usuarioId) {
        // 1. Tareas pendientes (asignadas y que no estén completadas)
        List<Tarea> tareasAsignadas = tareaRepository.findByUsuariosAsignados_Id(usuarioId);
        List<Tarea> pendientes = tareasAsignadas.stream()
                .filter(t -> !"completada".equalsIgnoreCase(t.getEstado()))
                .collect(Collectors.toList());

        // 2. Proyectos activos (creados por el usuario)
        List<Proyecto> proyectos = proyectoRepository.findByCreadorId(usuarioId);
        
        // 3. Notificaciones no leídas
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);

        // Construir DTO
        return new DashboardDTO(
            pendientes.size(),
            proyectos.size(),
            notificaciones.size(),
            proyectos.stream().limit(5).collect(Collectors.toList()), // Últimos 5 proyectos
            pendientes.stream().limit(5).collect(Collectors.toList()) // Próximas 5 tareas
        );
    }
}