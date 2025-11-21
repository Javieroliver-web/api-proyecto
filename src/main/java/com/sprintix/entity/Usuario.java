package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects; // Importante

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol;
    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fecha_registro;

    // --- Relaciones (Con JsonIgnore para evitar bucles) ---

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private Set<Proyecto> proyectosCreados = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Notificacion> notificaciones = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "Usuario_Proyecto",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    @JsonIgnore
    private Set<Proyecto> proyectosAsignados = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "Tarea_Asignada",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    @JsonIgnore
    private Set<Tarea> tareasAsignadas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "Tarea_Favorita",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    @JsonIgnore
    private Set<Tarea> tareasFavoritas = new HashSet<>();

    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @JsonIgnore // Seguridad: No enviar password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Date getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(Date fecha_registro) { this.fecha_registro = fecha_registro; }

    public Set<Proyecto> getProyectosCreados() { return proyectosCreados; }
    public void setProyectosCreados(Set<Proyecto> proyectosCreados) { this.proyectosCreados = proyectosCreados; }

    public Set<Notificacion> getNotificaciones() { return notificaciones; }
    public void setNotificaciones(Set<Notificacion> notificaciones) { this.notificaciones = notificaciones; }

    public Set<Proyecto> getProyectosAsignados() { return proyectosAsignados; }
    public void setProyectosAsignados(Set<Proyecto> proyectosAsignados) { this.proyectosAsignados = proyectosAsignados; }

    public Set<Tarea> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(Set<Tarea> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }

    public Set<Tarea> getTareasFavoritas() { return tareasFavoritas; }
    public void setTareasFavoritas(Set<Tarea> tareasFavoritas) { this.tareasFavoritas = tareasFavoritas; }

    // --- Identidad (Vital para que los Sets funcionen) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != 0 && id == usuario.id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}