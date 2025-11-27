package com.sprintix.controller;

import com.sprintix.entity.Proyecto;
import com.sprintix.entity.Usuario;
import com.sprintix.service.ProyectoService;
import com.sprintix.service.UsuarioService;
import com.sprintix.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping
    public List<Proyecto> listarTodos() {
        return proyectoService.listarTodos();
    }

    @GetMapping("/buscar")
    public List<Proyecto> buscar(@RequestParam String nombre) {
        return proyectoService.buscarPorNombre(nombre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerPorId(@PathVariable int id) {
        return proyectoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto, 
                                           @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
        }

        String token = authHeader.substring(7);
        String emailUsuario = jwtUtil.extractUsername(token);
        
        Optional<Usuario> creador = usuarioService.obtenerPorEmail(emailUsuario);
        
        if (creador.isPresent()) {
            proyecto.setCreador(creador.get());
            Proyecto nuevoProyecto = proyectoService.guardar(proyecto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario creador no encontrado");
        }
    }

    // --- CORRECCIÓN AQUÍ ---
    // Validación de nulos para permitir actualizaciones parciales
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable int id, @RequestBody Proyecto proyectoDetalles) {
        return proyectoService.obtenerPorId(id).map(proyecto -> {
            
            if (proyectoDetalles.getNombre() != null) {
                proyecto.setNombre(proyectoDetalles.getNombre());
            }
            if (proyectoDetalles.getDescripcion() != null) {
                proyecto.setDescripcion(proyectoDetalles.getDescripcion());
            }
            if (proyectoDetalles.getFecha_inicio() != null) {
                proyecto.setFecha_inicio(proyectoDetalles.getFecha_inicio());
            }
            if (proyectoDetalles.getFecha_fin() != null) {
                proyecto.setFecha_fin(proyectoDetalles.getFecha_fin());
            }
            if (proyectoDetalles.getEstado() != null) {
                proyecto.setEstado(proyectoDetalles.getEstado());
            }
            
            return ResponseEntity.ok(proyectoService.guardar(proyecto));
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- PARTICIPANTES ---

    @GetMapping("/{id}/participantes")
    public ResponseEntity<Set<Usuario>> listarParticipantes(@PathVariable int id) {
        return proyectoService.obtenerPorId(id)
            .map(p -> ResponseEntity.ok(p.getParticipantes()))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/participantes")
    public ResponseEntity<?> agregarParticipante(@PathVariable int id, @RequestBody Map<String, Integer> body) {
        Integer usuarioId = body.get("usuario_id");
        if (usuarioId == null) return ResponseEntity.badRequest().body("usuario_id requerido");
        try {
            proyectoService.agregarParticipante(id, usuarioId);
            return ResponseEntity.ok("Participante añadido");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/participantes/{usuarioId}")
    public ResponseEntity<?> eliminarParticipante(@PathVariable int id, @PathVariable int usuarioId) {
        try {
            proyectoService.eliminarParticipante(id, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}