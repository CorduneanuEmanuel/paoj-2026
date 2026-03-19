package com.pao.laboratory00;
import java.util.Scanner;

/**
 * Exercitiul 1
 *
 * Cititi de la tastatura un sir cu n elemente intregi.
 *
 * 1. Afisati elementele sirului in doua modalitati.
 * 2. Afisati media aritmetica a elementelor sirului.
 *
 */

public class MediaAritmetica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sir = new int[n];
        for (int i = 0; i<n; i++) {
            sir[i] = scanner.nextInt();
        }
        for (int i = 0; i<n; i++) {
            System.out.print(sir[i] + " ");
        }
        System.out.println();
        for (var a : sir){
            System.out.print(a+" ");
        }
        int suma = 0;
        for (int i = 0; i < n; i++) {
            suma += sir[i];
        }
        double media=suma;
        media=(double) media/n;
        System.out.println(media);
    }
}
