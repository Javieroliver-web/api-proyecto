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
import java.util.Optional;

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

    // --- NUEVO: ACTUALIZAR PROYECTO ---
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable int id, @RequestBody Proyecto proyectoDetalles) {
        return proyectoService.obtenerPorId(id).map(proyecto -> {
            proyecto.setNombre(proyectoDetalles.getNombre());
            proyecto.setDescripcion(proyectoDetalles.getDescripcion());
            proyecto.setFecha_inicio(proyectoDetalles.getFecha_inicio());
            proyecto.setFecha_fin(proyectoDetalles.getFecha_fin());
            proyecto.setEstado(proyectoDetalles.getEstado());
            return ResponseEntity.ok(proyectoService.guardar(proyecto));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}