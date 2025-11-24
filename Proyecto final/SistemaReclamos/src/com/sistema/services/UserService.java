package com.sistema.services;

import com.sistema.MongoDBConnection;
import com.sistema.model.Usuario;
import com.sistema.enums.Rol;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private MongoDatabase database;
    private MongoCollection<Document> usuariosCollection;

    public UserService() {
        this.database = MongoDBConnection.getDatabase();
        this.usuariosCollection = database.getCollection("usuarios");
    }

    public Usuario registrarUsuario(String nombre, String email, Rol rol) {
        return registrarUsuario(nombre, email, "password", rol);
    }

    public Usuario registrarUsuario(String nombre, String email, String password, Rol rol) {
        // Validar que el email no exista
        if (obtenerUsuarioPorEmail(email) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Obtener el próximo ID disponible
        int nextId = getNextId();

        Document usuarioDoc = new Document()
                .append("_id", nextId)
                .append("nombre", nombre)
                .append("email", email)
                .append("password", password)
                .append("rol", rol.toString())
                .append("fechaCreacion", new java.util.Date());

        usuariosCollection.insertOne(usuarioDoc);

        return new Usuario(nextId, nombre, email, password, rol);
    }

    public Usuario autenticar(String email, String password) {
        // En un sistema real, verificaríamos la contraseña hasheada
        // Por ahora, verificamos que el email exista y la contraseña coincida
        Usuario usuario = obtenerUsuarioPorEmail(email);
        if (usuario != null && password != null && password.equals(usuario.getPassword())) {
            return usuario;
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : usuariosCollection.find()) {
            usuarios.add(documentToUsuario(doc));
        }
        return usuarios;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        Document doc = usuariosCollection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToUsuario(doc) : null;
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        Document doc = usuariosCollection.find(Filters.eq("email", email)).first();
        return doc != null ? documentToUsuario(doc) : null;
    }

    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : usuariosCollection.find(Filters.eq("rol", rol.toString()))) {
            usuarios.add(documentToUsuario(doc));
        }
        return usuarios;
    }

    private int getNextId() {
        Document maxIdDoc = usuariosCollection.find()
                .sort(new Document("_id", -1))
                .first();

        if (maxIdDoc != null) {
            return maxIdDoc.getInteger("_id") + 1;
        }
        return 1;
    }

    private Usuario documentToUsuario(Document doc) {
        return new Usuario(
            doc.getInteger("_id"),
            doc.getString("nombre"),
            doc.getString("email"),
            doc.getString("password"),
            Rol.valueOf(doc.getString("rol"))
        );
    }
}