package com.example;

import org.ejml.simple.SimpleMatrix;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Análisis de Conexidad y Caminos en Grafos ===");
        System.out.println("Ingrese la matriz de adyacencia (0/1 para aristas).");
        // Controller: Leer matriz desde terminal
        SimpleMatrix grafo = MatrixInput.leerMatrizDesdeTerminal();
        // Model: Crear instancia y analizar
        ConexidadYCaminosS analizador = new ConexidadYCaminosS();
        analizador.determinarConexidad(grafo);

    }
}
