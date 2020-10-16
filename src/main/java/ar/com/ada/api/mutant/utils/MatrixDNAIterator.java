package ar.com.ada.api.mutant.utils;

import ar.com.ada.api.mutant.entities.DNASample;

public class MatrixDNAIterator {

    public int matchesByRows(DNASample sample) {
        char[][] m = sample.toMatrix();
        int matches = 0;

        //iteramos por fila
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (j + 4 >= m.length) {
                    break;
                }
                // SI A = B, y B = C, C = D, A = D?
                // A = B, A = C, A = D y B = D?
                if (m[i][j] == m[i][j + 1] && m[i][j] == m[i][j + 2] && m[i][j] == m[i][j + 3]) {
                    matches++;
                }
            }
        }

        return matches;

    }

    public int matchesByColumns(DNASample sample) {
        char[][] m = sample.toMatrix();
        int matches = 0;

        //iteramos por columna
        for (int j = 0; j < m.length; j++) {
            for (int i = 0; i < m.length; i++) {

                if (i + 4 >= m.length) {
                    break;
                }
                // SI A = B, y B = C, C = D, A = D?
                // A = B, A = C, A = D y B = D?
                if (m[i][j] == m[i + 1][j] && m[i][j] == m[i + 2][j] && m[i][j] == m[i + 3][j]) {
                    matches++;
                }
            }
        }

        return matches;

    }

    public int matchesByDiagonals(DNASample sample) {

        int matches = 0;
        matches = matchesByDiagonals(sample.toMatrix());

        /*if (matches > 1)
            return matches;*/

        matches += matchesByDiagonals(sample.toMatrixInverted());

        return matches;

    }

    private int matchesByDiagonals(char[][] m) {

        int matches = 0;
        int matrixSize = m.length;

        //diagonales de izq a derecha
        for (int k = 0; k < m.length * 2; k++) {
            for (int j = 0; j <= k; j++) {
                int i = k - j;
                if (i < matrixSize && j < matrixSize) {
                    //Aca estoy en diagonal de izq a derecha.

                    if (i - 4 < 0 || j + 4 >= matrixSize) {
                        continue;
                    }
                    // SI A = B, y B = C, C = D, A = D?
                    // A = B, A = C, A = D y B = D?
                    if (m[i][j] == m[i - 1][j + 1] && m[i][j] == m[i - 2][j + 2] && m[i][j] == m[i - 3][j + 3]) {
                        matches++;
                    }
                }
            }

        }

        return matches;
    }

}
