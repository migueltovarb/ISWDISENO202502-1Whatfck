package com.vehiculos.api.controller;

import com.vehiculos.api.dto.*;
import com.vehiculos.api.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controlador REST para la gestión de vehículos
 * Proporciona endpoints para operaciones CRUD y consultas avanzadas
 */
@RestController
@RequestMapping("/vehiculos")
@Tag(name = "Vehiculos", description = "API para gestión de vehículos")
public class VehiculoController {

    private static final Logger logger = Logger.getLogger(VehiculoController.class.getName());
    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // ========== ENDPOINTS CRUD ==========

    /**
     * Crear un nuevo vehículo
     */
    @PostMapping
    @Operation(
        summary = "Crear vehículo",
        description = "Crea un nuevo vehículo en el sistema",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un vehículo con esa placa")
    })
    public ResponseEntity<VehiculoResponseDTO> crearVehiculo(
            @Valid @RequestBody VehiculoCreateRequestDTO requestDTO) {
        logger.info("POST /vehiculos - Creando vehículo con placa: " + requestDTO.getPlaca());
        VehiculoResponseDTO vehiculo = vehiculoService.crearVehiculo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculo);
    }

    /**
     * Obtener todos los vehículos con paginación
     */
    @GetMapping
    @Operation(
        summary = "Listar vehículos",
        description = "Obtiene todos los vehículos del sistema con paginación",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    })
    public ResponseEntity<PaginatedResponseDTO<VehiculoResponseDTO>> obtenerTodosLosVehiculos(
            @Parameter(description = "Número de página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        logger.info("GET /vehiculos - Obteniendo vehículos página: " + page + ", tamaño: " + size);
        PaginatedResponseDTO<VehiculoResponseDTO> vehiculos = vehiculoService.obtenerTodosLosVehiculos(page, size);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Obtener vehículo por ID
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener vehículo por ID",
        description = "Obtiene un vehículo específico por su ID",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<VehiculoResponseDTO> obtenerVehiculoPorId(
            @Parameter(description = "ID del vehículo", required = true)
            @PathVariable String id) {
        logger.info("GET /vehiculos/" + id + " - Obteniendo vehículo");
        VehiculoResponseDTO vehiculo = vehiculoService.obtenerVehiculoPorId(id);
        return ResponseEntity.ok(vehiculo);
    }

    /**
     * Obtener vehículo por placa
     */
    @GetMapping("/placa/{placa}")
    @Operation(
        summary = "Obtener vehículo por placa",
        description = "Obtiene un vehículo específico por su placa",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<VehiculoResponseDTO> obtenerVehiculoPorPlaca(
            @Parameter(description = "Placa del vehículo", required = true)
            @PathVariable String placa) {
        logger.info("GET /vehiculos/placa/" + placa + " - Obteniendo vehículo");
        VehiculoResponseDTO vehiculo = vehiculoService.obtenerVehiculoPorPlaca(placa);
        return ResponseEntity.ok(vehiculo);
    }

    /**
     * Actualizar vehículo
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar vehículo",
        description = "Actualiza un vehículo existente",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un vehículo con esa placa")
    })
    public ResponseEntity<VehiculoResponseDTO> actualizarVehiculo(
            @Parameter(description = "ID del vehículo", required = true)
            @PathVariable String id,
            @Valid @RequestBody VehiculoUpdateRequestDTO requestDTO) {
        logger.info("PUT /vehiculos/" + id + " - Actualizando vehículo");
        VehiculoResponseDTO vehiculo = vehiculoService.actualizarVehiculo(id, requestDTO);
        return ResponseEntity.ok(vehiculo);
    }

    /**
     * Eliminar vehículo
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar vehículo",
        description = "Elimina un vehículo del sistema",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    public ResponseEntity<Void> eliminarVehiculo(
            @Parameter(description = "ID del vehículo", required = true)
            @PathVariable String id) {
        logger.info("DELETE /vehiculos/" + id + " - Eliminando vehículo");
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS DE CONSULTA ==========

    /**
     * Buscar vehículos por disponibilidad
     */
    @GetMapping("/disponibles")
    @Operation(
        summary = "Buscar vehículos por disponibilidad",
        description = "Obtiene vehículos filtrados por disponibilidad",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    })
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerVehiculosDisponibles(
            @Parameter(description = "Disponibilidad del vehículo", required = true)
            @RequestParam Boolean disponible) {
        logger.info("GET /vehiculos/disponibles?disponible=" + disponible + " - Buscando vehículos");
        List<VehiculoResponseDTO> vehiculos = vehiculoService.obtenerVehiculosDisponibles(disponible);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Buscar vehículos por tipo
     */
    @GetMapping("/tipo/{tipo}")
    @Operation(
        summary = "Buscar vehículos por tipo",
        description = "Obtiene vehículos filtrados por tipo",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    })
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerVehiculosPorTipo(
            @Parameter(description = "Tipo de vehículo", required = true)
            @PathVariable String tipo) {
        logger.info("GET /vehiculos/tipo/" + tipo + " - Buscando vehículos por tipo");
        List<VehiculoResponseDTO> vehiculos = vehiculoService.obtenerVehiculosPorTipo(tipo);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Buscar vehículos por marca
     */
    @GetMapping("/marca/{marca}")
    @Operation(
        summary = "Buscar vehículos por marca",
        description = "Obtiene vehículos filtrados por marca",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente")
    })
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerVehiculosPorMarca(
            @Parameter(description = "Marca del vehículo", required = true)
            @PathVariable String marca) {
        logger.info("GET /vehiculos/marca/" + marca + " - Buscando vehículos por marca");
        List<VehiculoResponseDTO> vehiculos = vehiculoService.obtenerVehiculosPorMarca(marca);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Búsqueda avanzada de vehículos
     */
    @GetMapping("/buscar")
    @Operation(
        summary = "Búsqueda avanzada de vehículos",
        description = "Busca vehículos por texto en marca, modelo o tipo",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos exitosamente")
    })
    public ResponseEntity<PaginatedResponseDTO<VehiculoResponseDTO>> buscarVehiculos(
            @Parameter(description = "Texto de búsqueda", required = true)
            @RequestParam String texto,
            @Parameter(description = "Número de página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        logger.info("GET /vehiculos/buscar?texto=" + texto + " - Buscando vehículos");
        PaginatedResponseDTO<VehiculoResponseDTO> vehiculos = vehiculoService.buscarVehiculos(texto, page, size);
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * Obtener estadísticas de vehículos
     */
    @GetMapping("/estadisticas")
    @Operation(
        summary = "Obtener estadísticas de vehículos",
        description = "Obtiene estadísticas generales sobre los vehículos del sistema",
        tags = {"Vehiculos"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    })
    public ResponseEntity<EstadisticasVehiculosDTO> obtenerEstadisticas() {
        logger.info("GET /vehiculos/estadisticas - Obteniendo estadísticas");
        EstadisticasVehiculosDTO estadisticas = vehiculoService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

    // ========== HEALTH CHECK ==========

    /**
     * Health check para la API de vehículos
     */
    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Verifica el estado de la API",
        tags = {"Vehiculos"}
    )
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API de vehículos funcionando correctamente");
    }
}