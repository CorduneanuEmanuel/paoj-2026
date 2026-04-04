package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica{
    private double cheltuielilunare;
//    public SRLColaborator(String nume, String prenume, double venit_brut_luanr){
//        super(nume,prenume,venit_brut_luanr);
//    }

    @Override
    double calculeazaVenitNetAnual() {
        return (venit_brut_lunar - cheltuielilunare)*12*0.84d;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume=in.next();
        this.prenume=in.next();
        this.venit_brut_lunar=in.nextDouble();
        this.cheltuielilunare=in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.println(this.tipContract()+ ": " +
                this.nume + " " + this.prenume +
                ", venit net anual: " + String.format("%.2f",this.calculeazaVenitNetAnual()) + " lei") ;
    }

    @Override
    public String tipContract() {
        return "SRL";
    }
}