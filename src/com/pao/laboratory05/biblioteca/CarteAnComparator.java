package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAnComparator implements Comparator<Carte> {
    public int compare(Carte a, Carte b){
        if(a.getan()>b.getan())return 1;
        else if(a.getan()==b.getan())return 0;
        else return -1;
    }
}
