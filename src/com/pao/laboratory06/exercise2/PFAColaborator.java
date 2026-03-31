package com.pao.laboratory06.exercise2;

public class PFAColaborator extends Colaborator{

    public PFAColaborator(String nume, String prenume, double venit_brut_luanr){
        super(nume,prenume,venit_brut_luanr);
    }

    @Override
    double calculeazaVenitNetAnual() {
        return 0;
    }
}