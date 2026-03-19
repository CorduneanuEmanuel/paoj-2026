package com.pao.laboratory00;
import java.util.Scanner;

/**
 * Exercitiul 2
 *
 * Cititi de la tastatura o matrice de n ori n elemente REALE.
 *
 * 1. Afisati matricea in consola.
 * 2. Afisati suma elementelor de pe diagonala principala
 *    si produsul elementelor de pe diagonala secundara.
 *
 */

public class DiagonaleleMatricei {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti n: ");
        int n = scanner.nextInt();

        int[][] matrice = new int[n][n];

        for (int i = 0;i<n;i++) {
            for (int j = 0; j<n; j++) {
                matrice[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                System.out.print(matrice[i][j]+ " ");
            }
            System.out.println();
        }
        int principala = 0;
        for (int i = 0; i<n; i++) {
            principala += matrice[i][i];
        }
        int secundara = 1;
        for (int i = 0; i<n; i++) {
            secundara *= matrice[i][n - i - 1];
        }

        System.out.println("Suma diagonalei principale: " + principala);
        System.out.println("Produsul diagonalei secundare: " + secundara);

    }
}
