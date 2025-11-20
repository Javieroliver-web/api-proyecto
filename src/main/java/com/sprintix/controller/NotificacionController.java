package com.sprintix.controller;

import com.sprintix.entity.Notificacion;
import com.sprintix.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> listarPorUsuario(@PathVariable int usuarioId) {
        return notificacionService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public List<Notificacion> listarNoLeidas(@PathVariable int usuarioId) {
        return notificacionService.listarNoLeidas(usuarioId);
    }

    @PostMapping
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return notificacionService.guardar(notificacion);
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable int id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok().build();
    }
}