package com.pao.laboratory08.exercise1;

public class Student implements Cloneable {
    private String nume;
    private int varsta;
    private Adresa adresa;

    public Student(String nume, int varsta,  Adresa adresa) {
        this.varsta = varsta;
        this.nume = nume;
        this.adresa = adresa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", adresa=" + adresa +
                '}';
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Student student =  (Student) super.clone();
        student.setAdresa((Adresa) getAdresa().clone());
        return student;
    }



    public Object shallow_copy() throws CloneNotSupportedException {
        return super.clone();
    }
    // constructor(String nume, int varsta, Adresa adresa)
    // getteri, setteri
    // toString() → "Student{nume='...', varsta=..., adresa=Adresa{oras='...', strada='...'}}"

    // clone() — implementare diferită pentru shallow vs. deep (vezi mai jos)
}