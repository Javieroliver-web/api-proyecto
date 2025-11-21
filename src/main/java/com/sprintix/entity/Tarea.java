package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*; 
import com.fasterxml.jackson.annotation.JsonIgnore; // <--- IMPORTANTE: Añadir este import

/**
 * Esta es la clase Entidad que mapea la tabla "Tarea".
 * Incluye todas las relaciones y los getters/setters.
 */
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
    
    /**
     * Relación Muchos-a-Uno con Proyecto.
     * Muchas tareas pertenecen a un proyecto.
     * CORTAMOS EL BUCLE AQUÍ: La tarea conoce a su proyecto en Base de Datos,
     * pero al convertir a JSON, no volvemos a pintar el proyecto entero.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonIgnore // <--- ESTO SOLUCIONA EL ERROR 500 EN /proyectos
    private Proyecto proyecto;

    /**
     * Relación Muchos-a-Muchos con Usuario (para asignados).
     */
    @ManyToMany(mappedBy = "tareasAsignadas")
    // No necesitamos @JsonIgnore aquí porque ya lo pusimos en el lado "Usuario" (tareasAsignadas)
    // Así que se mostrarán los usuarios, pero esos usuarios no volverán a mostrar sus tareas. Perfecto.
    private Set<Usuario> usuariosAsignados = new HashSet<>();

    /**
     * Relación Muchos-a-Muchos con Usuario (para favoritos).
     */
    @ManyToMany(mappedBy = "tareasFavoritas")
    private Set<Usuario> usuariosFavoritos = new HashSet<>();

    // --- Constructor Vacío ---
    public Tarea() {
        // Requerido por JPA
    }

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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Set<Usuario> getUsuariosAsignados() {
        return usuariosAsignados;
    }

    public void setUsuariosAsignados(Set<Usuario> usuariosAsignados) {
        this.usuariosAsignados = usuariosAsignados;
    }

    public Set<Usuario> getUsuariosFavoritos() {
        return usuariosFavoritos;
    }

    public void setUsuariosFavoritos(Set<Usuario> usuariosFavoritos) {
        this.usuariosFavoritos = usuariosFavoritos;
    }

    // --- MÉTODOS HELPER ---

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