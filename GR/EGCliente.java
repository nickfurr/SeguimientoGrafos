package co.edu.uniquindio.poo.SeguimientoGrafos.GR;


import org.ejml.simple.SimpleMatrix;

public class EGCliente {
    public void execute(){
        //definir si el grafo es conexo
        SimpleMatrix A = new SimpleMatrix(new double[][] {
            {0,1,0,0},
            {0,0,1,1},
            {0,0,0,0},
            {0,0,1,0}
        });

        ConexidadYCaminosS conexionSimple = new ConexidadYCaminosS();
        conexionSimple.determinarConexidad(A, 4);

    }

    
}
