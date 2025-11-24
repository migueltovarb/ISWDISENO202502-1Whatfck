package com.sistema.controllers;

import com.sistema.model.Reclamo;
import com.sistema.model.Respuesta;
import com.sistema.model.Notificacion;
import com.sistema.model.Usuario;
import com.sistema.services.ReclamoService;
import com.sistema.services.NotificacionService;
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
        boolean continuar = true;
        while (continuar) {
            mostrarBreadcrumbs();
            ConsoleUtils.printHeader("Menú Principal");
            ConsoleUtils.printInfo("Usuario: " + usuarioActual.getNombre() + " (" + usuarioActual.getRol() + ")");

            if (usuarioActual.getRol() == Rol.CLIENTE) {
                mostrarMenuCliente();
            } else {
                mostrarMenuSoporte();
            }

            System.out.print("Seleccione una opción: ");
            int maxOpcion = (usuarioActual.getRol() == Rol.CLIENTE) ? 5 : 4;
            int opcion = leerOpcionNumerica(0, maxOpcion);
            scanner.nextLine();

            if (usuarioActual.getRol() == Rol.CLIENTE) {
                continuar = procesarOpcionCliente(opcion);
            } else {
                continuar = procesarOpcionSoporte(opcion);
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
        System.out.println("5. Ver notificaciones");
        System.out.println("0. Cerrar sesión");
    }

    private void mostrarMenuSoporte() {
        System.out.println("1. Ver todos los reclamos");
        System.out.println("2. Ver reclamos/Responder pendientes");
        System.out.println("3. Buscar reclamos");
        System.out.println("4. Ver notificaciones");
        System.out.println("0. Cerrar sesión");
    }

    private boolean procesarOpcionCliente(int opcion) {
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
            case 5:
                breadcrumbs.add("Notificaciones");
                verNotificaciones();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 0:
                return false; // Cerrar sesión
            default:
                ConsoleUtils.printError("Opción inválida.");
        }
        return true; // Continuar en el menú
    }

    private boolean procesarOpcionSoporte(int opcion) {
        switch (opcion) {
            case 1:
                breadcrumbs.add("Todos los Reclamos");
                verTodosReclamos();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 2:
                breadcrumbs.add("Reclamos/Responder Pendientes");
                gestionarReclamosPendientes();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 3:
                breadcrumbs.add("Buscar Reclamos");
                buscarReclamos();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 4:
                breadcrumbs.add("Notificaciones");
                verNotificaciones();
                breadcrumbs.remove(breadcrumbs.size() - 1);
                break;
            case 0:
                return false; // Cerrar sesión
            default:
                ConsoleUtils.printError("Opción inválida.");
        }
        return true; // Continuar en el menú
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
        if (titulo == null) return;

        String descripcion = leerTextoNoVacio("Descripción: ");
        if (descripcion == null) return;

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
        if (ubicacion == null) return;

        Reclamo reclamo = reclamoService.crearReclamo(titulo, descripcion, tipoReclamo,
                                                     ubicacion, usuarioActual.getId());
        ConsoleUtils.printSuccess("Reclamo creado exitosamente con ID: " + reclamo.getId());
    }

    private void verDetalleReclamo() {
        int id = leerIdNumerico("Ingrese ID del reclamo: ");
        if (id == -1) return;

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

        // Si el cliente tiene respuestas del soporte, permitir acciones
        if (!respuestas.isEmpty() && (reclamo.getEstado() == EstadoReclamo.RESUELTO || reclamo.getEstado() == EstadoReclamo.PENDIENTE)) {
            System.out.println();
            System.out.println("Acciones disponibles:");
            System.out.println("1. Responder al reclamo");
            System.out.println("2. Cerrar satisfactoriamente");
            System.out.println("0. Regresar al menú");
            System.out.print("Seleccione una opción: ");

            int accion = leerOpcionNumerica(0, 2);
            scanner.nextLine();

            switch (accion) {
                case 1:
                    responderReclamoCliente(id);
                    break;
                case 2:
                    cerrarSatisfactoriamente(id);
                    break;
                case 0:
                    return;
            }
        }
    }

    private void responderReclamoCliente(int reclamoId) {
        String mensaje = leerTextoNoVacio("Tu respuesta: ");
        if (mensaje == null) return;

        try {
            Respuesta respuesta = reclamoService.agregarRespuesta(reclamoId, mensaje, usuarioActual.getId());
            ConsoleUtils.printSuccess("Respuesta enviada exitosamente.");
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Error: " + e.getMessage());
        }
    }

    private void cerrarSatisfactoriamente(int reclamoId) {
        if (confirmarAccion("¿Estás satisfecho con la solución? El reclamo se cerrará definitivamente.")) {
            if (reclamoService.cerrarSatisfactoriamente(reclamoId, usuarioActual.getId())) {
                ConsoleUtils.printSuccess("Reclamo cerrado satisfactoriamente.");
                // Notificación será creada automáticamente por el servicio
            } else {
                ConsoleUtils.printError("Error al cerrar el reclamo.");
            }
        } else {
            ConsoleUtils.printInfo("Operación cancelada.");
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
        if (reclamoId == -1) return;

        String mensaje = leerTextoNoVacio("Mensaje de respuesta: ");
        if (mensaje == null) return;

        try {
            Respuesta respuesta = reclamoService.agregarRespuesta(reclamoId, mensaje, usuarioActual.getId());
            ConsoleUtils.printSuccess("Respuesta enviada exitosamente.");
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoReclamo() {
        int reclamoId = leerIdNumerico("ID del reclamo: ");
        if (reclamoId == -1) return;

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
                ConsoleUtils.printInfo("Operación cancelada.");
                return null; // Cancelar operación
            } else {
                return texto;
            }
        }
    }

    private int leerIdNumerico(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                ConsoleUtils.printInfo("Operación cancelada.");
                return -1; // Cancelar operación
            }
            try {
                int id = Integer.parseInt(input);
                if (id > 0) {
                    return id;
                } else {
                    ConsoleUtils.printError("ID debe ser un número positivo.");
                }
            } catch (Exception e) {
                ConsoleUtils.printError("Entrada inválida. Ingrese un número válido o presione Enter para cancelar.");
            }
        }
    }

    private void editarReclamo() {
        int id = leerIdNumerico("Ingrese ID del reclamo a editar: ");
        if (id == -1) return;

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
                if (titulo == null) return;
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
                if (clienteId == -1) return;
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

    private void gestionarReclamosPendientes() {
        List<Reclamo> reclamosPendientes = reclamoService.obtenerReclamosPorEstado(EstadoReclamo.PENDIENTE);
        ConsoleUtils.printHeader("Reclamos Pendientes");

        if (reclamosPendientes.isEmpty()) {
            ConsoleUtils.printWarning("No hay reclamos pendientes.");
            return;
        }

        // Mostrar lista de reclamos pendientes
        ConsoleUtils.printTableSeparator(4);
        ConsoleUtils.printTableRow("ID", "Título", "Cliente ID", "Fecha");
        ConsoleUtils.printTableSeparator(4);
        for (Reclamo r : reclamosPendientes) {
            ConsoleUtils.printTableRow(
                String.valueOf(r.getId()),
                r.getTitulo().length() > 13 ? r.getTitulo().substring(0, 10) + "..." : r.getTitulo(),
                String.valueOf(r.getClienteId()),
                r.getFechaCreacion().toString()
            );
        }
        ConsoleUtils.printTableSeparator(4);

        // Preguntar si desea responder a algún reclamo
        System.out.println();
        System.out.print("¿Deseas responder a algún reclamo? ID (presiona ENTER para regresar): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return; // Regresar al menú principal
        }

        try {
            int reclamoId = Integer.parseInt(input);
            Reclamo reclamoSeleccionado = null;

            // Verificar que el ID esté en la lista de pendientes
            for (Reclamo r : reclamosPendientes) {
                if (r.getId() == reclamoId) {
                    reclamoSeleccionado = r;
                    break;
                }
            }

            if (reclamoSeleccionado == null) {
                ConsoleUtils.printError("ID no encontrado en la lista de pendientes.");
                return;
            }

            // Mostrar detalle del reclamo seleccionado
            ConsoleUtils.printHeader("Detalle del Reclamo Seleccionado");
            System.out.println("ID: " + reclamoSeleccionado.getId());
            System.out.println("Título: " + reclamoSeleccionado.getTitulo());
            System.out.println("Descripción: " + reclamoSeleccionado.getDescripcion());
            System.out.println("Tipo: " + reclamoSeleccionado.getTipo());
            System.out.println("Ubicación: " + reclamoSeleccionado.getUbicacion());
            System.out.println("Estado: " + reclamoSeleccionado.getEstado());
            System.out.println("Fecha: " + reclamoSeleccionado.getFechaCreacion());

            // Mostrar respuestas anteriores si existen
            List<Respuesta> respuestas = reclamoService.obtenerRespuestasPorReclamo(reclamoId);
            if (!respuestas.isEmpty()) {
                ConsoleUtils.printSubHeader("Respuestas Anteriores");
                for (Respuesta resp : respuestas) {
                    System.out.println("[" + resp.getFecha() + "] " + resp.getMensaje());
                }
            }

            // Menú de acciones para el reclamo seleccionado
            System.out.println();
            System.out.println("Acciones disponibles:");
            System.out.println("1. Responder al reclamo");
            System.out.println("2. Cambiar estado del reclamo");
            System.out.println("0. Regresar");
            System.out.print("Seleccione una opción: ");

            int accion = leerOpcionNumerica(0, 2);
            scanner.nextLine();

            switch (accion) {
                case 1:
                    responderReclamoEspecifico(reclamoId);
                    break;
                case 2:
                    cambiarEstadoReclamoEspecifico(reclamoId);
                    break;
                case 0:
                    return;
            }

        } catch (NumberFormatException e) {
            ConsoleUtils.printError("ID inválido. Debe ser un número.");
        }
    }

    private void responderReclamoEspecifico(int reclamoId) {
        String mensaje = leerTextoNoVacio("Mensaje de respuesta: ");
        if (mensaje == null) return;

        try {
            Respuesta respuesta = reclamoService.agregarRespuesta(reclamoId, mensaje, usuarioActual.getId());
            ConsoleUtils.printSuccess("Respuesta enviada exitosamente.");
        } catch (IllegalArgumentException e) {
            ConsoleUtils.printError("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoReclamoEspecifico(int reclamoId) {
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

    private void verNotificaciones() {
        List<Notificacion> notificaciones = NotificacionService.obtenerTodasNotificaciones();

        // Filtrar notificaciones relevantes para el usuario actual
        List<Notificacion> notificacionesRelevantes = new ArrayList<>();
        for (Notificacion n : notificaciones) {
            Reclamo reclamo = reclamoService.obtenerReclamoPorId(n.getReclamoId());
            if (reclamo != null) {
                if (usuarioActual.getRol() == Rol.CLIENTE) {
                    // Cliente solo ve notificaciones de sus propios reclamos
                    if (reclamo.getClienteId() == usuarioActual.getId()) {
                        notificacionesRelevantes.add(n);
                    }
                } else {
                    // Soporte ve todas las notificaciones
                    notificacionesRelevantes.add(n);
                }
            }
        }

        ConsoleUtils.printHeader("Notificaciones");
        if (notificacionesRelevantes.isEmpty()) {
            ConsoleUtils.printWarning("No tienes notificaciones pendientes.");
        } else {
            ConsoleUtils.printInfo("Tienes " + notificacionesRelevantes.size() + " notificación(es):");
            System.out.println();
            for (int i = 0; i < notificacionesRelevantes.size(); i++) {
                Notificacion n = notificacionesRelevantes.get(i);
                Reclamo reclamo = reclamoService.obtenerReclamoPorId(n.getReclamoId());
                String tituloReclamo = reclamo != null ? reclamo.getTitulo() : "Reclamo desconocido";

                System.out.println((i + 1) + ". 📧 Tienes una respuesta en '" + tituloReclamo + "' ID: " + n.getReclamoId());
                System.out.println("   " + n.getMensaje());
                System.out.println();
            }
        }
    }

    private boolean confirmarAccion(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        return respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("y") || respuesta.equals("yes");
    }
}