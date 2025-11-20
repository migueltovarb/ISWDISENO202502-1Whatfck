package com.vehiculos.api.repository;

import com.vehiculos.api.model.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio MongoDB para la entidad Vehiculo
 * Proporciona operaciones CRUD y consultas personalizadas
 */
@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {

    /**
     * Buscar vehículo por placa (única)
     */
    Optional<Vehiculo> findByPlaca(String placa);

    /**
     * Verificar si existe un vehículo con la placa especificada
     */
    boolean existsByPlaca(String placa);

    /**
     * Buscar vehículos por disponibilidad
     */
    List<Vehiculo> findByDisponible(Boolean disponible);

    /**
     * Buscar vehículos por tipo
     */
    List<Vehiculo> findByTipo(String tipo);

    /**
     * Buscar vehículos por marca
     */
    List<Vehiculo> findByMarca(String marca);

    /**
     * Buscar vehículos por marca y modelo
     */
    List<Vehiculo> findByMarcaAndModelo(String marca, String modelo);

    /**
     * Buscar vehículos por rango de precios
     */
    List<Vehiculo> findByPrecioBetween(Double precioMin, Double precioMax);

    /**
     * Buscar vehículos disponibles por tipo
     */
    @Query("{ 'disponible': true, 'tipo': ?0 }")
    List<Vehiculo> findDisponiblesByTipo(String tipo);

    /**
     * Buscar vehículos con precio mayor al especificado
     */
    @Query("{ 'precio': { '$gte': ?0 } }")
    List<Vehiculo> findByPrecioMayorIgual(Double precio);

    /**
     * Buscar vehículos por año específico
     */
    List<Vehiculo> findByAño(Integer año);

    /**
     * Buscar vehículos entre un rango de años
     */
    List<Vehiculo> findByAñoBetween(Integer añoMin, Integer añoMax);

    /**
     * Buscar vehículos disponibles con paginación
     */
    @Query("{ 'disponible': ?0 }")
    Page<Vehiculo> findByDisponiblePaged(Boolean disponible, Pageable pageable);

    /**
     * Búsqueda avanzada por texto en marca, modelo y tipo
     */
    @Query("{ '$or': [ " +
           "{ 'marca': { '$regex': ?0, '$options': 'i' } }, " +
           "{ 'modelo': { '$regex': ?0, '$options': 'i' } }, " +
           "{ 'tipo': { '$regex': ?0, '$options': 'i' } } " +
           "] }")
    Page<Vehiculo> buscarPorTexto(String texto, Pageable pageable);

    /**
     * Contar vehículos por tipo
     */
    @Query(count = true)
    long countByTipo(String tipo);

    /**
     * Contar vehículos disponibles
     */
    @Query(value = "{ 'disponible': true }", count = true)
    long countDisponibles();

    /**
     * Obtener vehículos ordenados por precio
     */
    List<Vehiculo> findAllByOrderByPrecioAsc();

    /**
     * Obtener vehículos ordenados por año descendente
     */
    List<Vehiculo> findAllByOrderByAñoDesc();
}