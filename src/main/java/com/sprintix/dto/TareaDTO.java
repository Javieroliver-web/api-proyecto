package com.sprintix.dto;

import java.util.Date;

/**
 * DTO para LEER una Tarea (enviado desde la API al cliente)
 * Basado en la entidad Tarea.java
 */
public class TareaDTO {

    private int id;
    private String titulo;
    private String descripcion;
    private String estado;
    private Date fecha_limite;
    
    // ID del proyecto al que pertenece, para no enviar el objeto entero
    private int proyecto_id; 

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(Date fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public int getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(int proyecto_id) {
        this.proyecto_id = proyecto_id;
    }
}