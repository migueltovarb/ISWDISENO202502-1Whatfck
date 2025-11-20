package com.vehiculos.api.model;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Modelo de datos para entidades de Vehículos
 * Representa un vehículo en el sistema con todas sus propiedades
 */
@Document(collection = "vehiculos")
@CompoundIndexes({
    @CompoundIndex(name = "idx_marca_modelo", def = "{'marca': 1, 'modelo': 1}"),
    @CompoundIndex(name = "idx_disponible_tipo", def = "{'disponible': 1, 'tipo': 1}")
})
public class Vehiculo {

    @Id
    private String id;

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
    @Indexed(unique = true)
    private String placa;

    @DecimalMin(value = "1000.0", message = "El precio debe ser mayor o igual a 1000.0")
    @DecimalMax(value = "1000000.0", message = "El precio debe ser menor o igual a 1000000.0")
    private Double precio;

    @Size(max = 20, message = "El tipo no puede tener más de 20 caracteres")
    private String tipo; // sedan, SUV, hatchback, pickup, etc.

    private Boolean disponible;

    private LocalDateTime fechaCreacion;

    // Constructor por defecto
    public Vehiculo() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor con parámetros principales
    public Vehiculo(String marca, String modelo, Integer año, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.placa = placa;
        this.disponible = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor completo
    public Vehiculo(String marca, String modelo, Integer año, String placa, Double precio, String tipo, Boolean disponible) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.placa = placa;
        this.precio = precio;
        this.tipo = tipo;
        this.disponible = disponible;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Builder pattern manual
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

        public Vehiculo build() {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(this.marca);
            vehiculo.setModelo(this.modelo);
            vehiculo.setAño(this.año);
            vehiculo.setPlaca(this.placa);
            vehiculo.setPrecio(this.precio);
            vehiculo.setTipo(this.tipo);
            vehiculo.setDisponible(this.disponible);
            return vehiculo;
        }
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
     * Verificar si el vehículo está disponible
     */
    public boolean isDisponible() {
        return Boolean.TRUE.equals(this.disponible);
    }

    /**
     * Obtener la información básica del vehículo
     */
    public String getInfoBasica() {
        return String.format("%s %s (%d) - Placa: %s", 
                           marca, modelo, año, placa);
    }
}