package com.sprintix.service;

import com.sprintix.entity.Archivo;
import com.sprintix.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ArchivoService {

    @Autowired
    private ArchivoRepository archivoRepository;

    public List<Archivo> listarPorProyecto(int proyectoId) {
        return archivoRepository.findByProyectoId(proyectoId);
    }

    public Archivo guardar(Archivo archivo) {
        return archivoRepository.save(archivo);
    }

    public void eliminar(int id) {
        archivoRepository.deleteById(id);
    }

    // --- NUEVO MÉTODO ---
    public List<Archivo> listarPorUsuario(int usuarioId) {
        return archivoRepository.findByUsuarioId(usuarioId);
    }
}