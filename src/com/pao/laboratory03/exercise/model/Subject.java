package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ("Programare Avansată pe Obiecte", 6){

    },
    BD("Baze de date", 4){

    },
    SO("Sisteme de operare", 5){

    },
    RC("Retele si calculatoare", 4){

    };
    final String fullname;
    final int credits;

    private Subject(String fullname, int b)
    {
        this.fullname=fullname;
        this.credits=b;
    }

    public int getCredits(){
        return this.credits;
    }
    public String getFullname(){
        return this.fullname;
    }

    @Override
    public String toString(){
        return this.name() + "(" + this.fullname + ", " + this.credits + " credite)";
    }


}
