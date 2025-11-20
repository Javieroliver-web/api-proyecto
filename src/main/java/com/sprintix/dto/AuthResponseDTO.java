package com.sprintix.dto;

import com.sprintix.entity.Usuario;

public class AuthResponseDTO {
    
    private boolean success;
    private String message;
    private String token;
    private Usuario usuario;

    // --- Constructor 1: Respuesta simple (Error o éxito sin datos) ---
    public AuthResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // --- Constructor 2: Respuesta completa (Login/Registro exitoso) ---
    public AuthResponseDTO(boolean success, String message, String token, Usuario usuario) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.usuario = usuario;
    }

    // --- Getters y Setters ---
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}