// Archivo: MatrixInput.java (clase auxiliar para leer input de matriz)
package com.example;

import org.ejml.simple.SimpleMatrix;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MatrixInput {

    /**
     * Lee el tamaño n y la matriz n x n desde la terminal.
     * @return SimpleMatrix con los valores ingresados.
     */
    public static SimpleMatrix leerMatrizDesdeTerminal() {
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        boolean nValido = false;

        // Leer y validar tamaño n
        while (!nValido) {
            System.out.print("Ingrese el tamaño de la matriz (n para n x n, n > 0): ");
            if (scanner.hasNextInt()) {
                n = scanner.nextInt();
                if (n > 0) {
                    nValido = true;
                } else {
                    System.out.println("Error: n debe ser mayor que 0. Intente de nuevo.");
                }
            } else {
                System.out.println("Error: Ingrese un número entero válido. Intente de nuevo.");
                scanner.next();  // Limpiar input inválido
            }
        }
        scanner.nextLine();  // Limpiar buffer

        // Crear matriz vacía
        SimpleMatrix matriz = new SimpleMatrix(n, n);

        // Leer cada fila
        for (int i = 0; i < n; i++) {
            boolean filaValida = false;
            while (!filaValida) {
                System.out.print("Ingrese la fila " + i + " (" + n + " números separados por espacios, e.g., 0 1 0): ");
                String linea = scanner.nextLine().trim();

                // Parsear la línea
                String[] partes = linea.split("\\s+");  // Split por espacios
                if (partes.length == n) {
                    try {
                        for (int j = 0; j < n; j++) {
                            double valor = Double.parseDouble(partes[j]);
                            matriz.set(i, j, valor);
                        }
                        filaValida = true;
                        System.out.println("Fila " + i + " ingresada correctamente.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Todos los valores deben ser números (e.g., 0 o 1). Intente de nuevo.");
                    }
                } else {
                    System.out.println("Error: Debe ingresar exactamente " + n + " números. Intente de nuevo.");
                }
            }
        }

        // Mostrar matriz ingresada para confirmación
        System.out.println("\nMatriz ingresada:");
        matriz.print();
        System.out.println();

        scanner.close();
        return matriz;
    }
}

