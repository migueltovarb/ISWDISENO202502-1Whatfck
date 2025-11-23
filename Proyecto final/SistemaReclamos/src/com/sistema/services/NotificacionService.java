package com.sistema.services;

import com.sistema.model.Notificacion;
import java.util.ArrayList;
import java.util.List;

public class NotificacionService {
    private static List<Notificacion> notificaciones = new ArrayList<>();
    private static int nextId = 1;

    public static Notificacion crearNotificacion(String mensaje, int reclamoId) {
        Notificacion notificacion = new Notificacion(nextId++, mensaje, reclamoId);
        notificaciones.add(notificacion);

        // En un sistema real, aquí enviaríamos el email
        System.out.println("📧 Notificación enviada: " + mensaje);

        return notificacion;
    }

    public static List<Notificacion> obtenerNotificacionesPorReclamo(int reclamoId) {
        return notificaciones.stream()
                .filter(n -> n.getReclamoId() == reclamoId)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static List<Notificacion> obtenerTodasNotificaciones() {
        return new ArrayList<>(notificaciones);
    }

    public static String generarMensajeEstado(String tituloReclamo, String estadoAnterior, String estadoNuevo) {
        return "El estado del reclamo '" + tituloReclamo + "' cambió de " +
               estadoAnterior + " a " + estadoNuevo;
    }
}