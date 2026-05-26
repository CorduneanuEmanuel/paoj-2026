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

            lista.add(new Tranzactie(id, suma, data, tip));

        }

        while(scanner.hasNext()){
            String comanda = scanner.next();

            switch(comanda){
                case "UNIQUE_IDS" : {
                    LinkedHashSet<Integer> set = new LinkedHashSet<>();
                    for(var i : lista){
                        set.add(i.getId());
                    }
                    System.out.printf("IDs unice (%d): %s\n", set.size(), set);
//                    System.out.println(set);

                    break;
                }
                case "MONTHLY_REPORT" : {
                    TreeMap<String, double[]> sortare = new TreeMap<>();
                    for(var i : lista){
                        String luna = i.getData().substring(0, 7);

                        if(!sortare.containsKey(luna)){
//                            double [] t  = sortare.get(i.toString());
                            sortare.put(luna, new double[]{0.0, 0.0});
                        }

                        double[] suma = sortare.get(luna);
                        if(i.getTip() == TipTranzactie.CREDIT){
                            suma[0] += i.getSuma();
                        }
                        else{
                            suma[1] += i.getSuma();
                        }

                    }

                    for(Map.Entry<String, double[]> entry : sortare.entrySet()){
                        System.out.printf("%s: CREDIT %.2f RON, DEBIT %.2f RON\n", entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
                    }

                    break;
                }
                case "TOP" : {
                    int nn = scanner.nextInt();

                    ArrayList<Tranzactie> copie = new ArrayList<>(lista);

                    copie.sort((a, b) -> Double.compare(b.getSuma(), a.getSuma()));

                    List<Tranzactie> sublista = copie.subList(0, Math.min(nn, copie.size()));
                    for(var i : sublista){
                        System.out.println(i);
                    }

                    break;
                }
                case "SORT_ASC" : {
                    lista.sort((a, b) -> Double.compare(a.getSuma(), b.getSuma()));
                    for (var i : lista) {
                        System.out.println(i);
                    }
                    break;
                }
                case "SORT_DESC" : {
                    lista.sort((a, b) -> Double.compare(b.getSuma(), a.getSuma()));
                    for (var i : lista) {
                        System.out.println(i);
                    }
                    break;
                }
                case "REVERSE" : {
                    Collections.reverse(lista);
                    for (var i : lista) {
                        System.out.println(i);
                    }
                    break;
                }
                case "MIN_MAX" : {
                    if (!lista.isEmpty()) {
                        Tranzactie min = Collections.min(lista, (a, b) -> Double.compare(a.getSuma(), b.getSuma()));
                        Tranzactie max = Collections.max(lista, (a, b) -> Double.compare(a.getSuma(), b.getSuma()));
                        System.out.println("MIN: " + min);
                        System.out.println("MAX: " + max);
                    }
                    break;
                }
                case "CME_DEMO" : {
                    try {  for (Tranzactie t : lista) lista.remove(t); }
                    catch (ConcurrentModificationException e) { System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata."); }
                    break;
                }
            }

        }
    }



}
