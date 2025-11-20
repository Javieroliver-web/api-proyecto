package com.sprintix.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*; // Importante: usa jakarta.persistence

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

    // --- Relaciones ---
    // (Estas son las relaciones de tu diagrama. Las añadiremos ahora
    // para que no sean el siguiente error)

    // Un usuario es "creador" de muchos proyectos
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Proyecto> proyectosCreados = new HashSet<>();

    // Un usuario recibe muchas notificaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notificacion> notificaciones = new HashSet<>();

    // Un usuario participa en muchos proyectos (tabla intermedia Usuario_Proyecto)
    @ManyToMany
    @JoinTable(
        name = "Usuario_Proyecto",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectosAsignados = new HashSet<>();

    // Un usuario tiene muchas tareas asignadas
    @ManyToMany
    @JoinTable(
        name = "Tarea_Asignada",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    private Set<Tarea> tareasAsignadas = new HashSet<>();

    // Un usuario tiene muchas tareas favoritas
    @ManyToMany
    @JoinTable(
        name = "Tarea_Favorita",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarea_id")
    )
    private Set<Tarea> tareasFavoritas = new HashSet<>();


    // --- Getters y Setters ---
    // (En VS Code: Clic derecho > Source Action > Generate Getters and Setters)

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Set<Proyecto> getProyectosCreados() {
        return proyectosCreados;
    }

    public void setProyectosCreados(Set<Proyecto> proyectosCreados) {
        this.proyectosCreados = proyectosCreados;
    }

    public Set<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Set<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Set<Proyecto> getProyectosAsignados() {
        return proyectosAsignados;
    }

    public void setProyectosAsignados(Set<Proyecto> proyectosAsignados) {
        this.proyectosAsignados = proyectosAsignados;
    }

    public Set<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(Set<Tarea> tareasAsignadas) {
        this.tareasAsignadas = tareasAsignadas;
    }

    public Set<Tarea> getTareasFavoritas() {
        return tareasFavoritas;
    }

    public void setTareasFavoritas(Set<Tarea> tareasFavoritas) {
        this.tareasFavoritas = tareasFavoritas;
    }
}