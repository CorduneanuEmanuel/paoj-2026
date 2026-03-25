package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAutorComparator implements Comparator<Carte> {
        public int compare(Carte a, Carte b){
            int l=a.getautor().compareTo(b.getautor());
            return l;
        }
}
