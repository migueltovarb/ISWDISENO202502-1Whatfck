package com.sistema.model;

import java.util.Date;

public class Respuesta {
    private int id;
    private String mensaje;
    private Date fecha;
    private int reclamoId;
    private int usuarioId;

    // Constructor vacío
    public Respuesta() {}

    // Constructor con parámetros
    public Respuesta(int id, String mensaje, int reclamoId, int usuarioId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = new Date();
        this.reclamoId = reclamoId;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getReclamoId() {
        return reclamoId;
    }

    public void setReclamoId(int reclamoId) {
        this.reclamoId = reclamoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                ", reclamoId=" + reclamoId +
                ", usuarioId=" + usuarioId +
                '}';
    }
}