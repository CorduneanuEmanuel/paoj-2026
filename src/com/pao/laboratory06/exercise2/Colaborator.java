package com.pao.laboratory06.exercise2;

abstract class Colaborator {
    String nume;
    String prenume;
    double venit_brut_lunar;

    Colaborator(String nume, String prenume, double venit_brut_lunar){
        this.nume=nume;
        this.prenume=prenume;
        this.venit_brut_lunar=venit_brut_lunar;
    }

    abstract double calculeazaVenitNetAnual();

}



