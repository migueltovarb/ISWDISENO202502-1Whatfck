package com.sistema.services;

import com.sistema.model.Usuario;
import com.sistema.enums.Rol;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int nextId = 1;

    // Datos iniciales
    static {
        usuarios.add(new Usuario(nextId++, "Juan Pérez", "juan@email.com", Rol.CLIENTE));
        usuarios.add(new Usuario(nextId++, "María García", "maria@email.com", Rol.CLIENTE));
        usuarios.add(new Usuario(nextId++, "Carlos López", "carlos@email.com", Rol.SOPORTE));
    }

    public Usuario registrarUsuario(String nombre, String email, Rol rol) {
        // Validar que el email no exista
        if (obtenerUsuarioPorEmail(email) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(nextId++, nombre, email, rol);
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario autenticar(String email, String password) {
        // En un sistema real, verificaríamos la contraseña hasheada
        // Por ahora, solo verificamos que el email exista
        Usuario usuario = obtenerUsuarioPorEmail(email);
        if (usuario != null) {
            // Simular autenticación exitosa
            return usuario;
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> obtenerUsuariosPorRol(Rol rol) {
        return usuarios.stream()
                .filter(u -> u.getRol() == rol)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}