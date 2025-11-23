package com.sistema.model;

import com.sistema.enums.Rol;
import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private Rol rol;
    private Date fechaRegistro;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(int id, String nombre, String email, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.fechaRegistro = new Date();
    }

    // Getters y Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}