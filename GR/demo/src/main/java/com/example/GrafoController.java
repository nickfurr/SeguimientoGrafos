package com.example;

import org.ejml.simple.SimpleMatrix;
import java.awt.event.*;

public class GrafoController {
    private GrafoView view;
    private ConexidadYCaminosS modelo;

    public GrafoController(GrafoView view, ConexidadYCaminosS modelo) {
        this.view = view;
        this.modelo = modelo;

        this.view.addAnalizarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analizarGrafo();
            }
        });
    }

    private void analizarGrafo() {
        try {
            String input = view.getMatrizInput();
            double[][] matriz = parseMatriz(input);
            SimpleMatrix A = new SimpleMatrix(matriz);
            int n = matriz.length;

            // Redirigir la salida a un String
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream old = System.out;
            System.setOut(ps);

            modelo.determinarConexidad(A, n);

            System.out.flush();
            System.setOut(old);

            view.setResultado(baos.toString());
        } catch (Exception ex) {
            view.setResultado("Error en la matriz: " + ex.getMessage());
        }
    }

    private double[][] parseMatriz(String input) {
        String[] rows = input.trim().split("\\n");
        int n = rows.length;
        double[][] matriz = new double[n][n];
        for (int i = 0; i < n; i++) {
            String[] vals = rows[i].trim().split("\\s+");
            if (vals.length != n) throw new IllegalArgumentException("La matriz debe ser cuadrada");
            for (int j = 0; j < n; j++) {
                matriz[i][j] = Double.parseDouble(vals[j]);
            }
        }
        return matriz;
    }
}
