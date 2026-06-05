package com.pao.project.service;

import com.pao.project.model.Livrator;
import com.pao.project.model.Locatie;
import com.pao.project.model.SoferDistantaRestaurant;
import com.pao.project.repository.LivratorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceLivrator {

    private final List<Livrator> soferi = new ArrayList<>();
    private static ServiceLivrator INSTANCE;
    private final LivratorRepository repositoryLivrator = new LivratorRepository();


    private ServiceLivrator(){
    }

    public static ServiceLivrator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceLivrator();
        }
        return INSTANCE;
    }


    public void adaugaSofer(Livrator s) {
        soferi.add(s);
        repositoryLivrator.save(s);
    }

    public List<Livrator> sorteazaDupaDistanta(Locatie locatie) {
        sincronizeazaSoferiDinBazaDate();
        List<Livrator> lista = new ArrayList<>(soferi);
        Collections.sort(lista, new SoferDistantaRestaurant(locatie));
        return lista;
    }

    public Livrator celMaiApropiat(Locatie locatie) {
        List<Livrator> lista = sorteazaDupaDistanta(locatie);
        for (Livrator s: lista) {
            if (s.isDisponibil()) {
                return s;
            }
        }
        throw new RuntimeException("Nu exista un cel mai apropriat!");

    }

    public void updateSofer(Livrator sofer) {
        repositoryLivrator.update(sofer);
        for (int i = 0; i < soferi.size(); i++) {
            if (soferi.get(i).getId() == sofer.getId()) {
                soferi.set(i, sofer);
                return;
            }
        }
        soferi.add(sofer);
    }

    private void sincronizeazaSoferiDinBazaDate() {
        List<Livrator> soferiBazaDate = repositoryLivrator.findAll();
        for (Livrator soferBazaDate : soferiBazaDate) {
            boolean exista = false;
            for (Livrator soferMemorie : soferi) {
                if (soferMemorie.getId() == soferBazaDate.getId()) {
                    exista = true;
                    break;
                }
            }
            if (!exista) {
                soferi.add(soferBazaDate);
            }
        }
    }

}
