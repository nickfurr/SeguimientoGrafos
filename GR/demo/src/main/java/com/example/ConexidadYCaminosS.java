package com.example;



import org.ejml.simple.SimpleMatrix;

public class ConexidadYCaminosS {

    // Convierte una matriz de adyacencia numérica a booleana
    public boolean[][] toBooleanMatrix(SimpleMatrix grafo) {
        int n = grafo.getNumRows();
        boolean[][] boolMat = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolMat[i][j] = grafo.get(i, j) != 0;
            }
        }
        return boolMat;
    }

    // Convierte una matriz booleana a SimpleMatrix (0/1)
    public SimpleMatrix toSimpleMatrix(boolean[][] boolMat) {
        int n = boolMat.length;
        SimpleMatrix mat = new SimpleMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat.set(i, j, boolMat[i][j] ? 1 : 0);
            }
        }
        return mat;
    }

    public void determinarConexidad(SimpleMatrix grafo, int tamañoCamino) {
        // Mostrar las iteraciones de las multiplicaciones
        mostrarIteracionesMultiplicacion(grafo);

        boolean[][] boolGrafo = toBooleanMatrix(grafo);
        boolean[][] resultado = caminosBooleanos(boolGrafo, tamañoCamino, 0);
        determinarConexidadM(resultado);
    }

    public void determinarConexidadM(boolean[][] resultado) {
        if (isConex(resultado) && isStronglyConnected(resultado)) {
            System.out.println("Es Fuertemente conexo");
        } else if (isConex(resultado)) {
            System.out.println("Es conexo");
        }else {
            System.out.println("No es conexo ni fuertemente conexo");
        }
        // Imprime la matriz booleana como 0/1
        SimpleMatrix mat = toSimpleMatrix(resultado);
        mat.print();
    }

    private boolean isStronglyConnected(boolean[][] resultado) {
        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado.length; j++) {
                if (i != j && !resultado[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isConex(boolean[][] grafo) {
        int n = grafo.length;
        for (int i = 0; i < n; i++) {
            boolean filaConexa = false;
            for (int j = 0; j < n; j++) {
                if (grafo[i][j]) {
                    filaConexa = true;
                    break;
                }
            }
            if (!filaConexa) {
                return false;
            }
        }
        return true;
    }

    // Calcula la matriz de caminos de longitud <= tamañoCamino usando lógica booleana
    public boolean[][] caminosBooleanos(boolean[][] matrizA, int tamañoCamino, int actual) {
        if (tamañoCamino == actual) {
            return elevacionBooleana(matrizA, actual);
        }
        boolean[][] suma = sumaBooleana(
            elevacionRBooleana(matrizA, actual, 0),
            caminosBooleanos(matrizA, tamañoCamino, actual + 1)
        );
        return suma;
    }

    // Suma lógica OR de dos matrices booleanas
    public boolean[][] sumaBooleana(boolean[][] a, boolean[][] b) {
        int n = a.length;
        boolean[][] res = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = a[i][j] || b[i][j];
            }
        }
        return res;
    }

    // Multiplicación lógica AND-OR de matrices booleanas
    public boolean[][] multBooleana(boolean[][] a, boolean[][] b) {
        int n = a.length;
        boolean[][] res = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boolean val = false;
                for (int k = 0; k < n; k++) {
                    val = val || (a[i][k] && b[k][j]);
                }
                res[i][j] = val;
            }
        }
        return res;
    }

    // Elevación recursiva booleana
    public boolean[][] elevacionRBooleana(boolean[][] matriz, int numeroDeVeces, int actual) {
        if (numeroDeVeces == actual) {
            return matriz;
        }
        return multBooleana(matriz, elevacionRBooleana(matriz, numeroDeVeces, actual + 1));
    }

    // Elevación booleana iterativa
    public boolean[][] elevacionBooleana(boolean[][] matriz, int numeroDeVeces) {
        int n = matriz.length;
        boolean[][] devolver = new boolean[n][n];
        // Copia matriz
        for (int i = 0; i < n; i++)
            System.arraycopy(matriz[i], 0, devolver[i], 0, n);
        for (int i = 0; i < numeroDeVeces; i++) {
            devolver = multBooleana(devolver, devolver);
        }
        return devolver;
    }

    // Muestra las iteraciones de las multiplicaciones de la matriz
    public void mostrarIteracionesMultiplicacion(SimpleMatrix grafo) {
        int n = grafo.getNumRows();
        boolean[][] matriz = toBooleanMatrix(grafo);

        System.out.println("Iteración 1 (A):");
        imprimirMatrizBooleana(matriz);

        boolean[][] potencia = matriz;
        for (int k = 2; k <= n; k++) {
            potencia = multBooleana(potencia, matriz);
            System.out.println("Iteración " + k + " (A^" + k + "):");
            imprimirMatrizBooleana(potencia);
        }
    }

    // Imprime una matriz booleana como 0/1
    public void imprimirMatrizBooleana(boolean[][] matriz) {
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
}
