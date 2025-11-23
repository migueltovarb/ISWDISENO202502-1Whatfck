package com.sistema.services;

import com.sistema.model.Reclamo;
import com.sistema.model.Respuesta;
import com.sistema.model.Notificacion;
import com.sistema.enums.EstadoReclamo;
import com.sistema.enums.TipoReclamo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReclamoService {
    private static List<Reclamo> reclamos = new ArrayList<>();
    private static List<Respuesta> respuestas = new ArrayList<>();
    private static List<Notificacion> notificaciones = new ArrayList<>();
    private static int nextReclamoId = 1;
    private static int nextRespuestaId = 1;
    private static int nextNotificacionId = 1;

    // Datos iniciales
    static {
        reclamos.add(new Reclamo(nextReclamoId++, "Problema con suministro de agua",
                "No tengo agua desde hace 2 días", TipoReclamo.AGUA,
                "Calle Principal 123", 1));
        reclamos.add(new Reclamo(nextReclamoId++, "Factura de luz incorrecta",
                "Cargo extra no justificado", TipoReclamo.LUZ,
                "Avenida Central 456", 2));
    }

    public Reclamo crearReclamo(String titulo, String descripcion, TipoReclamo tipo,
                               String ubicacion, int clienteId) {
        Reclamo reclamo = new Reclamo(nextReclamoId++, titulo, descripcion, tipo,
                                     ubicacion, clienteId);
        reclamos.add(reclamo);

        // Crear notificación automática
        NotificacionService.crearNotificacion("Nuevo reclamo registrado: " + titulo, reclamo.getId());

        return reclamo;
    }

    public List<Reclamo> listarReclamosPorUsuario(int usuarioId) {
        return reclamos.stream()
                .filter(r -> r.getClienteId() == usuarioId)
                .collect(Collectors.toList());
    }

    public List<Reclamo> listarTodosReclamos() {
        return new ArrayList<>(reclamos);
    }

    public Reclamo obtenerReclamoPorId(int id) {
        return reclamos.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarEstado(int reclamoId, EstadoReclamo nuevoEstado, int usuarioId) {
        Reclamo reclamo = obtenerReclamoPorId(reclamoId);
        if (reclamo == null) {
            return false;
        }

        EstadoReclamo estadoAnterior = reclamo.getEstado();
        reclamo.setEstado(nuevoEstado);

        // Crear notificación de cambio de estado
        String mensaje = "El estado del reclamo '" + reclamo.getTitulo() +
                        "' cambió de " + estadoAnterior + " a " + nuevoEstado;
        NotificacionService.crearNotificacion(mensaje, reclamoId);

        return true;
    }

    public Respuesta agregarRespuesta(int reclamoId, String mensaje, int usuarioId) {
        Reclamo reclamo = obtenerReclamoPorId(reclamoId);
        if (reclamo == null) {
            throw new IllegalArgumentException("Reclamo no encontrado");
        }

        Respuesta respuesta = new Respuesta(nextRespuestaId++, mensaje, reclamoId, usuarioId);
        respuestas.add(respuesta);

        // Cambiar estado a PENDIENTE si estaba ABIERTO
        if (reclamo.getEstado() == EstadoReclamo.ABIERTO) {
            actualizarEstado(reclamoId, EstadoReclamo.PENDIENTE, usuarioId);
        }

        return respuesta;
    }

    public List<Respuesta> obtenerRespuestasPorReclamo(int reclamoId) {
        return respuestas.stream()
                .filter(r -> r.getReclamoId() == reclamoId)
                .sorted((r1, r2) -> r2.getFecha().compareTo(r1.getFecha())) // Más recientes primero
                .collect(Collectors.toList());
    }

    public void cerrarAutomatico() {
        // Cerrar reclamos sin respuesta del cliente en 3 días
        long tresDiasMillis = 3 * 24 * 60 * 60 * 1000; // 3 días en milisegundos

        for (Reclamo reclamo : reclamos) {
            if (reclamo.getEstado() == EstadoReclamo.RESUELTO) {
                long tiempoSinRespuesta = System.currentTimeMillis() - reclamo.getFechaCreacion().getTime();
                if (tiempoSinRespuesta > tresDiasMillis) {
                    reclamo.setEstado(EstadoReclamo.CERRADO);
                    NotificacionService.crearNotificacion(
                        "Reclamo cerrado automáticamente por falta de respuesta", reclamo.getId());
                }
            }
        }
    }

    public List<Reclamo> obtenerReclamosPorEstado(EstadoReclamo estado) {
        return reclamos.stream()
                .filter(r -> r.getEstado() == estado)
                .collect(Collectors.toList());
    }
}