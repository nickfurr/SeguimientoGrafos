package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GrafoView extends JFrame {
    private JTextArea matrizInput;
    private JButton analizarBtn;
    private JTextArea resultadoArea;

    public GrafoView() {
        setTitle("Conexidad de Grafos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        matrizInput = new JTextArea(5, 30);
        matrizInput.setBorder(BorderFactory.createTitledBorder("Matriz de adyacencia (separada por espacios)"));
        add(new JScrollPane(matrizInput), BorderLayout.NORTH);

        analizarBtn = new JButton("Analizar Conexidad");
        add(analizarBtn, BorderLayout.CENTER);

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        resultadoArea.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(new JScrollPane(resultadoArea), BorderLayout.SOUTH);
    }

    public String getMatrizInput() {
        return matrizInput.getText();
    }

    public void setResultado(String texto) {
        resultadoArea.setText(texto);
    }

    public void addAnalizarListener(ActionListener listener) {
        analizarBtn.addActionListener(listener);
    }
}
