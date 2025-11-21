package com.sprintix.controller;

import com.sprintix.dto.NotificacionCreateDTO;
import com.sprintix.entity.Notificacion;
import com.sprintix.service.NotificacionService;
import com.sprintix.service.UsuarioService; // <--- 1. Importar esto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioService usuarioService; // <--- 2. Inyectar UsuarioService

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> listarPorUsuario(@PathVariable int usuarioId) {
        return notificacionService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public List<Notificacion> listarNoLeidas(@PathVariable int usuarioId) {
        return notificacionService.listarNoLeidas(usuarioId);
    }

    // --- CORRECCIÓN PRINCIPAL ---
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody NotificacionCreateDTO createDTO) {
        // Buscamos al usuario por el ID que viene en el JSON (createDTO.getUsuario_id())
        return usuarioService.obtenerPorId(createDTO.getUsuario_id())
            .map(usuario -> {
                // Creamos la entidad manualmente con los datos del DTO
                Notificacion notificacion = new Notificacion();
                notificacion.setMensaje(createDTO.getMensaje());
                notificacion.setTipo(createDTO.getTipo());
                notificacion.setUsuario(usuario); // Asignamos la relación correctamente
                
                // Guardamos
                return ResponseEntity.ok(notificacionService.guardar(notificacion));
            })
            .orElse(ResponseEntity.badRequest().body("Usuario no encontrado"));
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable int id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/usuario/{usuarioId}/leer-todas")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable int usuarioId) {
        notificacionService.marcarTodasComoLeidas(usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}