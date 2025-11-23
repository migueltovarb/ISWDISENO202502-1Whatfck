package exam;
import java.util.Scanner;

public class Asistencia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int DIAS_SEMANA = 5;
        final int NUM_ESTUDIANTES = 4;

        String[][] asistencia = new String[NUM_ESTUDIANTES][DIAS_SEMANA];
        String[] nombres = new String[NUM_ESTUDIANTES];

        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            System.out.print("Ingrese el nombre del estudiante " + (i + 1) + ": ");
            nombres[i] = scanner.nextLine();
        }

        for (int d = 0; d < DIAS_SEMANA; d++) {
            System.out.println("\n=== Día " + (d + 1) + " ===");
            for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                String entrada;
                do {
                    System.out.print("Asistencia de " + nombres[e] + " (P/A): ");
                    entrada = scanner.nextLine().toUpperCase();
                } while (!entrada.equals("P") && !entrada.equals("A"));
                asistencia[e][d] = entrada;
            }
        }

        int opcion;
        do {
            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Ver asistencia individual");
            System.out.println("2. Ver resumen general");
            System.out.println("3. Volver a registrar");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    
                    for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                        System.out.print(nombres[e] + ": ");
                        for (int d = 0; d < DIAS_SEMANA; d++) {
                            System.out.print(asistencia[e][d] + " ");
                        }
                        System.out.println();
                    }
                    break;

                case 2:
                    int[] totalAsistencias = new int[NUM_ESTUDIANTES];
                    int[] ausenciasPorDia = new int[DIAS_SEMANA];

                    for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                        for (int d = 0; d < DIAS_SEMANA; d++) {
                            if (asistencia[e][d].equals("P")) {
                                totalAsistencias[e]++;
                            } else {
                                ausenciasPorDia[d]++;
                            }
                        }
                    }

                    System.out.println("\n--- Total de asistencias por estudiante ---");
                    for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                        System.out.println(nombres[e] + ": " + totalAsistencias[e]);
                    }

                    System.out.println("\n--- Estudiantes que asistieron todos los días ---");
                    for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                        if (totalAsistencias[e] == DIAS_SEMANA) {
                            System.out.println(nombres[e]);
                        }
                    }

                    System.out.println("\n--- Días con mayor número de ausencias ---");
                    int maxAusencias = 0;
                    for (int d = 0; d < DIAS_SEMANA; d++) {
                        if (ausenciasPorDia[d] > maxAusencias) {
                            maxAusencias = ausenciasPorDia[d];
                        }
                    }
                    for (int d = 0; d < DIAS_SEMANA; d++) {
                        if (ausenciasPorDia[d] == maxAusencias) {
                            System.out.println("Día " + (d + 1) + " con " + maxAusencias + " ausencias");
                        }
                    }
                    break;

                case 3:
                    for (int d = 0; d < DIAS_SEMANA; d++) {
                        for (int e = 0; e < NUM_ESTUDIANTES; e++) {
                            asistencia[e][d] = "";
                        }
                    }
                    System.out.println("Datos borrados. Vuelva a registrar.");
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 4);

        scanner.close();
    }
}