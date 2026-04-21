package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează



//        System.out.println("TODO: implementează exercițiul 1");
        BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH));
        ArrayList<Student> arr = new ArrayList<Student>();
        var linie=buffer.readLine();
        while(linie != null){
            String[] tablou_arr= linie.split(",");
            arr.add(new Student(tablou_arr[0], Integer.parseInt(tablou_arr[1]), new Adresa(tablou_arr[2], tablou_arr[3])));
            linie=buffer.readLine();
        }
        buffer.close();



        Scanner scanner = new Scanner(System.in);
        String comanda = scanner.nextLine();
        if (Objects.equals(comanda, "PRINT")){
            for(var a : arr){
                System.out.println(a);
            }
            return;
        }

        String tip_comanda=comanda.trim().split(" ")[0];
        String nume_extras=comanda.trim().split(" ")[1];

        Student student_ales=null;
        for(var a : arr){
            if (Objects.equals(a.getNume(), nume_extras)){
                student_ales=a;
                break;
            }
        }


        Student copie;
        if(Objects.equals(tip_comanda, "SHALLOW")){
            copie=(Student) student_ales.shallow_copy();
            copie.getAdresa().setOras("MODIFICAT");

            System.out.println("Original: "+student_ales);
            System.out.println("Clona: "+ copie);

        }
        else if(Objects.equals(tip_comanda, "DEEP")){
            copie=(Student) student_ales.clone();
            copie.getAdresa().setOras("MODIFICAT");

            System.out.println("Original: "+student_ales);
            System.out.println("Clona: "+ copie);



        }



    }
}
