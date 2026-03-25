package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        AngajatService service=AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune=scanner.nextInt();
            switch (optiune){
                case 1:
                    System.out.println("Introduce-ti numele angajatului: ");
                    String nume=scanner.next();
                    System.out.println("Introduce-ti salariul angajatului: ");
                    double salariu=scanner.nextDouble();
                    System.out.println("Introduce-ti numele departamenului in care se afla: ");
                    String numedept=scanner.next();
                    System.out.println("Introduce-ti locatia departamenului: ");
                    String locatie=scanner.next();
                    service.addAngajat(new Angajat(new Departament(numedept, locatie), nume, salariu));
                    break;
                case 2:
                    service.listBySalary();
                    break;
                case 3:
                    System.out.println("Introduce-ti numele departamenului in care se afla: ");
                    String numedept2=scanner.next();

                    service.findByDepartament(numedept2);
                    break;
                case 0:
                    return;
            }

        }

    }
}
