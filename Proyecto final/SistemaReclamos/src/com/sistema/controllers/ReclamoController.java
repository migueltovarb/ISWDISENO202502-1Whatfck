package com.sistema.controllers;

import com.sistema.model.Reclamo;
import com.sistema.model.Respuesta;
import com.sistema.model.Usuario;
import com.sistema.services.ReclamoService;
import com.sistema.enums.EstadoReclamo;
import com.sistema.enums.TipoReclamo;
import com.sistema.enums.Rol;
import com.sistema.ConsoleUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReclamoController {
    private ReclamoService reclamoService;
    private Scanner scanner;
    private Usuario usuarioActual;
    private List<String> breadcrumbs;

    public ReclamoController(Usuario usuarioActual) {
        this.reclamoService = new ReclamoService();
        this.scanner = new Scanner(System.in);
        this.usuarioActual = usuarioActual;
        this.breadcrumbs = new ArrayList<>();
        this.breadcrumbs.add("Inicio");
    }

    public void mostrarMenu() {
        while (true) {
            mostrarBreadcrumbs();
            ConsoleUtils.printHeader("Menú Principal");
            ConsoleUtils.printInfo("Usuario: " + usuarioActual.getNombre() + " (" + usuarioActual.getRol() + ")");

            if (usuarioActual.getRol() == Rol.CLIENTE) {
                mostrarMenuCliente();
            } else {
                mostrarMenuSoporte();
            }

            System.out.print("Seleccione una opción: ");
            int maxOpcion = (usuarioActual.getRol() == Rol.CLIENTE) ? 4 : 5;
            int opcion = leerOpcionNumerica(0, maxOpcion);
            scanner.nextLine();

            if (usuarioActual.getRol() == Rol.CLIENTE) {
                procesarOpcionCliente(opcion);
            } else {
                procesarOpcionSoporte(opcion);
            }
        }
    }

    private void mostrarBreadcrumbs() {
        if (!breadcrumbs.isEmpty()) {
            System.out.println(ConsoleUtils.CYAN + "Ubicación: " + String.join(" > ", breadcrumbs) + ConsoleUtils.RESET);
        }
    }

    private void mostrarMenuCliente() {
        System.out.println("1. Ver mis reclamos");
        System.out.println("2. Crear nuevo reclamo");
        System.out.println("3. Ver detalle de reclamo");
        System.out.println("4. Editar reclamo");
        System.out.println("0. Cerrar sesión");
    }

    private void mostrarMenuSoporte() {
        System.out.println("1. Ver todos los reclamos");
        System.out.println("2. Ver reclamos pendientes");
        System.out.println("3. Responder a reclamo");
        System.out.println("4. Cambiar estado de reclamo");
        System.out.println("5. Buscar reclamos");
        System.out.println("0. Cerrar sesión");
    }

    private void procesarOpcionCliente(int opcion) {
        switch (opcion) {
            case 1:
                breadcrumbs.add("Mis Reclamos");
                verMisReclamos();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 2:
                breadcrumbs.add("Crear Reclamo");
                crearNuevoReclamo();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 3:
                breadcrumbs.add("Detalle Reclamo");
                verDetalleReclamo();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 4:
                breadcrumbs.add("Editar Reclamo");
                editarReclamo();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 0:
                return;
            default:
                ConsoleUtils.printError("Opción inválida.");
        }
    }

    private void procesarOpcionSoporte(int opcion) {
        switch (opcion) {
            case 1:
                breadcrumbs.add("Todos los Reclamos");
                verTodosReclamos();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 2:
                breadcrumbs.add("Reclamos Pendientes");
                verReclamosPendientes();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 3:
                breadcrumbs.add("Responder Reclamo");
                responderReclamo();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 4:
                breadcrumbs.add("Cambiar Estado");
                cambiarEstadoReclamo();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 5:
                breadcrumbs.add("Buscar Reclamos");
                buscarReclamos();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 0:
                return;
            default:
                ConsoleUtils.printError("Opción inválida.");
        }
    }

    private void verMisReclamos() {
        List<Reclamo> reclamos = reclamoService.listarReclamosPorUsuario(usuarioActual.getId());
        if (reclamos.isEmpty()) {
            ConsoleUtils.printWarning("No tienes reclamos registrados.");
            return;
        }

        ConsoleUtils.printHeader("Mis Reclamos");
        ConsoleUtils.printTableSeparator(3);
        ConsoleUtils.printTableRow("ID", "Título", "Estado");
        ConsoleUtils.printTableSeparator(3);
        for (Reclamo r : reclamos) {
            ConsoleUtils.printTableRow(
                String.valueOf(r.getId()),
                r.getTitulo().length() > 13 ? r.getTitulo().substring(0, 10) + "..." : r.getTitulo(),
                r.getEstado().toString()
            );
        }
        ConsoleUtils.printTableSeparator(3);
    }

    private void crearNuevoReclamo() {
        ConsoleUtils.printHeader("Crear Nuevo Reclamo");
        String titulo = leerTextoNoVacio("Título: ");
        String descripcion = leerTextoNoVacio("Descripción: ");

        System.out.println("Tipo de servicio:");
        System.out.println("1. Agua");
        System.out.println("2. Luz");
        System.out.println("3. Internet");
        System.out.println("4. Alcantarillado");
        System.out.print("Seleccione: ");

        int tipo = leerOpcionNumerica(1, 4);
        scanner.nextLine();

        TipoReclamo tipoReclamo;
        switch (tipo) {
            case 1: tipoReclamo = TipoReclamo.AGUA; break;
            case 2: tipoReclamo = TipoReclamo.LUZ; break;
            case 3: tipoReclamo = TipoReclamo.INTERNET; break;
            case 4: tipoReclamo = TipoReclamo.ALCANTARILLADO; break;
            default:
                // No debería llegar aquí
                return;
        }

        String ubicacion = leerTextoNoVacio("Ubicación: ");

        Reclamo reclamo = reclamoService.crearReclamo(titulo, descripcion, tipoReclamo,
                                                     ubicacion, usuarioActual.getId());
        ConsoleUtils.printSuccess("Reclamo creado exitosamente con ID: " + reclamo.getId());
    }

    private void verDetalleReclamo() {
        int id = leerIdNumerico("Ingrese ID del reclamo: ");

        Reclamo reclamo = reclamoService.obtenerReclamoPorId(id);
        if (reclamo == null || reclamo.getClienteId() != usuarioActual.getId()) {
            ConsoleUtils.printError("Reclamo no encontrado o no tienes acceso.");
            return;
        }

        ConsoleUtils.printHeader("Detalle del Reclamo");
        System.out.println("ID: " + reclamo.getId());
        System.out.println("Título: " + reclamo.getTitulo());
        System.out.println("Descripción: " + reclamo.getDescripcion());
        System.out.println("Tipo: " + reclamo.getTipo());
        System.out.println("Ubicación: " + reclamo.getUbicacion());
        System.out.println("Estado: " + reclamo.getEstado());
        System.out.println("Fecha: " + reclamo.getFechaCreacion());

        List<Respuesta> respuestas = reclamoService.obtenerRespuestasPorReclamo(id);
        if (!respuestas.isEmpty()) {
            ConsoleUtils.printSubHeader("Respuestas");
            for (Respuesta r : respuestas) {
                System.out.println("[" + r.getFecha() + "] " + r.getMensaje());
            }
        }
    }

    private void verTodosReclamos() {
        List<Reclamo> reclamos = reclamoService.listarTodosReclamos();
        ConsoleUtils.printHeader("Todos los Reclamos");
        if (reclamos.isEmpty()) {
            ConsoleUtils.printWarning("No hay reclamos registrados.");
            return;
        }
        ConsoleUtils.printTableSeparator(4);
        ConsoleUtils.printTableRow("ID", "Título", "Estado", "Cliente ID");
        ConsoleUtils.printTableSeparator(4);
        for (Reclamo r : reclamos) {
            ConsoleUtils.printTableRow(
                String.valueOf(r.getId()),
                r.getTitulo().length() > 13 ? r.getTitulo().substring(0, 10) + "..." : r.getTitulo(),
                r.getEstado().toString(),
                String.valueOf(r.getClienteId())
            );
        }
        ConsoleUtils.printTableSeparator(4);
    }

    private void verReclamosPendientes() {
        List<Reclamo> reclamos = reclamoService.obtenerReclamosPorEstado(EstadoReclamo.PENDIENTE);
        ConsoleUtils.printHeader("Reclamos Pendientes");
        if (reclamos.isEmpty()) {
            ConsoleUtils.printWarning("No hay reclamos pendientes.");
            return;
        }
        ConsoleUtils.printTableSeparator(3);
        ConsoleUtils.printTableRow("ID", "Título", "Cliente ID");
        ConsoleUtils.printTableSeparator(3);
        for (Reclamo r : reclamos) {
            ConsoleUtils.printTableRow(
                String.valueOf(r.getId()),
                r.getTitulo().length() > 13 ? r.getTitulo().substring(0, 10) + "..." : r.getTitulo(),
                String.valueOf(r.getClienteId())
            );
        }
        ConsoleUtils.printTableSeparator(3);
    }

    private void responderReclamo() {
        int reclamoId = leerIdNumerico("ID del reclamo a responder: ");
        String mensaje = leerTextoNoVacio("Mensaje de respuesta: ");

        try {
            Respuesta respuesta = reclamoService.agregarRespuesta(reclamoId, mensaje, usuarioActual.getId());
            ConsoleUtils.printSuccess("Respuesta enviada exitosamente.");
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoReclamo() {
        int reclamoId = leerIdNumerico("ID del reclamo: ");

        System.out.println("Nuevo estado:");
        System.out.println("1. Abierto");
        System.out.println("2. Pendiente");
        System.out.println("3. En Proceso");
        System.out.println("4. Resuelto");
        System.out.println("5. Cerrado");
        System.out.print("Seleccione: ");

        int estado = leerOpcionNumerica(1, 5);
        scanner.nextLine();

        EstadoReclamo nuevoEstado;
        switch (estado) {
            case 1: nuevoEstado = EstadoReclamo.ABIERTO; break;
            case 2: nuevoEstado = EstadoReclamo.PENDIENTE; break;
            case 3: nuevoEstado = EstadoReclamo.EN_PROCESO; break;
            case 4: nuevoEstado = EstadoReclamo.RESUELTO; break;
            case 5: nuevoEstado = EstadoReclamo.CERRADO; break;
            default:
                // No debería llegar aquí
                return;
        }

        if (nuevoEstado == EstadoReclamo.CERRADO) {
            if (!confirmarAccion("¿Está seguro de cerrar este reclamo? Esta acción es irreversible.")) {
                ConsoleUtils.printInfo("Operación cancelada.");
                return;
            }
        }

        if (reclamoService.actualizarEstado(reclamoId, nuevoEstado, usuarioActual.getId())) {
            ConsoleUtils.printSuccess("Estado actualizado exitosamente.");
        } else {
            ConsoleUtils.printError("Reclamo no encontrado.");
        }
    }

    // Métodos de validación y entrada segura
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

    private String leerTextoNoVacio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                ConsoleUtils.printError("Este campo no puede estar vacío. Intente nuevamente.");
            } else {
                return texto;
            }
        }
    }

    private int leerIdNumerico(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int id = scanner.nextInt();
                scanner.nextLine();
                if (id > 0) {
                    return id;
                } else {
                    ConsoleUtils.printError("ID debe ser un número positivo.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Entrada inválida. Ingrese un número válido.");
                scanner.nextLine();
            }
        }
    }

    private void editarReclamo() {
        int id = leerIdNumerico("Ingrese ID del reclamo a editar: ");
        Reclamo reclamo = reclamoService.obtenerReclamoPorId(id);
        if (reclamo == null || reclamo.getClienteId() != usuarioActual.getId()) {
            ConsoleUtils.printError("Reclamo no encontrado o no tienes acceso.");
            return;
        }

        if (reclamo.getEstado() != EstadoReclamo.ABIERTO && reclamo.getEstado() != EstadoReclamo.PENDIENTE) {
            ConsoleUtils.printError("Solo puedes editar reclamos en estado Abierto o Pendiente.");
            return;
        }

        ConsoleUtils.printHeader("Editar Reclamo");
        System.out.println("Deja en blanco para mantener el valor actual.");

        String nuevoTitulo = leerTextoOpcional("Título actual: " + reclamo.getTitulo() + "\nNuevo título: ");
        if (!nuevoTitulo.isEmpty()) {
            reclamo.setTitulo(nuevoTitulo);
        }

        String nuevaDescripcion = leerTextoOpcional("Descripción actual: " + reclamo.getDescripcion() + "\nNueva descripción: ");
        if (!nuevaDescripcion.isEmpty()) {
            reclamo.setDescripcion(nuevaDescripcion);
        }

        String nuevaUbicacion = leerTextoOpcional("Ubicación actual: " + reclamo.getUbicacion() + "\nNueva ubicación: ");
        if (!nuevaUbicacion.isEmpty()) {
            reclamo.setUbicacion(nuevaUbicacion);
        }

        ConsoleUtils.printSuccess("Reclamo actualizado exitosamente.");
    }

    private String leerTextoOpcional(String prompt) {
        System.out.print(prompt);
        String texto = scanner.nextLine().trim();
        return texto;
    }

    private void buscarReclamos() {
        ConsoleUtils.printHeader("Buscar Reclamos");
        System.out.println("Criterios de búsqueda:");
        System.out.println("1. Por título");
        System.out.println("2. Por estado");
        System.out.println("3. Por tipo");
        System.out.println("4. Por cliente ID");
        System.out.print("Seleccione criterio: ");

        int criterio = leerOpcionNumerica(1, 4);
        scanner.nextLine();

        List<Reclamo> resultados = new ArrayList<>();

        switch (criterio) {
            case 1:
                String titulo = leerTextoNoVacio("Ingrese parte del título: ");
                resultados = reclamoService.listarTodosReclamos().stream()
                    .filter(r -> r.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                    .collect(Collectors.toList());
                break;
            case 2:
                System.out.println("Estados: 1.ABIERTO 2.PENDIENTE 3.EN_PROCESO 4.RESUELTO 5.CERRADO");
                int estadoNum = leerOpcionNumerica(1, 5);
                EstadoReclamo estado = EstadoReclamo.values()[estadoNum - 1];
                resultados = reclamoService.obtenerReclamosPorEstado(estado);
                break;
            case 3:
                System.out.println("Tipos: 1.AGUA 2.LUZ 3.INTERNET 4.ALCANTARILLADO");
                int tipoNum = leerOpcionNumerica(1, 4);
                TipoReclamo tipo = TipoReclamo.values()[tipoNum - 1];
                resultados = reclamoService.listarTodosReclamos().stream()
                    .filter(r -> r.getTipo() == tipo)
                    .collect(Collectors.toList());
                break;
            case 4:
                int clienteId = leerIdNumerico("Ingrese ID del cliente: ");
                resultados = reclamoService.listarReclamosPorUsuario(clienteId);
                break;
        }

        if (resultados.isEmpty()) {
            ConsoleUtils.printWarning("No se encontraron reclamos con ese criterio.");
        } else {
            ConsoleUtils.printInfo("Resultados encontrados: " + resultados.size());
            ConsoleUtils.printTableSeparator(4);
            ConsoleUtils.printTableRow("ID", "Título", "Estado", "Cliente ID");
            ConsoleUtils.printTableSeparator(4);
            for (Reclamo r : resultados) {
                ConsoleUtils.printTableRow(
                    String.valueOf(r.getId()),
                    r.getTitulo().length() > 13 ? r.getTitulo().substring(0, 10) + "..." : r.getTitulo(),
                    r.getEstado().toString(),
                    String.valueOf(r.getClienteId())
                );
            }
            ConsoleUtils.printTableSeparator(4);
        }
    }

    private boolean confirmarAccion(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("y") || respuesta.equals("yes");
    }
}