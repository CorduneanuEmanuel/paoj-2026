package com.pao.laboratory05.angajati;

import com.pao.laboratory05.biblioteca.BibliotecaService;
import com.pao.laboratory05.biblioteca.Carte;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;

    private AngajatService(){
        this.angajati=new Angajat[0];
    }
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    public static AngajatService getInstance() {
        return AngajatService.Holder.INSTANCE;
    }

    public void addAngajat(Angajat a){
        Angajat[] angajat = new Angajat[this.angajati.length+1];
        System.arraycopy(this.angajati, 0, angajat, 0, this.angajati.length);
        angajat[this.angajati.length]=a;
        this.angajati=angajat;
    }

    void printAll(){
        for(var a : angajati){
            System.out.println(a.getNume());
        }
        System.out.println();
    }
    void listBySalary(){
        Angajat[] angajat=new Angajat[this.angajati.length];
        System.arraycopy(this.angajati, 0, angajat, 0, this.angajati.length);
        Arrays.sort(angajat);
        for(var a : angajat){
            System.out.println(a.getNume());
        }
        System.out.println();
    }
    void findByDepartament(String numeDept){
        boolean ok=false;
        for(var a : this.angajati){
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(a.getNume());
                ok=true;
            }
        }
        if (!ok){
            System.out.println("Niciun angajat in departamentul "+numeDept+".");
        }
    }



}
