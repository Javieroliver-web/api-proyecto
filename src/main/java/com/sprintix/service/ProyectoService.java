package com.sprintix.service;

import com.sprintix.entity.Proyecto;
import com.sprintix.repository.ProyectoRepository;
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

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public List<Proyecto> listarPorCreador(int creadorId) {
        return proyectoRepository.findByCreadorId(creadorId);
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
}