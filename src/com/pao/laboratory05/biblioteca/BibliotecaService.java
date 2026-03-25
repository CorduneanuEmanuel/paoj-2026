package com.pao.laboratory05.biblioteca;

import com.pao.laboratory05.playlist.Song;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti;

    private BibliotecaService(){
        carti=new Carte[0];
    }
    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }
    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    public void addCarte(Carte carte){
        Carte[] carti = new Carte[this.carti.length+1];
        System.arraycopy(this.carti, 0, carti, 0, this.carti.length);
        carti[this.carti.length]=carte;
        this.carti=carti;
    }

    public void listSortedByRating(){
        Carte[] carti=new Carte[this.carti.length];
        System.arraycopy(this.carti, 0, carti, 0, this.carti.length);
        Arrays.sort(carti);
        for(var i : carti){
            System.out.println(i.gettitlu() + " "+ i.getrating());
        }
    }

    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] carti=new Carte[this.carti.length];
        System.arraycopy(this.carti, 0, carti, 0, this.carti.length);
        Arrays.sort(carti, comparator);
        for(var i : carti){
            System.out.println(i.gettitlu());
        }
    }


}
