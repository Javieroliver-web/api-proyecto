package com.sprintix.dto;

// Esta clase representa un Usuario como se DEVUELVE al frontend
// NUNCA incluyas la contraseña aquí
public class UsuarioDTO {
    
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private String avatar;
    private java.util.Date fecha_registro;

    // --- Getters y Setters ---
    // (En VS Code: Clic derecho > Source Action > Generate Getters and Setters)

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public java.util.Date getFecha_registro() {
        return fecha_registro;
    }
    public void setFecha_registro(java.util.Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}