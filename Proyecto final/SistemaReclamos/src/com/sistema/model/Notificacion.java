package com.sistema.model;

import java.util.Date;

public class Notificacion {
    private int id;
    private String mensaje;
    private Date fechaEnvio;
    private int reclamoId;

    // Constructor vacío
    public Notificacion() {}

    // Constructor con parámetros
    public Notificacion(int id, String mensaje, int reclamoId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fechaEnvio = new Date();
        this.reclamoId = reclamoId;
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

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public int getReclamoId() {
        return reclamoId;
    }

    public void setReclamoId(int reclamoId) {
        this.reclamoId = reclamoId;
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", reclamoId=" + reclamoId +
                '}';
    }
}