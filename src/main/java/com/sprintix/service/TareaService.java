package com.sprintix.service;

import com.sprintix.dto.TareaCreateDTO;
import com.sprintix.entity.Proyecto;
import com.sprintix.entity.Tarea;
import com.sprintix.entity.Usuario;
import com.sprintix.repository.ProyectoRepository;
import com.sprintix.repository.TareaRepository;
import com.sprintix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoRepository proyectoRepository; 

    /**
     * Crear tarea desde DTO
     */
    public Tarea crearDesdeDTO(TareaCreateDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(dto.getProyecto_id())
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(dto.getEstado());
        tarea.setFecha_limite(dto.getFecha_limite());
        tarea.setProyecto(proyecto);

        return tareaRepository.save(tarea);
    }

    /**
     * ✅ NUEVO: Forzar flush para asegurar persistencia inmediata en BD
     * Esto garantiza que los cambios se escriben en la base de datos
     * ANTES de devolver la respuesta al cliente
     */
    public void flush() {
        tareaRepository.flush();
    }

    /**
     * Listar tareas por proyecto con filtro opcional de estado
     */
    public List<Tarea> listarPorProyecto(int proyectoId, String estado) {
        if (estado != null && !estado.isEmpty()) {
            return tareaRepository.findByProyectoIdAndEstado(proyectoId, estado);
        }
        return tareaRepository.findByProyectoId(proyectoId);
    }

    /**
     * Obtener tarea por ID
     */
    public Optional<Tarea> obtenerPorId(int id) {
        return tareaRepository.findById(id);
    }

    /**
     * Guardar o actualizar tarea
     */
    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    /**
     * Eliminar tarea por ID
     */
    public void eliminar(int id) {
        tareaRepository.deleteById(id);
    }

    /**
     * Asignar usuario a una tarea
     */
    public Tarea asignarUsuario(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.addUsuarioAsignado(usuario);
        return tareaRepository.save(tarea);
    }

    /**
     * Desasignar usuario de una tarea
     */
    public void desasignarUsuario(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.removeUsuarioAsignado(usuario);
        tareaRepository.save(tarea);
    }

    /**
     * Listar tareas asignadas a un usuario
     */
    public List<Tarea> listarAsignadasPorUsuario(int usuarioId) {
        return tareaRepository.findByUsuariosAsignados_Id(usuarioId);
    }

    /**
     * Listar tareas favoritas de un usuario
     */
    public List<Tarea> listarFavoritasPorUsuario(int usuarioId) {
        return tareaRepository.findByUsuariosFavoritos_Id(usuarioId);
    }

    /**
     * Marcar tarea como favorita para un usuario
     */
    public Tarea marcarComoFavorita(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.addUsuarioFavorito(usuario);
        return tareaRepository.save(tarea);
    }

    /**
     * Eliminar tarea de favoritos de un usuario
     */
    public void eliminarDeFavoritos(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.removeUsuarioFavorito(usuario);
        tareaRepository.save(tarea);
    }
}