package com.sistema;

public class ConsoleUtils {
    // Códigos ANSI para colores
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    // Métodos para imprimir con colores
    public static void printSuccess(String message) {
        System.out.println(GREEN + "✅ " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "❌ " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠️ " + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(BLUE + "ℹ️ " + message + RESET);
    }

    public static void printHeader(String message) {
        System.out.println(CYAN + BOLD + "\n=== " + message.toUpperCase() + " ===" + RESET);
    }

    public static void printSubHeader(String message) {
        System.out.println(BOLD + "--- " + message + " ---" + RESET);
    }

    // Método para imprimir tabla simple
    public static void printTableRow(String... columns) {
        StringBuilder row = new StringBuilder("|");
        for (String col : columns) {
            row.append(" ").append(String.format("%-15s", col)).append(" |");
        }
        System.out.println(row.toString());
    }

    public static void printTableSeparator(int numColumns) {
        StringBuilder sep = new StringBuilder("+");
        for (int i = 0; i < numColumns; i++) {
            sep.append("-".repeat(17)).append("+");
        }
        System.out.println(sep.toString());
    }
}