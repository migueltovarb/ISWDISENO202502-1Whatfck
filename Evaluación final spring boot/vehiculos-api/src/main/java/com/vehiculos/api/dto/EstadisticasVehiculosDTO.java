package com.vehiculos.api.dto;

/**
 * DTO para estadísticas de vehículos
 * Contiene métricas y datos estadísticos sobre los vehículos del sistema
 */
public class EstadisticasVehiculosDTO {

    private long totalVehiculos;
    private long vehiculosDisponibles;
    private long vehiculosOcupados;
    private double porcentajeDisponibilidad;
    private String mensajeEstadisticas;

    // Constructores
    public EstadisticasVehiculosDTO() {
    }

    public EstadisticasVehiculosDTO(long totalVehiculos, long vehiculosDisponibles, long vehiculosOcupados, double porcentajeDisponibilidad, String mensajeEstadisticas) {
        this.totalVehiculos = totalVehiculos;
        this.vehiculosDisponibles = vehiculosDisponibles;
        this.vehiculosOcupados = vehiculosOcupados;
        this.porcentajeDisponibilidad = porcentajeDisponibilidad;
        this.mensajeEstadisticas = mensajeEstadisticas;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long totalVehiculos;
        private long vehiculosDisponibles;
        private long vehiculosOcupados;
        private double porcentajeDisponibilidad;
        private String mensajeEstadisticas;

        public Builder totalVehiculos(long totalVehiculos) {
            this.totalVehiculos = totalVehiculos;
            return this;
        }

        public Builder vehiculosDisponibles(long vehiculosDisponibles) {
            this.vehiculosDisponibles = vehiculosDisponibles;
            return this;
        }

        public Builder vehiculosOcupados(long vehiculosOcupados) {
            this.vehiculosOcupados = vehiculosOcupados;
            return this;
        }

        public Builder porcentajeDisponibilidad(double porcentajeDisponibilidad) {
            this.porcentajeDisponibilidad = porcentajeDisponibilidad;
            return this;
        }

        public Builder mensajeEstadisticas(String mensajeEstadisticas) {
            this.mensajeEstadisticas = mensajeEstadisticas;
            return this;
        }

        public EstadisticasVehiculosDTO build() {
            return new EstadisticasVehiculosDTO(totalVehiculos, vehiculosDisponibles, vehiculosOcupados, porcentajeDisponibilidad, mensajeEstadisticas);
        }
    }

    // Getters y Setters
    public long getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(long totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public long getVehiculosDisponibles() {
        return vehiculosDisponibles;
    }

    public void setVehiculosDisponibles(long vehiculosDisponibles) {
        this.vehiculosDisponibles = vehiculosDisponibles;
    }

    public long getVehiculosOcupados() {
        return vehiculosOcupados;
    }

    public void setVehiculosOcupados(long vehiculosOcupados) {
        this.vehiculosOcupados = vehiculosOcupados;
    }

    public double getPorcentajeDisponibilidad() {
        return porcentajeDisponibilidad;
    }

    public void setPorcentajeDisponibilidad(double porcentajeDisponibilidad) {
        this.porcentajeDisponibilidad = porcentajeDisponibilidad;
    }

    public String getMensajeEstadisticas() {
        return mensajeEstadisticas;
    }

    public void setMensajeEstadisticas(String mensajeEstadisticas) {
        this.mensajeEstadisticas = mensajeEstadisticas;
    }

    /**
     * Método adicional para obtener un resumen textual de las estadísticas
     */
    public String getResumenEstadisticas() {
        return String.format("Total: %d vehículos | Disponibles: %d | Ocupados: %d | %.1f%% disponible",
                           totalVehiculos, vehiculosDisponibles, vehiculosOcupados, porcentajeDisponibilidad);
    }
}