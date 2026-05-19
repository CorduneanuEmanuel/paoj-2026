package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        Scanner scanner = new Scanner(System.in);

        List<Tranzactii> tranzactii = new ArrayList<>();

//        System.out.print("N(nr tranzatctii): ");
        int n = scanner.nextInt();


        for(int i = 0; i < n; i++) {

            int id = scanner.nextInt();

            double suma = scanner.nextDouble();

            String data = scanner.next();

            String contSursa = scanner.next();

            String contDestinatie = scanner.next();

            String tip = scanner.next();

            Tranzactii tranzactie = new Tranzactii(id, suma, data, contSursa, contDestinatie, tip, "procesat");
            tranzactii.add(tranzactie);



        }

        String FILE = "output/lab09_ex1.ser";



        File file = new File(FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }


        try(ObjectOutputStream e = new ObjectOutputStream(new FileOutputStream(FILE))){
            e.writeObject(tranzactii);
        }catch (IOException l){
            l.printStackTrace();
        }

        ArrayList<Tranzactii> tranzactii2 = new ArrayList<>();
        try(ObjectInputStream e = new ObjectInputStream(new FileInputStream(FILE))){
            tranzactii2 = (ArrayList<Tranzactii>) e.readObject();
        }
        catch(IOException l){
            l.printStackTrace();
        }

        while(scanner.hasNext()){
            String comanda = scanner.next();
            switch (comanda){
                case "LIST":
                {
                    for(Tranzactii t : tranzactii2){
                        System.out.println(t);
                    }
                    break;
                }
                case "FILTER":
                {

                    String data = scanner.next();
                    boolean check = true;
                    for(Tranzactii t : tranzactii2){
                        if(t.getData().contains(data)){
                            System.out.println(t);
                            check = false;
                        }
                    }
                    if(check){
                        System.out.println("Niciun rezultat.");
                    }
                    break;
                }
                case "NOTE":
                {
                    int id = scanner.nextInt();
                    boolean check = false;
                    for(Tranzactii t : tranzactii2){
                        if(id == t.getId() ){
                            if(t.getNote() == null){
                                System.out.println(String.format("NOTE[%d]: null", id));
                            }
                            else{
                                System.out.println(String.format("NOTE[%d]: %s", id, t.getNote()));
                            }
                            check = true;
                            break;
                        }

                    }
                    if(!check){
                        System.out.println(String.format("NOTE[%d]: not found", id));
                    }
                    break;
                }
            }
        }





        ;
    }
}
