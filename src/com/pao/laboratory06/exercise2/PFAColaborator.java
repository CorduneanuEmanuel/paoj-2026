package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica{

//    public PFAColaborator(String nume, String prenume, double venit_brut_luanr){
//        super(nume,prenume,venit_brut_luanr);
//    }

    private double cheltuielilunare;

    @Override
    double calculeazaVenitNetAnual() {
        double venit_net = (this.venit_brut_lunar - this.cheltuielilunare) *12;
        double impozit_venit = 0.1d * venit_net;
        double cass = 0;
        if (venit_net<6*venit_minim_brut_lunar){
            cass=0.1d*6*venit_minim_brut_lunar;
        }
        else if(venit_net<=72*venit_minim_brut_lunar){
            cass=0.1d*venit_net;
        }
        else{
            cass=0.1d*(72*venit_minim_brut_lunar);
        }
        double cas=0;
        if(venit_net<12*venit_minim_brut_lunar){
            cas=0;
        }
        else if(venit_net<=24*venit_minim_brut_lunar){
            cas=0.25d*(12*venit_minim_brut_lunar);
        }
        else {
            cas= 0.25d*(24*venit_minim_brut_lunar);
        }
        return venit_net-impozit_venit-cass-cas;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
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
        return "PFA";
    }
}