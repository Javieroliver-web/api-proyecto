package com.sprintix.service;

import com.sprintix.entity.Tarea;
import com.sprintix.entity.Usuario;
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

    public List<Tarea> listarPorProyecto(int proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
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

    // Lógica de negocio: Asignar usuario
    public Tarea asignarUsuario(int tareaId, int usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
            .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
            
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Usamos el método helper de tu entidad para sincronizar la relación
        tarea.addUsuarioAsignado(usuario);
        
        return tareaRepository.save(tarea);
    }

    // --- NUEVOS MÉTODOS ---

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
        
        // Usamos el método helper de la entidad Tarea
        tarea.addUsuarioFavorito(usuario);
        
        return tareaRepository.save(tarea);
    }
}