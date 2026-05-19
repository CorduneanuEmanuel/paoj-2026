package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // Folosește LinkedList<Tranzactie> ca structură internă.
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON

//        System.out.println("TODO: implementează exercițiul 1");

        LinkedList<Tranzactie> coada = new LinkedList<>();
        boolean lifo = false;
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String comanda = scanner.next();
            switch (comanda){
                case "ENQUEUE" : {
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;

                }
                case "DEQUEUE" : {
                    try {
                        System.out.println("Procesat: " + coada.removeFirst());
                    }
                    catch(NoSuchElementException e){
                        System.out.println("Coada goala.");
                    }
                    break;
                }
                case "PUSH" : {
                    lifo = true;
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "POP" : {
                    lifo = true;
                    try {
                        System.out.println("Extras: " + coada.removeLast());
                    }
                    catch(NoSuchElementException e){
                        System.out.println("Coada goala.");
                    }
                    break;
                }
                case "REMOVE_DEBIT" : {
                    int cnt = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    while(it.hasNext()){
                        Tranzactie i = it.next();
                        if(i.getTip() == TipTranzactie.DEBIT){
                            it.remove();
                            cnt++;
                        }
                    }
                    System.out.printf("Eliminat %d tranzactii DEBIT.\n", cnt);
                    break;
                }
                case "REMOVE_BELOW" : {
                    int cnt = 0;
                    double threshold = scanner.nextDouble();
                    Iterator<Tranzactie> it = coada.iterator();
                    while(it.hasNext()){
                        Tranzactie i = it.next();
                        if(i.getSuma() < threshold){
                            it.remove();
                            cnt++;
                        }
                    }
                    System.out.println(String.format("Eliminat %d tranzactii sub %.2f RON.", cnt, threshold));
                    break;
                }
                case "PRINT" : {
//                    int cnt = 0;
                    Iterator<Tranzactie> it = coada.iterator();
                    if(!lifo) {
                        while (it.hasNext()) {
                            Tranzactie i = it.next();
                            System.out.println(i);
                        }
                    }
                    else{
                        invers(it);
                    }
                    break;
//                    System.out.printf("Eliminat %d tranzactii sub threshold RON.", cnt);
                }
                case "SIZE" : {
                    System.out.println("Dimensiune coada: " + coada.size());
                }
                break;
            }
        }

    }
    public static void invers(Iterator<Tranzactie> it){
        if(it.hasNext()){
            Tranzactie t = it.next();
            invers(it);
            System.out.println(t);
        }


    }
}
