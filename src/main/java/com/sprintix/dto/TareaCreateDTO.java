package com.sprintix.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TareaCreateDTO {

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("estado")
    private String estado;

    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    @JsonProperty("fecha_limite")
    private Date fecha_limite;

    @JsonProperty("proyecto_id")
    private int proyecto_id;

    // --- Getters y Setters ---
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha_limite() { return fecha_limite; }
    public void setFecha_limite(Date fecha_limite) { this.fecha_limite = fecha_limite; }

    public int getProyecto_id() { return proyecto_id; }
    public void setProyecto_id(int proyecto_id) { this.proyecto_id = proyecto_id; }
}