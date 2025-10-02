package com.example;


import org.ejml.simple.SimpleMatrix;

public class EGCliente {
    public void execute(){
        //definir si el grafo es conexo
        SimpleMatrix grafo = new SimpleMatrix(new double[][] {
            {0,1,0},
            {0,0,1},
            {0,0,0}
        });

        new ConexidadYCaminosS().determinarConexidad(grafo);


    }

    
}
