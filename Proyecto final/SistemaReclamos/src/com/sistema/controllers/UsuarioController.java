package com.sistema.controllers;

import com.sistema.model.Usuario;
import com.sistema.services.UserService;
import com.sistema.enums.Rol;
import com.sistema.ConsoleUtils;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UsuarioController {
    private UserService userService;
    private Scanner scanner;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UsuarioController() {
        this.userService = new UserService();
        this.scanner = new Scanner(System.in);
    }

    public Usuario iniciarSesion() {
        ConsoleUtils.printHeader("Sistema de Reclamos");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Registrarse");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = leerOpcionNumerica(1, 3);
        scanner.nextLine(); // Consumir el salto de línea

        switch (opcion) {
            case 1:
                return login();
            case 2:
                return registro();
            case 3:
                ConsoleUtils.printInfo("¡Hasta luego!");
                System.exit(0);
                return null;
        }
        return null; // No debería llegar aquí
    }

    private Usuario login() {
        ConsoleUtils.printHeader("Iniciar Sesión");
        String email = leerEmail("Email: ");
        if (email == null) {
            return iniciarSesion(); // Volver al menú principal
        }

        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            ConsoleUtils.printInfo("Operación cancelada.");
            return iniciarSesion(); // Volver al menú principal
        }

        Usuario usuario = userService.autenticar(email, password);
        if (usuario != null) {
            ConsoleUtils.printSuccess("¡Bienvenido " + usuario.getNombre() + "! (Rol: " + usuario.getRol() + ")");
            return usuario;
        } else {
            ConsoleUtils.printError("Credenciales incorrectas. Intente nuevamente.");
            return login();
        }
    }

    private Usuario registro() {
        ConsoleUtils.printHeader("Registro de Usuario");
        String nombre = leerNombre("Nombre completo: ");
        if (nombre == null) {
            return iniciarSesion(); // Volver al menú principal
        }

        String email = leerEmail("Email: ");
        if (email == null) {
            return iniciarSesion(); // Volver al menú principal
        }

        // Todos los usuarios registrados son automáticamente CLIENTES
        // El soporte ya está predefinido en la base de datos
        Rol rol = Rol.CLIENTE;

        try {
            Usuario usuario = userService.registrarUsuario(nombre, email, rol);
            ConsoleUtils.printSuccess("Usuario registrado exitosamente como Cliente!");
            ConsoleUtils.printInfo("Ahora puede iniciar sesión con sus credenciales.");
            return iniciarSesion();
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Error: " + e.getMessage());
            return registro();
        }
    }

    // Métodos de validación y entrada segura
    private String leerNombre(String prompt) {
        while (true) {
            System.out.print(prompt);
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                ConsoleUtils.printInfo("Operación cancelada.");
                return null; // Cancelar operación
            } else if (nombre.length() < 2) {
                ConsoleUtils.printError("El nombre debe tener al menos 2 caracteres. Intente nuevamente o presione Enter para cancelar.");
            } else {
                return nombre;
            }
        }
    }

    private String leerEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                ConsoleUtils.printInfo("Operación cancelada.");
                return null; // Cancelar operación
            } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                ConsoleUtils.printError("Formato de email inválido. Intente nuevamente o presione Enter para cancelar.");
            } else {
                return email;
            }
        }
    }

    private int leerOpcionNumerica(int min, int max) {
        while (true) {
            try {
                int opcion = scanner.nextInt();
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    ConsoleUtils.printError("Opción fuera de rango. Seleccione entre " + min + " y " + max + ".");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    public void cerrarSesion() {
        System.out.println("Sesión cerrada. ¡Hasta luego!");
    }
}