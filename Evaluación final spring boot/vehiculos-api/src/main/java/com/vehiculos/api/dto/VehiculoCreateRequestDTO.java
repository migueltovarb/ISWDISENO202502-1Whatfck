package com.vehiculos.api.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para crear vehículos
 * Contiene todos los campos requeridos para crear un nuevo vehículo
 */
public class VehiculoCreateRequestDTO {

    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 1, max = 100, message = "El modelo debe tener entre 1 y 100 caracteres")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1886, message = "El año debe ser mayor o igual a 1886")
    @Max(value = 2100, message = "El año debe ser menor o igual a 2100")
    private Integer año;

    @NotBlank(message = "La placa es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "La placa debe tener formato ABC123")
    private String placa;

    @DecimalMin(value = "1000.0", message = "El precio debe ser mayor o igual a 1000.0")
    @DecimalMax(value = "1000000.0", message = "El precio debe ser menor o igual a 1000000.0")
    private Double precio;

    @Size(max = 20, message = "El tipo no puede tener más de 20 caracteres")
    private String tipo; // sedan, SUV, hatchback, pickup, etc.

    private Boolean disponible;

    // Constructor por defecto
    public VehiculoCreateRequestDTO() {
    }

    // Constructor con todos los parámetros
    public VehiculoCreateRequestDTO(String marca, String modelo, Integer año, String placa, Double precio, String tipo, Boolean disponible) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.placa = placa;
        this.precio = precio;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String marca;
        private String modelo;
        private Integer año;
        private String placa;
        private Double precio;
        private String tipo;
        private Boolean disponible;

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

        public VehiculoCreateRequestDTO build() {
            return new VehiculoCreateRequestDTO(marca, modelo, año, placa, precio, tipo, disponible);
        }
    }

    // Getters y Setters
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
}