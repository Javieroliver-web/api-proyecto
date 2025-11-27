package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*; 
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat; 

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
    // --- CAMBIO A TU FORMATO SOLICITADO ---
    // La API ahora devolverá las fechas como "28-11-2025"
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date fecha_limite;

    // --- Relaciones ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonIgnore
    private Proyecto proyecto;

    @ManyToMany(mappedBy = "tareasAsignadas")
    @JsonIgnore
    private Set<Usuario> usuariosAsignados = new HashSet<>();

    @ManyToMany(mappedBy = "tareasFavoritas")
    @JsonIgnore
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

    @JsonProperty("proyecto_id")
    public Integer getProyectoId() {
        return proyecto != null ? proyecto.getId() : null;
    }

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
}