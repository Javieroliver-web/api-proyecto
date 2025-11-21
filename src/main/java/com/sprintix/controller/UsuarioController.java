package com.sprintix.controller;

import com.sprintix.dto.DashboardDTO;
import com.sprintix.entity.Usuario;
import com.sprintix.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable int id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- NUEVO: ENDPOINT DASHBOARD ---
    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardDTO> obtenerDashboard(@PathVariable int id) {
        try {
            DashboardDTO dashboard = usuarioService.obtenerDashboard(id);
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable int id, @RequestBody Usuario usuarioDetalles) {
        return usuarioService.obtenerPorId(id).map(usuario -> {
            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setApellido(usuarioDetalles.getApellido());
            usuario.setEmail(usuarioDetalles.getEmail());
            if(usuarioDetalles.getRol() != null) {
                usuario.setRol(usuarioDetalles.getRol());
            }
            return ResponseEntity.ok(usuarioService.guardar(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}