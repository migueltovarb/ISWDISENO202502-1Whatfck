package com.sistema.services;

import com.sistema.MongoDBConnection;
import com.sistema.model.Reclamo;
import com.sistema.model.Respuesta;
import com.sistema.model.Notificacion;
import com.sistema.enums.EstadoReclamo;
import com.sistema.enums.TipoReclamo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ReclamoService {
    private MongoDatabase database;
    private MongoCollection<Document> reclamosCollection;
    private MongoCollection<Document> respuestasCollection;
    private MongoCollection<Document> notificacionesCollection;

    public ReclamoService() {
        this.database = MongoDBConnection.getDatabase();
        this.reclamosCollection = database.getCollection("reclamos");
        this.respuestasCollection = database.getCollection("respuestas");
        this.notificacionesCollection = database.getCollection("notificaciones");
    }

    public Reclamo crearReclamo(String titulo, String descripcion, TipoReclamo tipo,
                               String ubicacion, int clienteId) {
        // Obtener el próximo ID disponible
        int nextId = getNextReclamoId();

        Document reclamoDoc = new Document()
                .append("_id", nextId)
                .append("titulo", titulo)
                .append("descripcion", descripcion)
                .append("tipo", tipo.toString())
                .append("ubicacion", ubicacion)
                .append("estado", EstadoReclamo.ABIERTO.toString())
                .append("clienteId", clienteId)
                .append("fechaCreacion", new Date());

        reclamosCollection.insertOne(reclamoDoc);

        Reclamo reclamo = new Reclamo(nextId, titulo, descripcion, tipo, ubicacion, clienteId);
        reclamo.setEstado(EstadoReclamo.ABIERTO);

        // Crear notificación automática
        NotificacionService.crearNotificacion("Nuevo reclamo registrado: " + titulo, reclamo.getId());

        return reclamo;
    }

    public List<Reclamo> listarReclamosPorUsuario(int usuarioId) {
        List<Reclamo> reclamos = new ArrayList<>();
        for (Document doc : reclamosCollection.find(Filters.eq("clienteId", usuarioId))) {
            reclamos.add(documentToReclamo(doc));
        }
        return reclamos;
    }

    public List<Reclamo> listarTodosReclamos() {
        List<Reclamo> reclamos = new ArrayList<>();
        for (Document doc : reclamosCollection.find()) {
            reclamos.add(documentToReclamo(doc));
        }
        return reclamos;
    }

    public Reclamo obtenerReclamoPorId(int id) {
        Document doc = reclamosCollection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToReclamo(doc) : null;
    }

    public boolean actualizarEstado(int reclamoId, EstadoReclamo nuevoEstado, int usuarioId) {
        Document reclamoDoc = reclamosCollection.find(Filters.eq("_id", reclamoId)).first();
        if (reclamoDoc == null) {
            return false;
        }

        String estadoAnterior = reclamoDoc.getString("estado");

        // Actualizar estado en la base de datos
        reclamosCollection.updateOne(
            Filters.eq("_id", reclamoId),
            Updates.set("estado", nuevoEstado.toString())
        );

        // Crear notificación de cambio de estado
        String titulo = reclamoDoc.getString("titulo");
        String mensaje = "El estado del reclamo '" + titulo +
                        "' cambió de " + estadoAnterior + " a " + nuevoEstado;
        NotificacionService.crearNotificacion(mensaje, reclamoId);

        return true;
    }

    public boolean cerrarSatisfactoriamente(int reclamoId, int clienteId) {
        Document reclamoDoc = reclamosCollection.find(Filters.eq("_id", reclamoId)).first();
        if (reclamoDoc == null) {
            return false;
        }

        // Verificar que el cliente sea el propietario del reclamo
        if (reclamoDoc.getInteger("clienteId") != clienteId) {
            return false;
        }

        // Verificar que el estado permita cierre satisfactorio
        String estadoActual = reclamoDoc.getString("estado");
        if (!"RESUELTO".equals(estadoActual) && !"PENDIENTE".equals(estadoActual)) {
            return false;
        }

        // Actualizar estado a CERRADO
        reclamosCollection.updateOne(
            Filters.eq("_id", reclamoId),
            Updates.set("estado", EstadoReclamo.CERRADO.toString())
        );

        // Crear notificación de cierre satisfactorio
        String titulo = reclamoDoc.getString("titulo");
        String mensaje = "El reclamo '" + titulo + "' ha sido cerrado satisfactoriamente por el cliente.";
        NotificacionService.crearNotificacion(mensaje, reclamoId);

        return true;
    }

    public Respuesta agregarRespuesta(int reclamoId, String mensaje, int usuarioId) {
        Document reclamoDoc = reclamosCollection.find(Filters.eq("_id", reclamoId)).first();
        if (reclamoDoc == null) {
            throw new IllegalArgumentException("Reclamo no encontrado");
        }

        int nextRespuestaId = getNextRespuestaId();

        Document respuestaDoc = new Document()
                .append("_id", nextRespuestaId)
                .append("mensaje", mensaje)
                .append("reclamoId", reclamoId)
                .append("usuarioId", usuarioId)
                .append("fecha", new Date());

        respuestasCollection.insertOne(respuestaDoc);

        // Cambiar estado a PENDIENTE si estaba ABIERTO
        String estadoActual = reclamoDoc.getString("estado");
        if ("ABIERTO".equals(estadoActual)) {
            actualizarEstado(reclamoId, EstadoReclamo.PENDIENTE, usuarioId);
        }

        return new Respuesta(nextRespuestaId, mensaje, reclamoId, usuarioId);
    }

    public List<Respuesta> obtenerRespuestasPorReclamo(int reclamoId) {
        List<Respuesta> respuestas = new ArrayList<>();
        for (Document doc : respuestasCollection.find(Filters.eq("reclamoId", reclamoId))) {
            respuestas.add(documentToRespuesta(doc));
        }
        // Ordenar por fecha (más recientes primero)
        respuestas.sort((r1, r2) -> r2.getFecha().compareTo(r1.getFecha()));
        return respuestas;
    }

    public void cerrarAutomatico() {
        // Cerrar reclamos sin respuesta del cliente en 3 días
        long tresDiasMillis = 3 * 24 * 60 * 60 * 1000; // 3 días en milisegundos
        Date limiteFecha = new Date(System.currentTimeMillis() - tresDiasMillis);

        // Buscar reclamos resueltos con fecha de creación anterior al límite
        for (Document doc : reclamosCollection.find(
            Filters.and(
                Filters.eq("estado", "RESUELTO"),
                Filters.lt("fechaCreacion", limiteFecha)
            )
        )) {
            int reclamoId = doc.getInteger("_id");
            reclamosCollection.updateOne(
                Filters.eq("_id", reclamoId),
                Updates.set("estado", EstadoReclamo.CERRADO.toString())
            );

            NotificacionService.crearNotificacion(
                "Reclamo cerrado automáticamente por falta de respuesta", reclamoId);
        }
    }

    public List<Reclamo> obtenerReclamosPorEstado(EstadoReclamo estado) {
        List<Reclamo> reclamos = new ArrayList<>();
        for (Document doc : reclamosCollection.find(Filters.eq("estado", estado.toString()))) {
            reclamos.add(documentToReclamo(doc));
        }
        return reclamos;
    }

    private int getNextReclamoId() {
        Document maxIdDoc = reclamosCollection.find()
                .sort(new Document("_id", -1))
                .first();

        if (maxIdDoc != null) {
            return maxIdDoc.getInteger("_id") + 1;
        }
        return 1;
    }

    private int getNextRespuestaId() {
        Document maxIdDoc = respuestasCollection.find()
                .sort(new Document("_id", -1))
                .first();

        if (maxIdDoc != null) {
            return maxIdDoc.getInteger("_id") + 1;
        }
        return 1;
    }

    private Reclamo documentToReclamo(Document doc) {
        return new Reclamo(
            doc.getInteger("_id"),
            doc.getString("titulo"),
            doc.getString("descripcion"),
            TipoReclamo.valueOf(doc.getString("tipo")),
            doc.getString("ubicacion"),
            doc.getInteger("clienteId")
        );
    }

    private Respuesta documentToRespuesta(Document doc) {
        return new Respuesta(
            doc.getInteger("_id"),
            doc.getString("mensaje"),
            doc.getInteger("reclamoId"),
            doc.getInteger("usuarioId")
        );
    }
}