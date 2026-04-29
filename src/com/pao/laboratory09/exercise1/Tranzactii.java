package com.pao.laboratory09.exercise1;

import java.io.Serializable;

public class Tranzactii implements Serializable {
    int id;

    double suma;

    String data;

    String contSursa;

    String contDestinatie;

    String tip;

    public Tranzactii(int id, double suma, String data, String contSursa, String contDestinatie, String tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        this.tip = tip;
    }


}
