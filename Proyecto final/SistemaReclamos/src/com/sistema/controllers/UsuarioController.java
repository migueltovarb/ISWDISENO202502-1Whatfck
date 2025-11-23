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
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

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
        String email = leerEmail("Email: ");

        System.out.println("Tipo de usuario:");
        System.out.println("1. Cliente");
        System.out.println("2. Soporte");
        System.out.print("Seleccione: ");

        int tipoUsuario = leerOpcionNumerica(1, 2);
        scanner.nextLine();

        Rol rol = (tipoUsuario == 1) ? Rol.CLIENTE : Rol.SOPORTE;

        try {
            Usuario usuario = userService.registrarUsuario(nombre, email, rol);
            ConsoleUtils.printSuccess("Usuario registrado exitosamente!");
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
                ConsoleUtils.printError("El nombre no puede estar vacío. Intente nuevamente.");
            } else if (nombre.length() < 2) {
                ConsoleUtils.printError("El nombre debe tener al menos 2 caracteres. Intente nuevamente.");
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
                ConsoleUtils.printError("El email no puede estar vacío. Intente nuevamente.");
            } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                ConsoleUtils.printError("Formato de email inválido. Intente nuevamente.");
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