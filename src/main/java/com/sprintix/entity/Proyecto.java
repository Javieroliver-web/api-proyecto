package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*; // Importa todo lo de JPA

/**
 * Esta es la clase Entidad que mapea la tabla "Proyecto".
 * Incluye todas las relaciones y los getters/setters.
 */
@Entity
@Table(name = "Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    
    @Column(columnDefinition = "TEXT") // Para que coincida con el tipo 'text' de tu diagrama
    private String descripcion;
    
    @Temporal(TemporalType.DATE) // Solo fecha, sin hora
    private Date fecha_inicio;
    
    @Temporal(TemporalType.DATE) // Solo fecha, sin hora
    private Date fecha_fin;
    
    private String estado;

    // --- Relaciones ---
    
    /**
     * Relación Muchos-a-Uno con Usuario.
     * Muchos proyectos pueden ser creados por un usuario.
     * 'fetch = FetchType.LAZY' es una optimización para no cargar el usuario a menos que se pida.
     * 'JoinColumn' especifica que 'usuario_id' es la clave foránea en esta tabla.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario creador;

    /**
     * Relación Uno-a-Muchos con Tarea.
     * Un proyecto puede tener muchas tareas.
     * 'mappedBy = "proyecto"' indica que la clase 'Tarea' maneja la relación (con su campo 'proyecto').
     * 'cascade = CascadeType.ALL' significa que si borras un proyecto, sus tareas también se borran.
     * 'orphanRemoval = true' ayuda a limpiar tareas que se queden sin proyecto.
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tarea> tareas = new HashSet<>();

    /**
     * Relación Muchos-a-Muchos con Usuario (para participantes).
     * 'mappedBy = "proyectosAsignados"' indica que la clase 'Usuario' maneja esta relación
     * (con su campo 'proyectosAsignados' que define la @JoinTable).
     */
    @ManyToMany(mappedBy = "proyectosAsignados")
    private Set<Usuario> participantes = new HashSet<>();

    // --- Constructor Vacío ---
    public Proyecto() {
        // Requerido por JPA
    }

    // --- Getters y Setters ---
    // ¡ESTO ES LO QUE SOLUCIONA TODOS TUS ERRORES!

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Set<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Set<Usuario> participantes) {
        this.participantes = participantes;
    }
}