package com.sistema.services;

import com.sistema.MongoDBConnection;
import com.sistema.model.Notificacion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class NotificacionService {
    private static MongoDatabase database;
    private static MongoCollection<Document> notificacionesCollection;

    static {
        database = MongoDBConnection.getDatabase();
        notificacionesCollection = database.getCollection("notificaciones");
    }

    public static Notificacion crearNotificacion(String mensaje, int reclamoId) {
        // Obtener el próximo ID disponible
        int nextId = getNextId();

        Document notificacionDoc = new Document()
                .append("_id", nextId)
                .append("mensaje", mensaje)
                .append("reclamoId", reclamoId)
                .append("fechaEnvio", new java.util.Date());

        notificacionesCollection.insertOne(notificacionDoc);

        Notificacion notificacion = new Notificacion(nextId, mensaje, reclamoId);

        // En un sistema real, aquí enviaríamos el email
        System.out.println("📧 Notificación enviada: " + mensaje);

        return notificacion;
    }

    public static List<Notificacion> obtenerNotificacionesPorReclamo(int reclamoId) {
        List<Notificacion> notificaciones = new ArrayList<>();
        for (Document doc : notificacionesCollection.find(Filters.eq("reclamoId", reclamoId))) {
            notificaciones.add(documentToNotificacion(doc));
        }
        return notificaciones;
    }

    public static List<Notificacion> obtenerTodasNotificaciones() {
        List<Notificacion> notificaciones = new ArrayList<>();
        for (Document doc : notificacionesCollection.find()) {
            notificaciones.add(documentToNotificacion(doc));
        }
        return notificaciones;
    }

    public static String generarMensajeEstado(String tituloReclamo, String estadoAnterior, String estadoNuevo) {
        return "El estado del reclamo '" + tituloReclamo + "' cambió de " +
               estadoAnterior + " a " + estadoNuevo;
    }

    private static int getNextId() {
        Document maxIdDoc = notificacionesCollection.find()
                .sort(new Document("_id", -1))
                .first();

        if (maxIdDoc != null) {
            return maxIdDoc.getInteger("_id") + 1;
        }
        return 1;
    }

    private static Notificacion documentToNotificacion(Document doc) {
        return new Notificacion(
            doc.getInteger("_id"),
            doc.getString("mensaje"),
            doc.getInteger("reclamoId")
        );
    }
}