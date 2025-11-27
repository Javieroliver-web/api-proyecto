package com.sprintix.controller;

import com.sprintix.dto.TareaCreateDTO;
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
    public List<Tarea> listarPorProyecto(@PathVariable int proyectoId, 
                                         @RequestParam(required = false) String estado) {
        return tareaService.listarPorProyecto(proyectoId, estado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable int id) {
        return tareaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody TareaCreateDTO tareaDTO) {
        try {
            Tarea nuevaTarea = tareaService.crearDesdeDTO(tareaDTO);
            tareaService.flush();
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al crear tarea: " + e.getMessage());
        }
    }

    // --- CORRECCIÓN AQUÍ ---
    // Antes: Sobrescribía todo con lo que llegaba (incluyendo nulos).
    // Ahora: Solo actualiza los campos que vienen en el JSON.
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable int id, @RequestBody Tarea tareaDetalles) {
        return tareaService.obtenerPorId(id).map(tarea -> {
            
            // Validamos cada campo antes de sobrescribirlo
            if (tareaDetalles.getTitulo() != null) {
                tarea.setTitulo(tareaDetalles.getTitulo());
            }
            if (tareaDetalles.getDescripcion() != null) {
                tarea.setDescripcion(tareaDetalles.getDescripcion());
            }
            if (tareaDetalles.getEstado() != null) {
                tarea.setEstado(tareaDetalles.getEstado());
            }
            if (tareaDetalles.getFecha_limite() != null) {
                tarea.setFecha_limite(tareaDetalles.getFecha_limite());
            }
            
            return ResponseEntity.ok(tareaService.guardar(tarea));
        }).orElse(ResponseEntity.notFound().build());
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

    @DeleteMapping("/{id}/asignar/{usuarioId}")
    public ResponseEntity<?> desasignarUsuario(@PathVariable int id, @PathVariable int usuarioId) {
        try {
            tareaService.desasignarUsuario(id, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}/asignadas")
    public List<Tarea> listarAsignadas(@PathVariable int usuarioId) {
        return tareaService.listarAsignadasPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/favoritas")
    public List<Tarea> listarFavoritas(@PathVariable int usuarioId) {
        return tareaService.listarFavoritasPorUsuario(usuarioId);
    }

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

    @DeleteMapping("/{id}/favorito/{usuarioId}")
    public ResponseEntity<?> eliminarDeFavoritos(@PathVariable int id, @PathVariable int usuarioId) {
        try {
            tareaService.eliminarDeFavoritos(id, usuarioId);
            return ResponseEntity.noContent().build();
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