package com.pao.laboratory06.exercise2;

abstract class Colaborator implements IOperatiiCitireScriere, Comparable<Colaborator> {
    String nume;
    String prenume;
    double venit_brut_lunar;
    final static double venit_minim_brut_lunar=4050.0;
//    Colaborator(String nume, String prenume, double venit_brut_lunar){
//        this.nume=nume;
//        this.prenume=prenume;
//        this.venit_brut_lunar=venit_brut_lunar;
//    }

    public int compareTo(Colaborator c){
        return Double.compare(this.calculeazaVenitNetAnual(), c.calculeazaVenitNetAnual());
    }

    abstract double calculeazaVenitNetAnual();


    abstract public TipColaborator getTip();
}

interface PersoanaFizica {}
interface PersoanaJuridica {}



