package com.pao.laboratory06.exercise2;

public class SRLColaborator extends Colaborator{

    public SRLColaborator(String nume, String prenume, double venit_brut_luanr){
        super(nume,prenume,venit_brut_luanr);
    }

    @Override
    double calculeazaVenitNetAnual() {
        return 0;
    }
}