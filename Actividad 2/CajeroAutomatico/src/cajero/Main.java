package cajero;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final int SALDO_INICIAL = 1000;
        int saldo = SALDO_INICIAL;
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción (1-4): ");

            if (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Debes ingresar un número del 1 al 4.");
                sc.next();
                continue;
            }

            opcion = sc.nextInt();

            if (opcion < 1 || opcion > 4) {
                System.out.println("Opción inválida. Intenta de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\nTu saldo actual es: $" + saldo);
                    esperarRegresar(sc);
                    break;

                case 2: // Depositar
                    saldo = procesarTransaccion(sc, saldo, true);
                    break;

                case 3: // Retirar
                    saldo = procesarTransaccion(sc, saldo, false);
                    break;

                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
            }
        }

        sc.close();
    }

    // Función para esperar la tecla de regresar
    public static void esperarRegresar(Scanner sc) {
        String volver = "";
        while (!volver.equalsIgnoreCase("R")) {
            System.out.print("Escribe 'R' para regresar al menú: ");
            volver = sc.next();
        }
    }

    // Función para modificar saldo
    public static int modificarSaldo(int saldo, int cantidad) {
        return saldo + cantidad;
    }

    // Función para procesar depósitos o retiros
    public static int procesarTransaccion(Scanner sc, int saldo, boolean esDeposito) {
        String accion = esDeposito ? "depositar" : "retirar";

        while (true) {
            System.out.print("\nIngresa la cantidad a " + accion + " (o 'R' para regresar): ");
            if (sc.hasNextInt()) {
                int cantidad = sc.nextInt();
                if (cantidad > 0) {
                    if (!esDeposito && cantidad > saldo) {
                        System.out.println("Fondos insuficientes. Tu saldo es: $" + saldo);
                        continue;
                    }
                    int cambio = esDeposito ? cantidad : -cantidad;
                    saldo = modificarSaldo(saldo, cambio);
                    System.out.println("Transacción aprobada.");
                    System.out.println("Tu nuevo saldo es: $" + saldo);
                    break;
                } else {
                    System.out.println("La cantidad debe ser mayor a 0.");
                }
            } else {
                String entrada = sc.next();
                if (entrada.equalsIgnoreCase("R")) {
                    break;
                } else {
                    System.out.println("Entrada inválida. Ingresa un número entero positivo o 'R' para regresar.");
                }
            }
        }

        return saldo;
    }
}
