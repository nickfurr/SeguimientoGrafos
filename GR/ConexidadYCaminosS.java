package co.edu.uniquindio.poo.SeguimientoGrafos.GR;


import org.ejml.simple.SimpleMatrix;

public class ConexidadYCaminosS {


    public void determinarConexidad(SimpleMatrix grafo, int tamañoCamino){
        SimpleMatrix resultado = caminosLenght(grafo, tamañoCamino, 0);
        determinarConexidadM(resultado);
    }

    public void determinarConexidadM(SimpleMatrix resultado){
        if (isConex(resultado)) {
            System.out.println("es conexo");
        } else {
            System.out.println("no es conexo");
        }

        resultado.print();
    }

    public boolean isConex(SimpleMatrix grafo){
        boolean conexidad = true;
        var sumaconex = 0;
        SimpleMatrix resultado = caminosLenght(grafo, 4, 0);
        for(int i = 0; i<resultado.getNumRows(); i++){
            for(int j = 0; j<resultado.getNumCols(); j++){
                sumaconex += grafo.get(i,j);
            }
            if (sumaconex == 0) {
                conexidad = false;
            }
            sumaconex = 0;
        }

        return conexidad;
    }

    // metodo recursivo que se encarga de sumar las multiplicaciones, pero tiene que empezar desde uno 
    public SimpleMatrix caminosLenght(SimpleMatrix matrizA,int tamañoCamino, int actual){
        if (tamañoCamino == actual) {
            return elevacion(matrizA, actual);
        }

        return elevacionR(matrizA, actual,0).plus(caminosLenght(matrizA, tamañoCamino, actual+1));
    }
    
    //elevacion de manera recursiva, modo masterrrrrrrr >BD
    public SimpleMatrix elevacionR(SimpleMatrix matriz,int numeroDeVeces, int actual){
        if(numeroDeVeces == actual){
            return matriz;
        }
        return matriz.mult(elevacionR(matriz, numeroDeVeces, actual+1));
    }
    // metodo de elevacion, usa reglas matematicas, (sin indice cero :D)
    public SimpleMatrix elevacion(SimpleMatrix matriz, int numeroDeVeces){
        SimpleMatrix devolver = new SimpleMatrix(matriz);
        for(int i = 0; i<numeroDeVeces; i++){
            devolver = devolver.mult(devolver);
        }
        return devolver;
    }
}
