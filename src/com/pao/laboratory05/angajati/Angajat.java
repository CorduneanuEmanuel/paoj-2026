package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private Departament departament;
    private double salariu;

    public Angajat(Departament departament, String nume, double salariu) {
        this.departament = departament;
        this.nume = nume;
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return "Angajat{nume="+this.getNume()+", departament="+this.getDepartament()+", salariu="+this.getSalariu()+"}";
    }

    public double getSalariu() {
        return salariu;
    }

    public Departament getDepartament() {
        return departament;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public int compareTo(Angajat o) {
        if(this.getSalariu()<o.getSalariu())return -1;
        else if(this.getSalariu()==o.getSalariu())return 0;
        else return 1;
    }
}
