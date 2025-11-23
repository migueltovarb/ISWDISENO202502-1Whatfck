package com.sistema;

import com.sistema.controllers.UsuarioController;
import com.sistema.controllers.ReclamoController;
import com.sistema.model.Usuario;

public class SistemaReclamos {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Sistema de Reclamos...");

        UsuarioController usuarioController = new UsuarioController();

        while (true) {
            // Mostrar menú de autenticación
            Usuario usuarioActual = usuarioController.iniciarSesion();

            if (usuarioActual != null) {
                // Usuario autenticado, mostrar menú principal
                ReclamoController reclamoController = new ReclamoController(usuarioActual);
                reclamoController.mostrarMenu();

                // Cuando el usuario cierra sesión, volver al login
                usuarioController.cerrarSesion();
            }
        }
    }
}
