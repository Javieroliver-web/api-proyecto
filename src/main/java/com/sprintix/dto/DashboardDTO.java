package com.sprintix.dto;

import java.util.List;
import com.sprintix.entity.Proyecto;
import com.sprintix.entity.Tarea;

public class DashboardDTO {
    
    private long tareasPendientes;
    private long proyectosActivos;
    private long notificacionesNoLeidas;
    
    // Listas breves para la vista rápida
    private List<Proyecto> ultimosProyectos;
    private List<Tarea> tareasProximas;

    public DashboardDTO(long tareasPendientes, long proyectosActivos, long notificacionesNoLeidas,
                        List<Proyecto> ultimosProyectos, List<Tarea> tareasProximas) {
        this.tareasPendientes = tareasPendientes;
        this.proyectosActivos = proyectosActivos;
        this.notificacionesNoLeidas = notificacionesNoLeidas;
        this.ultimosProyectos = ultimosProyectos;
        this.tareasProximas = tareasProximas;
    }

    // Getters y Setters
    public long getTareasPendientes() { return tareasPendientes; }
    public void setTareasPendientes(long tareasPendientes) { this.tareasPendientes = tareasPendientes; }

    public long getProyectosActivos() { return proyectosActivos; }
    public void setProyectosActivos(long proyectosActivos) { this.proyectosActivos = proyectosActivos; }

    public long getNotificacionesNoLeidas() { return notificacionesNoLeidas; }
    public void setNotificacionesNoLeidas(long notificacionesNoLeidas) { this.notificacionesNoLeidas = notificacionesNoLeidas; }

    public List<Proyecto> getUltimosProyectos() { return ultimosProyectos; }
    public void setUltimosProyectos(List<Proyecto> ultimosProyectos) { this.ultimosProyectos = ultimosProyectos; }

    public List<Tarea> getTareasProximas() { return tareasProximas; }
    public void setTareasProximas(List<Tarea> tareasProximas) { this.tareasProximas = tareasProximas; }
}