package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import jakarta.persistence.*; 
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    private String estado;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_limite;

    // --- Relaciones ---
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonIgnore // Cortamos el bucle aquí
    private Proyecto proyecto;

    @ManyToMany(mappedBy = "tareasAsignadas")
    private Set<Usuario> usuariosAsignados = new HashSet<>();

    @ManyToMany(mappedBy = "tareasFavoritas")
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    public Tarea() {}

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha_limite() { return fecha_limite; }
    public void setFecha_limite(Date fecha_limite) { this.fecha_limite = fecha_limite; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Set<Usuario> getUsuariosAsignados() { return usuariosAsignados; }
    public void setUsuariosAsignados(Set<Usuario> usuariosAsignados) { this.usuariosAsignados = usuariosAsignados; }

    public Set<Usuario> getUsuariosFavoritos() { return usuariosFavoritos; }
    public void setUsuariosFavoritos(Set<Usuario> usuariosFavoritos) { this.usuariosFavoritos = usuariosFavoritos; }

    // --- Helpers ---
    public void addUsuarioAsignado(Usuario usuario) {
        this.usuariosAsignados.add(usuario);
        usuario.getTareasAsignadas().add(this);
    }
    public void removeUsuarioAsignado(Usuario usuario) {
        this.usuariosAsignados.remove(usuario);
        usuario.getTareasAsignadas().remove(this);
    }
    public void addUsuarioFavorito(Usuario usuario) {
        this.usuariosFavoritos.add(usuario);
        usuario.getTareasFavoritas().add(this);
    }
    public void removeUsuarioFavorito(Usuario usuario) {
        this.usuariosFavoritos.remove(usuario);
        usuario.getTareasFavoritas().remove(this);
    }

    // --- Identidad ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return id != 0 && id == tarea.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}