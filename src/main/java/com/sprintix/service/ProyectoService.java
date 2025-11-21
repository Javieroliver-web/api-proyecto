package com.sprintix.service;

import com.sprintix.entity.Proyecto;
import com.sprintix.entity.Usuario;
import com.sprintix.repository.ProyectoRepository;
import com.sprintix.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public List<Proyecto> listarPorCreador(int creadorId) {
        return proyectoRepository.findByCreadorId(creadorId);
    }

    // --- NUEVO: BUSCAR POR NOMBRE ---
    public List<Proyecto> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Optional<Proyecto> obtenerPorId(int id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void eliminar(int id) {
        proyectoRepository.deleteById(id);
    }

    // --- NUEVO: AGREGAR PARTICIPANTE ---
    public void agregarParticipante(int proyectoId, int usuarioId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Como Usuario es el "dueño" de la relación (mappedBy está en Proyecto),
        // añadimos el proyecto a la lista del usuario y guardamos el usuario.
        usuario.getProyectosAsignados().add(proyecto);
        usuarioRepository.save(usuario);
    }

    // --- NUEVO: ELIMINAR PARTICIPANTE ---
    public void eliminarParticipante(int proyectoId, int usuarioId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.getProyectosAsignados().remove(proyecto);
        usuarioRepository.save(usuario);
    }
}