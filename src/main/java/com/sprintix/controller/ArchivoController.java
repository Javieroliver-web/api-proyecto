package com.sprintix.controller;

import com.sprintix.entity.Archivo;
import com.sprintix.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @GetMapping("/proyecto/{proyectoId}")
    public List<Archivo> listarPorProyecto(@PathVariable int proyectoId) {
        return archivoService.listarPorProyecto(proyectoId);
    }

    // --- NUEVO: LISTAR POR USUARIO ---
    @GetMapping("/usuario/{usuarioId}")
    public List<Archivo> listarPorUsuario(@PathVariable int usuarioId) {
        return archivoService.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public Archivo subirArchivo(@RequestBody Archivo archivo) {
        return archivoService.guardar(archivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        archivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}