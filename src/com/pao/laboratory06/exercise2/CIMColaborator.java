package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica{
    private boolean arebonus=false;

//    public CIMColaborator(String nume, String prenume, double venit_brut_luanr){
//        super(nume,prenume,venit_brut_luanr);
//    }
    @Override
    double calculeazaVenitNetAnual() {
        return this.areBonus()? this.venit_brut_lunar*12*0.55*1.1 : this.venit_brut_lunar*12*0.55;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume=in.next();
//        System.out.println("Introdu prenumele: ");
        this.prenume=in.next();
//        System.out.println("Introdu venitul lunar brut: ");
        this.venit_brut_lunar=in.nextDouble();
        if (in.hasNext()){
            String a=in.next();
            this.arebonus= a.equals("DA");
        }
//        System.out.println("Spune")

    }

    @Override
    public void afiseaza() {
         System.out.println(this.tipContract()+ ": " +
                 this.nume + " " + this.prenume +
                 ", venit net anual: " + String.format("%.2f",this.calculeazaVenitNetAnual()) + " lei") ;
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public boolean areBonus() {
        return this.arebonus;
    }
}