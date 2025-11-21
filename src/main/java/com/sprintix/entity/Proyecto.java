package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita error 500 en Lazy Load
    private Usuario creador;

    // Ignoramos tareas al listar proyectos para evitar sobrecarga y bucles
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private Set<Tarea> tareas = new HashSet<>();

    // Ignoramos participantes al listar proyectos (usar endpoint específico para verlos)
    @ManyToMany(mappedBy = "proyectosAsignados")
    @JsonIgnore
    private Set<Usuario> participantes = new HashSet<>();

    public Proyecto() {}

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(Date fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public Date getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(Date fecha_fin) { this.fecha_fin = fecha_fin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }

    public Set<Tarea> getTareas() { return tareas; }
    public void setTareas(Set<Tarea> tareas) { this.tareas = tareas; }

    public Set<Usuario> getParticipantes() { return participantes; }
    public void setParticipantes(Set<Usuario> participantes) { this.participantes = participantes; }

    // --- Identidad ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proyecto proyecto = (Proyecto) o;
        return id != 0 && id == proyecto.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}