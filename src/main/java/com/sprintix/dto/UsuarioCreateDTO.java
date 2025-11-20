package com.sprintix.dto;

// Esta clase representa los datos para CREAR un usuario
// Puede tener la contraseña, pero no el id (que se genera solo)
public class UsuarioCreateDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String password; // La contraseña SÍ va aquí
    private String rol;
    private String avatar;

    // --- Getters y Setters ---

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
}