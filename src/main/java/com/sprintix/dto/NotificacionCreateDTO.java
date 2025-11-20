package com.sprintix.dto;

/**
 * DTO para CREAR una Notificacion
 * Basado en la entidad Notificacion.java
 */
public class NotificacionCreateDTO {

    private String mensaje;
    private String tipo;
    
    // 'leida' es 'false' por defecto en la entidad
    // 'fecha' se genera automáticamente por la base de datos
    
    // ID del usuario al que se debe asignar
    private int usuario_id;

    // --- Getters y Setters ---

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}