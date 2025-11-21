package com.sprintix.controller;

import com.sprintix.dto.NotificacionCreateDTO;
import com.sprintix.entity.Notificacion;
import com.sprintix.entity.Usuario;
import com.sprintix.service.NotificacionService;
import com.sprintix.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> listarPorUsuario(@PathVariable int usuarioId) {
        return notificacionService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public List<Notificacion> listarNoLeidas(@PathVariable int usuarioId) {
        return notificacionService.listarNoLeidas(usuarioId);
    }

    // --- MÉTODO CORREGIDO ---
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody NotificacionCreateDTO createDTO) {
        // 1. Buscamos al usuario
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(createDTO.getUsuario_id());
        
        // 2. Verificamos si existe
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // 3. Creamos la entidad
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje(createDTO.getMensaje());
            notificacion.setTipo(createDTO.getTipo());
            notificacion.setUsuario(usuario);
            
            // 4. Guardamos y devolvemos OK (Devuelve Notificacion)
            return ResponseEntity.ok(notificacionService.guardar(notificacion));
        } else {
            // 5. Si no existe, devolvemos Error (Devuelve String)
            // Al usar if/else, Java permite devolver '?' (Object) sin problemas
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
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