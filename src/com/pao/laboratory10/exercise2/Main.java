package com.pao.laboratory10.exercise2;
import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

//        System.out.println("TODO: implementează exercițiul 2");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Tranzactie> lista = new ArrayList<>();
        int n = scanner.nextInt();
        for(int i = 0; i < n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

        }

        while(scanner.hasNext()){
            String comanda = scanner.next();

            switch(comanda){
                case "UNIQUE_IDS" : {
                    LinkedHashSet<Integer> set = new LinkedHashSet<>();
                    for(var i : lista){
                        set.add(i.getId());
                    }
                    System.out.printf("IDs unice %d: ", set.size());
                    System.out.println(set);

                    break;
                }
                case "MONTHLY_REPORT" : {
                    TreeMap<String, double[]> sortare = new TreeMap<>();
                    for(var i : lista){
                        if(sortare.containsKey(i.toString())){
                            double [] t  = sortare.get(i.toString());

                        }
                        else{
                            double[] a = {0, 0};
                            sortare.put(i.toString(), a);
                        }
                        sortare.put(i.toString(), i);
                    }
                    break;
                }
                case "TOP" : {

                    break;
                }
                case "SORT_ASC" : {

                    break;
                }
                case "SORT_DESC" : {

                    break;
                }
                case "REVERSE" : {

                    break;
                }
                case "MIN_MAX" : {

                    break;
                }
                case "CME_DEMO" : {

                    break;
                }
            }

        }
    }



}
