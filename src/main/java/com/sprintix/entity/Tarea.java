package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*; // Importa todo lo de JPA

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
    
    @Column(columnDefinition = "TEXT") // Para que coincida con el tipo 'text' de tu diagrama
    private String descripcion;
    
    private String estado;
    
    @Temporal(TemporalType.TIMESTAMP) // Fecha y hora
    private Date fecha_limite;

    // --- Relaciones ---
    
    /**
     * Relación Muchos-a-Uno con Proyecto.
     * Muchas tareas pertenecen a un proyecto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    /**
     * Relación Muchos-a-Muchos con Usuario (para asignados).
     * 'mappedBy = "tareasAsignadas"' indica que la clase 'Usuario' maneja esta relación.
     * Este es el lado "espejo" (inverse side).
     */
    @ManyToMany(mappedBy = "tareasAsignadas")
    private Set<Usuario> usuariosAsignados = new HashSet<>();

    /**
     * Relación Muchos-a-Muchos con Usuario (para favoritos).
     * 'mappedBy = "tareasFavoritas"' indica que la clase 'Usuario' maneja esta relación.
     * Este es el lado "espejo" (inverse side).
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

    // -----------------------------------------------------------------
    // --- MÉTODOS HELPER PARA SINCRONIZAR RELACIONES BIDIRECCIONALES ---
    // --- ¡ESTA ES LA SOLUCIÓN! ---
    // -----------------------------------------------------------------

    /**
     * Añade un usuario a la lista de asignados,
     * sincronizando AMBOS lados de la relación.
     */
    public void addUsuarioAsignado(Usuario usuario) {
        this.usuariosAsignados.add(usuario);
        usuario.getTareasAsignadas().add(this); // Sincroniza el lado dueño
    }

    /**
     * Remueve un usuario de la lista de asignados,
     * sincronizando AMBOS lados de la relación.
     */
    public void removeUsuarioAsignado(Usuario usuario) {
        this.usuariosAsignados.remove(usuario);
        usuario.getTareasAsignadas().remove(this); // Sincroniza el lado dueño
    }

    /**
     * Añade un usuario a la lista de favoritos,
     * sincronizando AMBOS lados de la relación.
     */
    public void addUsuarioFavorito(Usuario usuario) {
        this.usuariosFavoritos.add(usuario);
        usuario.getTareasFavoritas().add(this); // Sincroniza el lado dueño
    }

    /**
     * Remueve un usuario de la lista de favoritos,
     * sincronizando AMBOS lados de la relación.
     */
    public void removeUsuarioFavorito(Usuario usuario) {
        this.usuariosFavoritos.remove(usuario);
        usuario.getTareasFavoritas().remove(this); // Sincroniza el lado dueño
    }
}