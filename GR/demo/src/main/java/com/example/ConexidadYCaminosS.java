package com.example;

import org.ejml.simple.SimpleMatrix;

public class ConexidadYCaminosS {

    // Convierte SimpleMatrix a booleana (adyacencia !=0 es true)
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

    // Convierte booleana a SimpleMatrix (true=1, false=0) para impresión
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

    // Método principal: determina si es dirigido/no dirigido, muestra multiplicaciones y conexidad
    public void determinarConexidad(SimpleMatrix grafo) {
        boolean[][] matrizOriginal = toBooleanMatrix(grafo);
        int n = matrizOriginal.length;

        // 1. Mostrar si es dirigido o no
        boolean esDirigidoGrafo = esDirigido(matrizOriginal);
        System.out.println("¿Es grafo dirigido? " + (esDirigidoGrafo ? "SÍ" : "NO"));
        System.out.println();

        // 2. Mostrar iteraciones de multiplicaciones del grafo original
        mostrarIteracionBk(matrizOriginal, "original");

        // 3. Calcular cierre transitivo del original
        boolean[][] cierreOriginal = calculoBk(matrizOriginal, n);

        System.out.println("\nBk del grafo original:");
        imprimirMatrizBooleana(cierreOriginal);

        boolean necesitaTransformacion = false;
        if (!esDirigidoGrafo) {
            // No dirigido: verificar conexo
            boolean esConexo = isConexo(cierreOriginal);
            System.out.println(esConexo ? "El grafo es CONEXO." : "El grafo NO es conexo.");
        } else {
            // Dirigido: verificar fuertemente conexo
            boolean esFuertementeConexo = isFuertementeConexo(cierreOriginal);
            if (esFuertementeConexo) {
                System.out.println("El grafo es FUERTEMENTE CONEXO.");
            } else {
                System.out.println("El grafo NO es fuertemente conexo.");
                // Transformar a undirected (grafo simple) y verificar conexidad
                necesitaTransformacion = true;
                boolean[][] matrizUndirected = orConTranspuesta(matrizOriginal);
                System.out.println("\n--- Transformación a grafo simple (no dirigido) ---");
                System.out.println("Matriz de grafo no dirigido:");
                imprimirMatrizBooleana(matrizUndirected);

                // Mostrar multiplicaciones de la versión undirected
                mostrarIteracionBk(matrizUndirected, "no dirigido");

                // Calcular cierre de la versión undirected
                boolean[][] cierreUndirected = calculoBk(matrizUndirected, n);
                System.out.println("\nBk:");
                imprimirMatrizBooleana(cierreUndirected);

                // Verificar si es conexo como undirected
                boolean esConexoUndirected = isConexo(cierreUndirected);
                System.out.println(esConexoUndirected ? "Como grafo simple, es CONEXO." : "Como grafo simple, NO es conexo.");
            }
        }

        // Imprimir matriz final como SimpleMatrix (la relevante)
        System.out.println("\nMatriz final (0/1):");
        boolean[][] cierreFinal = necesitaTransformacion ? cierreOriginal : cierreOriginal;  // Siempre el original por defecto, o ajusta si quieres el undirected
        toSimpleMatrix(cierreFinal).print();
    }

    // Verifica si es dirigido (no simétrico)
    public boolean esDirigido(boolean[][] grafo) {
        int n = grafo.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grafo[i][j] != grafo[j][i]) {
                    return true;
                }
            }
        }
        return false;
    }

    // OR lógico entre matriz y su transpuesta (convierte digrafo a undirected)
    public boolean[][] orConTranspuesta(boolean[][] A) {
        if (A == null || A.length == 0) {
            return null;
        }
        int n = A.length;
        // Calcular transpuesta
        boolean[][] transpuesta = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transpuesta[i][j] = A[j][i];
            }
        }
        // OR elemento a elemento
        boolean[][] undirected = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                undirected[i][j] = A[i][j] || transpuesta[i][j];
            }
        }
        return undirected;
    }

    // Muestra iteraciones de potencias booleanas (multiplicaciones AND-OR)
    public void mostrarIteracionBk(boolean[][] matriz, String tipo) {
        int n = matriz.length;
        System.out.println("Iteraciones de multiplicaciones (" + tipo + "):");

        boolean[][] potencia = new boolean[n][n];
        // Copia inicial: A^1 = A
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, potencia[i], 0, n);
        }
        System.out.println("Iteración 1 (A^1):");
        imprimirMatrizBooleana(potencia);

        for (int k = 2; k <= n; k++) {
            potencia = multBooleana(potencia, matriz);
            System.out.println("Iteración " + k + " (A^" + k + "):");
            imprimirMatrizBooleana(potencia);
        }
        System.out.println();
    }

    // Calcula cierre transitivo: OR acumulativo de A^1 | A^2 | ... | A^n
    public boolean[][] calculoBk(boolean[][] matriz, int n) {
        boolean[][] cierre = new boolean[n][n];
        boolean[][] potencia = new boolean[n][n];
        // Inicializar con A^1
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, potencia[i], 0, n);
            System.arraycopy(matriz[i], 0, cierre[i], 0, n);
        }
        for (int k = 2; k <= n; k++) {
            potencia = multBooleana(potencia, matriz);
            cierre = sumaBooleana(cierre, potencia);  // OR acumulativo
        }
        return cierre;
    }

    // Multiplicación booleana (AND-OR para caminos de longitud exacta)
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

    // Suma booleana (OR para unión de caminos)
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

    // Verifica si es conexo: todos los pares i≠j tienen camino (en cierre transitivo)
    public boolean isConexo(boolean[][] cierre) {
        int n = cierre.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && !cierre[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Verifica si es fuertemente conexo: hay caminos i->j y j->i para todos i≠j (todos off-diagonal true en cierre)
    public boolean isFuertementeConexo(boolean[][] cierre) {
        return isConexo(cierre);  // En cierre transitivo dirigido, equivale a todos los pares conectados en ambas direcciones
    }

    // Imprime matriz booleana como 0/1
    public void imprimirMatrizBooleana(boolean[][] matriz) {
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print((matriz[i][j] ? "1" : "0") + " ");
            }
            System.out.println();
        }
    }
}
