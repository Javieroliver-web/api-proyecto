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

    // Método para forzar la persistencia inmediata
    public void flush() {
        tareaRepository.flush();
    }

    public List<Tarea> listarPorProyecto(int proyectoId, String estado) {
        if (estado != null && !estado.isEmpty()) {
            // ACTUALIZADO: Llamada al método corregido con guion bajo
            return tareaRepository.findByProyecto_IdAndEstado(proyectoId, estado);
        }
        // ACTUALIZADO: Llamada al método corregido con guion bajo
        return tareaRepository.findByProyecto_Id(proyectoId);
    }

    public Optional<Tarea> obtenerPorId(int id) {
        return tareaRepository.findById(id);
    }

    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminar(int id) {
        tareaRepository.deleteById(id);
    }

    public Tarea asignarUsuario(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.addUsuarioAsignado(usuario);
        return tareaRepository.save(tarea);
    }

    public void desasignarUsuario(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.removeUsuarioAsignado(usuario);
        tareaRepository.save(tarea);
    }

    public List<Tarea> listarAsignadasPorUsuario(int usuarioId) {
        return tareaRepository.findByUsuariosAsignados_Id(usuarioId);
    }

    public List<Tarea> listarFavoritasPorUsuario(int usuarioId) {
        return tareaRepository.findByUsuariosFavoritos_Id(usuarioId);
    }

    public Tarea marcarComoFavorita(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.addUsuarioFavorito(usuario);
        return tareaRepository.save(tarea);
    }

    public void eliminarDeFavoritos(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        tarea.removeUsuarioFavorito(usuario);
        tareaRepository.save(tarea);
    }
}