package com.vehiculos.api.service;

import com.vehiculos.api.dto.*;
import com.vehiculos.api.model.Vehiculo;
import com.vehiculos.api.repository.VehiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * Servicio de lógica de negocio para la gestión de vehículos
 * Maneja todas las operaciones CRUD y validaciones de negocio
 */
@Service
@Transactional
public class VehiculoService {

    private static final Logger logger = Logger.getLogger(VehiculoService.class.getName());
    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    /**
     * Crear un nuevo vehículo
     */
    public VehiculoResponseDTO crearVehiculo(VehiculoCreateRequestDTO requestDTO) {
        logger.info("Creando nuevo vehículo con placa: " + requestDTO.getPlaca());

        // Validar que la placa no exista
        if (vehiculoRepository.existsByPlaca(requestDTO.getPlaca())) {
            throw new IllegalArgumentException("Ya existe un vehículo con la placa: " + requestDTO.getPlaca());
        }

        // Convertir DTO a modelo
        Vehiculo vehiculo = Vehiculo.builder()
                .marca(requestDTO.getMarca())
                .modelo(requestDTO.getModelo())
                .año(requestDTO.getAño())
                .placa(requestDTO.getPlaca())
                .precio(requestDTO.getPrecio())
                .tipo(requestDTO.getTipo())
                .disponible(requestDTO.getDisponible() != null ? requestDTO.getDisponible() : true)
                .build();

        // Guardar en la base de datos
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        logger.info("Vehículo creado exitosamente con ID: " + vehiculoGuardado.getId());

        return VehiculoResponseDTO.fromVehiculo(vehiculoGuardado);
    }

    /**
     * Obtener todos los vehículos con paginación
     */
    @Transactional(readOnly = true)
    public PaginatedResponseDTO<VehiculoResponseDTO> obtenerTodosLosVehiculos(int page, int size) {
        logger.info("Obteniendo vehículos - página: " + page + ", tamaño: " + size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<Vehiculo> vehiculosPage = vehiculoRepository.findAll(pageable);
        
        List<VehiculoResponseDTO> vehiculosDTO = vehiculosPage.getContent().stream()
                .map(VehiculoResponseDTO::fromVehiculo)
                .collect(Collectors.toList());
        
        return PaginatedResponseDTO.of(vehiculosDTO, page, size, vehiculosPage.getTotalElements());
    }

    /**
     * Obtener vehículo por ID
     */
    @Transactional(readOnly = true)
    public VehiculoResponseDTO obtenerVehiculoPorId(String id) {
        logger.info("Obteniendo vehículo con ID: " + id);
        
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));
        
        return VehiculoResponseDTO.fromVehiculo(vehiculo);
    }

    /**
     * Obtener vehículo por placa
     */
    @Transactional(readOnly = true)
    public VehiculoResponseDTO obtenerVehiculoPorPlaca(String placa) {
        logger.info("Obteniendo vehículo con placa: " + placa);
        
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con placa: " + placa));
        
        return VehiculoResponseDTO.fromVehiculo(vehiculo);
    }

    /**
     * Actualizar vehículo existente
     */
    public VehiculoResponseDTO actualizarVehiculo(String id, VehiculoUpdateRequestDTO requestDTO) {
        logger.info("Actualizando vehículo con ID: " + id);
        
        // Verificar que el vehículo existe
        Vehiculo vehiculoExistente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));

        // Validar que la nueva placa no esté en uso por otro vehículo
        if (requestDTO.getPlaca() != null && !requestDTO.getPlaca().equals(vehiculoExistente.getPlaca())) {
            if (vehiculoRepository.existsByPlaca(requestDTO.getPlaca())) {
                throw new IllegalArgumentException("Ya existe un vehículo con la placa: " + requestDTO.getPlaca());
            }
        }

        // Actualizar campos
        if (requestDTO.getMarca() != null) {
            vehiculoExistente.setMarca(requestDTO.getMarca());
        }
        if (requestDTO.getModelo() != null) {
            vehiculoExistente.setModelo(requestDTO.getModelo());
        }
        if (requestDTO.getAño() != null) {
            vehiculoExistente.setAño(requestDTO.getAño());
        }
        if (requestDTO.getPlaca() != null) {
            vehiculoExistente.setPlaca(requestDTO.getPlaca());
        }
        if (requestDTO.getPrecio() != null) {
            vehiculoExistente.setPrecio(requestDTO.getPrecio());
        }
        if (requestDTO.getTipo() != null) {
            vehiculoExistente.setTipo(requestDTO.getTipo());
        }
        if (requestDTO.getDisponible() != null) {
            vehiculoExistente.setDisponible(requestDTO.getDisponible());
        }

        // Guardar cambios
        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculoExistente);
        logger.info("Vehículo actualizado exitosamente con ID: " + vehiculoActualizado.getId());

        return VehiculoResponseDTO.fromVehiculo(vehiculoActualizado);
    }

    /**
     * Eliminar vehículo
     */
    public void eliminarVehiculo(String id) {
        logger.info("Eliminando vehículo con ID: " + id);
        
        if (!vehiculoRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + id);
        }
        
        vehiculoRepository.deleteById(id);
        logger.info("Vehículo eliminado exitosamente con ID: " + id);
    }

    /**
     * Buscar vehículos por disponibilidad
     */
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> obtenerVehiculosDisponibles(Boolean disponible) {
        logger.info("Obteniendo vehículos con disponibilidad: " + disponible);
        
        List<Vehiculo> vehiculos = vehiculoRepository.findByDisponible(disponible);
        
        return vehiculos.stream()
                .map(VehiculoResponseDTO::fromVehiculo)
                .collect(Collectors.toList());
    }

    /**
     * Buscar vehículos por tipo
     */
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> obtenerVehiculosPorTipo(String tipo) {
        logger.info("Obteniendo vehículos por tipo: " + tipo);
        
        List<Vehiculo> vehiculos = vehiculoRepository.findByTipo(tipo);
        
        return vehiculos.stream()
                .map(VehiculoResponseDTO::fromVehiculo)
                .collect(Collectors.toList());
    }

    /**
     * Buscar vehículos por marca
     */
    @Transactional(readOnly = true)
    public List<VehiculoResponseDTO> obtenerVehiculosPorMarca(String marca) {
        logger.info("Obteniendo vehículos por marca: " + marca);
        
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarca(marca);
        
        return vehiculos.stream()
                .map(VehiculoResponseDTO::fromVehiculo)
                .collect(Collectors.toList());
    }

    /**
     * Búsqueda avanzada por texto
     */
    @Transactional(readOnly = true)
    public PaginatedResponseDTO<VehiculoResponseDTO> buscarVehiculos(String texto, int page, int size) {
        logger.info("Búsqueda avanzada de vehículos con texto: " + texto);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<Vehiculo> vehiculosPage = vehiculoRepository.buscarPorTexto(texto, pageable);
        
        List<VehiculoResponseDTO> vehiculosDTO = vehiculosPage.getContent().stream()
                .map(VehiculoResponseDTO::fromVehiculo)
                .collect(Collectors.toList());
        
        return PaginatedResponseDTO.of(vehiculosDTO, page, size, vehiculosPage.getTotalElements());
    }

    /**
     * Obtener estadísticas básicas
     */
    @Transactional(readOnly = true)
    public EstadisticasVehiculosDTO obtenerEstadisticas() {
        logger.info("Obteniendo estadísticas de vehículos");
        
        long totalVehiculos = vehiculoRepository.count();
        long vehiculosDisponibles = vehiculoRepository.countDisponibles();
        long vehiculosOcupados = totalVehiculos - vehiculosDisponibles;
        
        return EstadisticasVehiculosDTO.builder()
                .totalVehiculos(totalVehiculos)
                .vehiculosDisponibles(vehiculosDisponibles)
                .vehiculosOcupados(vehiculosOcupados)
                .porcentajeDisponibilidad(totalVehiculos > 0 ? 
                    (double) vehiculosDisponibles / totalVehiculos * 100 : 0.0)
                .build();
    }
}