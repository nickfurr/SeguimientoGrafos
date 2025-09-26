package co.edu.uniquindio.poo.SeguimientoGrafos.GR;


import org.ejml.simple.SimpleMatrix;

public class EG2Cliente {

    // corregir
    public void execute(){
        // ciclo de hamilton de menor costo:
        // matriz que tiro chatsito
        SimpleMatrix valores = new SimpleMatrix(new double[][] {
            {8, 5, 4, 9},  
            {3, 7, 2, 3},
            {1, 7, 9, 6},  
            {7, 6, 2, 6}
        });

        valores.print();


        System.out.println("inicioMatriz");
       caminoMenorCostoP(0, valores).print();
       System.out.println("finMatriz");
    }

    public SimpleMatrix caminoMenorCostoP(int Vinicio,SimpleMatrix grafo){
        SimpleMatrix recorrido = new SimpleMatrix(grafo.getNumRows(), grafo.getNumCols());
        return caminoMenorCosto(Vinicio, Vinicio, grafo, recorrido, 0);
    }

    public SimpleMatrix caminoMenorCosto(int Vinicio, int Vactual,SimpleMatrix grafo, SimpleMatrix recorrido, int vuelta){
        if(Vinicio == Vactual && vuelta != 0 && vuelta == grafo.getNumCols()-1){
            return recorrido;
        }

        SimpleMatrix fila = grafo.extractVector(true, Vactual);
        fila.print();
        int proximoVerticeMenor = proximoMenorVerticeProximo(fila,recorrido, Double.MAX_VALUE, 0,0,Vactual);
        System.out.println(proximoVerticeMenor);
        recorrido.set(Vactual,proximoVerticeMenor , 1);
        //System.out.println(vuelta);
        return caminoMenorCosto(Vinicio, proximoVerticeMenor, grafo, recorrido, vuelta+1);

    }

    public int proximoMenorVerticeProximo(SimpleMatrix matriz, SimpleMatrix recorrido,double valorContenido, int posibleHastaVertice, int indiceActual, int fila ){
        if (indiceActual == matriz.getNumCols()) {
            return posibleHastaVertice;
        }
        // esto se encarga de no repetir caminos, ya que hagarra el recorrido y la fila y reviza que la matriz en fila y columna no tenga un uno para avanzar
        if (matriz.get(0,indiceActual) < valorContenido && recorrido.get(fila, indiceActual) != 1 ) {
            valorContenido = matriz.get(0,indiceActual);
            return proximoMenorVerticeProximo (matriz, recorrido , valorContenido, indiceActual, indiceActual+1, fila);-
    
        }

        return proximoMenorVerticeProximo(matriz,recorrido, valorContenido, posibleHastaVertice, indiceActual+1, fila);

    }
    
}
