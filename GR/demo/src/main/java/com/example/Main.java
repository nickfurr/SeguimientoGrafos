package com.example;


public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GrafoView view = new GrafoView();
            ConexidadYCaminosS modelo = new ConexidadYCaminosS();
            new GrafoController(view, modelo);
            view.setVisible(true);
        });
    }
}
