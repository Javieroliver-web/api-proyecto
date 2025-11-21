package com.sprintix.controller;

import com.sprintix.entity.Tarea;
import com.sprintix.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping("/proyecto/{proyectoId}")
    public List<Tarea> listarPorProyecto(@PathVariable int proyectoId) {
        return tareaService.listarPorProyecto(proyectoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable int id) {
        return tareaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        // Nota: Asegúrate de que el objeto Tarea venga con el objeto Proyecto (o su ID) seteado
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.guardar(tarea));
    }

    @PostMapping("/{id}/asignar")
    public ResponseEntity<?> asignarUsuario(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        Integer usuarioId = body.get("usuario_id");
        if (usuarioId == null) return ResponseEntity.badRequest().body("usuario_id requerido");

        try {
            Tarea tareaActualizada = tareaService.asignarUsuario(id, usuarioId);
            return ResponseEntity.ok(tareaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- NUEVO: ACTUALIZAR TAREA ---
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable int id, @RequestBody Tarea tareaDetalles) {
        return tareaService.obtenerPorId(id).map(tarea -> {
            tarea.setTitulo(tareaDetalles.getTitulo());
            tarea.setDescripcion(tareaDetalles.getDescripcion());
            tarea.setEstado(tareaDetalles.getEstado());
            tarea.setFecha_limite(tareaDetalles.getFecha_limite());
            return ResponseEntity.ok(tareaService.guardar(tarea));
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- NUEVO: LISTAR ASIGNADAS A USUARIO ---
    @GetMapping("/usuario/{usuarioId}/asignadas")
    public List<Tarea> listarAsignadas(@PathVariable int usuarioId) {
        return tareaService.listarAsignadasPorUsuario(usuarioId);
    }

    // --- NUEVO: LISTAR FAVORITAS DE USUARIO ---
    @GetMapping("/usuario/{usuarioId}/favoritas")
    public List<Tarea> listarFavoritas(@PathVariable int usuarioId) {
        return tareaService.listarFavoritasPorUsuario(usuarioId);
    }

    // --- NUEVO: AGREGAR A FAVORITOS ---
    @PostMapping("/{id}/favorito")
    public ResponseEntity<?> agregarAFavoritos(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        Integer usuarioId = body.get("usuario_id");
        if (usuarioId == null) return ResponseEntity.badRequest().body("usuario_id requerido");
        try {
            Tarea tarea = tareaService.marcarComoFavorita(id, usuarioId);
            return ResponseEntity.ok(tarea);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}