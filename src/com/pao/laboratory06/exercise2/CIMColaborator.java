package com.pao.laboratory06.exercise2;

public class CIMColaborator extends Colaborator{


    public CIMColaborator(String nume, String prenume, double venit_brut_luanr){
        super(nume,prenume,venit_brut_luanr);
    }
    @Override
    double calculeazaVenitNetAnual() {
        return this.venit_brut_lunar*12*0.55 + ();
    }
}