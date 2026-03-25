package com.pao.laboratory05.biblioteca;

public class Carte implements Comparable<Carte> {
    private String titlu;
    private String autor;
    private int an;
    private double rating;

    public String toString(){
        return "Carte{titlu=+"+this.titlu+", autor="+this.autor+", an="+this.an+", rating="+this.rating+"}";
    }

    public Carte(String titlu, String autor, int an, double rating) {
        this.titlu = titlu;
        this.autor = autor;
        this.an = an;
        this.rating = rating;
    }

    public int compareTo(Carte a){
        double l = this.rating-a.rating;
        if (l==0){
            return 0;
        }
        else if(l<0){
            return 1;
        }
        else return -1;
    }
    public String getautor() {
        return autor;
    }

    public String gettitlu() {
        return titlu;
    }

    public int getan() {
        return an;
    }

    public double getrating() {
        return rating;
    }


}
