package com.sprintix.dto;

public class AuthResponseDTO {
    
    private boolean success;
    private String message;
    private String token;
    private UsuarioDTO usuario;

    // Constructores
    public AuthResponseDTO() {}

    public AuthResponseDTO(boolean success, String message, String token, UsuarioDTO usuario) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.usuario = usuario;
    }

    // Constructor para errores (sin token ni usuario)
    public AuthResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters y Setters
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}