package com.sistema;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Clase para gestionar la conexión a MongoDB
 */
public class MongoDBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Configuración de conexión
    private static final String CONNECTION_STRING = "mongodb://reclamos_user:reclamos_password@localhost:27018/reclamosdb?authSource=reclamosdb";
    private static final String DATABASE_NAME = "reclamosdb";

    /**
     * Obtiene la instancia del cliente MongoDB
     */
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                System.out.println("✅ Conexión a MongoDB establecida exitosamente");
            } catch (Exception e) {
                System.err.println("❌ Error conectando a MongoDB: " + e.getMessage());
                System.err.println("💡 Asegúrate de que MongoDB esté ejecutándose en localhost:27018");
                throw new RuntimeException("No se pudo conectar a MongoDB", e);
            }
        }
        return mongoClient;
    }

    /**
     * Obtiene la base de datos
     */
    public static MongoDatabase getDatabase() {
        if (database == null) {
            database = getMongoClient().getDatabase(DATABASE_NAME);
        }
        return database;
    }

    /**
     * Cierra la conexión
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("🔌 Conexión a MongoDB cerrada");
        }
    }

    /**
     * Verifica si la conexión está activa
     */
    public static boolean isConnected() {
        try {
            getDatabase().runCommand(new org.bson.Document("ping", 1));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}