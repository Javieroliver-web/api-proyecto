package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // <--- 1. IMPORTAR ESTO
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <--- 2. IMPORTAR ESTO

/**
 * Esta es la clase Entidad que mapea la tabla "Proyecto".
 */
@Entity
@Table(name = "Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Temporal(TemporalType.DATE)
    private Date fecha_inicio;
    
    @Temporal(TemporalType.DATE)
    private Date fecha_fin;
    
    private String estado;

    // --- Relaciones ---
    
    /**
     * El creador SÍ queremos verlo, pero como es carga perezosa (LAZY),
     * usamos @JsonIgnoreProperties para evitar errores de proxy de Hibernate.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // <--- EVITA ERROR 500 EN LAZY LOAD
    private Usuario creador;

    /**
     * IMPORTANTE: Ponemos @JsonIgnore aquí.
     * Al pedir la lista de proyectos, NO queremos que se descarguen todas las tareas.
     * Para ver tareas, usaremos el endpoint específico: /api/tareas/proyecto/{id}
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // <--- CORRECCIÓN CLAVE
    private Set<Tarea> tareas = new HashSet<>();

    /**
     * IMPORTANTE: Ponemos @JsonIgnore aquí.
     * No queremos listar todos los participantes en la vista general.
     */
    @ManyToMany(mappedBy = "proyectosAsignados")
    @JsonIgnore // <--- CORRECCIÓN CLAVE
    private Set<Usuario> participantes = new HashSet<>();

    // --- Constructor Vacío ---
    public Proyecto() {
    }

    // --- Getters y Setters ---

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