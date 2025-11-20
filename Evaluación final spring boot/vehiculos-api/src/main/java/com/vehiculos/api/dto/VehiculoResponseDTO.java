package com.vehiculos.api.dto;

import com.vehiculos.api.model.Vehiculo;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de vehículos
 * Contiene toda la información del vehículo para mostrar al cliente
 */
public class VehiculoResponseDTO {

    private String id;
    private String marca;
    private String modelo;
    private Integer año;
    private String placa;
    private Double precio;
    private String tipo;
    private Boolean disponible;
    private LocalDateTime fechaCreacion;

    // Constructores
    public VehiculoResponseDTO() {
    }

    public VehiculoResponseDTO(String id, String marca, String modelo, Integer año, String placa, Double precio, String tipo, Boolean disponible, LocalDateTime fechaCreacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.placa = placa;
        this.precio = precio;
        this.tipo = tipo;
        this.disponible = disponible;
        this.fechaCreacion = fechaCreacion;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String marca;
        private String modelo;
        private Integer año;
        private String placa;
        private Double precio;
        private String tipo;
        private Boolean disponible;
        private LocalDateTime fechaCreacion;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public Builder año(Integer año) {
            this.año = año;
            return this;
        }

        public Builder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public Builder precio(Double precio) {
            this.precio = precio;
            return this;
        }

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder disponible(Boolean disponible) {
            this.disponible = disponible;
            return this;
        }

        public Builder fechaCreacion(LocalDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public VehiculoResponseDTO build() {
            return new VehiculoResponseDTO(id, marca, modelo, año, placa, precio, tipo, disponible, fechaCreacion);
        }
    }

    /**
     * Crear un VehiculoResponseDTO a partir de un modelo Vehiculo
     */
    public static VehiculoResponseDTO fromVehiculo(Vehiculo vehiculo) {
        return builder()
                .id(vehiculo.getId())
                .marca(vehiculo.getMarca())
                .modelo(vehiculo.getModelo())
                .año(vehiculo.getAño())
                .placa(vehiculo.getPlaca())
                .precio(vehiculo.getPrecio())
                .tipo(vehiculo.getTipo())
                .disponible(vehiculo.getDisponible())
                .fechaCreacion(vehiculo.getFechaCreacion())
                .build();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Información básica del vehículo como string
     */
    public String getInfoBasica() {
        return String.format("%s %s (%d) - Placa: %s", 
                           marca, modelo, año, placa);
    }

    /**
     * Verificar si el vehículo está disponible
     */
    public boolean isDisponible() {
        return Boolean.TRUE.equals(this.disponible);
    }
}