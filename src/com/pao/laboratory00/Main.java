package com.pao.laboratory00;

import java.util.Scanner;

/**
 *  Rezolvați următoarele exerciții în fișierele
 *  1 MediaAritmetica.java și
 *  2 DiagonaleleMatricei.java din pachetul com.pao.loborator00.
 *
 * 1. Cititi de la tastatura un sir cu n elemente intregi.
 * Afisati sirul si media aritmetica a elementelor sirului.
 *
 * 2. Cititi de la tastatura o matrice de n ori n elemente REALE.
 * Afisati matricea in consola, apoi suma elementelor de pe diagonala principala
 *    si produsul elementelor de pe diagonala secundara.
 *
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        int[] array;
        n = scanner.nextInt(); // scannerul citeste primitive
        // declaram un array de lungimea n
        array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        // afisam elementele array-ului
        for (int num : array) {
            System.out.println(num);
        }
        // afisam din nou elementele, folosind indici si campul length
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

        int suma = 0;
        for (int i = 0; i < n; i++) {
            suma += array[i];
        }
        double media=suma;
        media=(double) media/n;
        System.out.println(media);


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
