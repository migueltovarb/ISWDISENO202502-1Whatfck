package com.sistema.model;

import com.sistema.enums.EstadoReclamo;
import com.sistema.enums.TipoReclamo;
import java.util.Date;

public class Reclamo {
    private int id;
    private String titulo;
    private String descripcion;
    private TipoReclamo tipo;
    private String ubicacion;
    private Date fechaCreacion;
    private EstadoReclamo estado;
    private int clienteId;

    // Constructor vacío
    public Reclamo() {}

    // Constructor con parámetros
    public Reclamo(int id, String titulo, String descripcion, TipoReclamo tipo,
                   String ubicacion, int clienteId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.fechaCreacion = new Date();
        this.estado = EstadoReclamo.ABIERTO;
        this.clienteId = clienteId;
    }

    // Getters y Setters
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

    public TipoReclamo getTipo() {
        return tipo;
    }

    public void setTipo(TipoReclamo tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoReclamo estado) {
        this.estado = estado;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo=" + tipo +
                ", ubicacion='" + ubicacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estado=" + estado +
                ", clienteId=" + clienteId +
                '}';
    }
}