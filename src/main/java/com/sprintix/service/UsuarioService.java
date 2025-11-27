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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // --- CORRECCIÓN DASHBOARD ---
    public DashboardDTO obtenerDashboard(int usuarioId) {
        
        // 1. Obtener usuario para acceder a sus proyectos (Creados y Asignados)
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        List<Tarea> pendientes = new ArrayList<>();
        List<Proyecto> misProyectos = new ArrayList<>();

        if (usuario != null) {
            // Unimos los proyectos creados y en los que participa
            Set<Proyecto> setProyectos = new HashSet<>();
            setProyectos.addAll(usuario.getProyectosCreados());
            setProyectos.addAll(usuario.getProyectosAsignados());
            
            misProyectos = new ArrayList<>(setProyectos);

            // 2. Extraemos las tareas de esos proyectos que NO estén completadas
            pendientes = setProyectos.stream()
                .flatMap(p -> p.getTareas().stream()) // Obtenemos el stream de tareas de cada proyecto
                .filter(t -> t.getEstado() == null || !"completada".equalsIgnoreCase(t.getEstado())) // Filtramos estado
                .sorted((t1, t2) -> {
                    // Ordenamos por fecha límite (las más urgentes primero)
                    if (t1.getFecha_limite() == null) return 1;
                    if (t2.getFecha_limite() == null) return -1;
                    return t1.getFecha_limite().compareTo(t2.getFecha_limite());
                })
                .collect(Collectors.toList());
        }
        
        // 3. Notificaciones no leídas (lógica original)
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioIdAndLeidaFalse(usuarioId);

        // Construir DTO con los datos calculados
        return new DashboardDTO(
            pendientes.size(),
            misProyectos.size(),
            notificaciones.size(),
            misProyectos.stream().limit(5).collect(Collectors.toList()), // Últimos 5 proyectos
            pendientes.stream().limit(5).collect(Collectors.toList())    // Próximas 5 tareas de mis proyectos
        );
    }
}