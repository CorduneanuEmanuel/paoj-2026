package com.pao.laboratory06.exercise2;

public enum TipColaborator {
    CIM("PersoanaJuridica"),
    PFA("PersoanaFizica"),
    SRL("PersoanaJuridica");

    private TipColaborator(String tip){
        this.tip_colaborator=tip;
    }

    String tip_colaborator;
}
