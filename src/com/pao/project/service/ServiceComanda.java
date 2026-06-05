package com.pao.project.service;

import com.pao.project.model.Comanda;
import com.pao.project.repository.ComandaRepository;

import java.util.ArrayList;
import java.util.List;

public class ServiceComanda {

    private static ServiceComanda INSTANCE;
    private final List<Comanda> comenzi = new ArrayList<>();
    private final ComandaRepository repositoryComanda = new ComandaRepository();

    private ServiceComanda(){
    }

    public static ServiceComanda getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceComanda();
        }
        return INSTANCE;
    }


    public void adaugaComanda(Comanda c) {
        repositoryComanda.salveazaCuTranzactie(c);
        comenzi.add(c);
    }

    public Comanda cautaComanda(String id) {
        for (Comanda c : comenzi) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        Comanda comandaBazaDate = repositoryComanda.findById(id).orElse(null);
        if (comandaBazaDate != null) {
            comenzi.add(comandaBazaDate);
        }
        return comandaBazaDate;
    }

    public List<Comanda> getComenzi() {
        List<Comanda> comenziBazaDate = repositoryComanda.findAll();
        for (Comanda comandaBazaDate : comenziBazaDate) {
            boolean exista = false;
            for (Comanda comandaMemorie : comenzi) {
                if (comandaMemorie.getId().equals(comandaBazaDate.getId())) {
                    exista = true;
                    break;
                }
            }
            if (!exista) {
                comenzi.add(comandaBazaDate);
            }
        }
        return comenzi;
    }

    public void updateComanda(Comanda comanda) {
        repositoryComanda.update(comanda);
        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).getId().equals(comanda.getId())) {
                comenzi.set(i, comanda);
                return;
            }
        }
        comenzi.add(comanda);
    }

    public void finalizeazaLivrare(String idComanda) {
        Comanda comanda = cautaComanda(idComanda);
        if (comanda == null) {
            throw new RuntimeException("Comanda nu exista");
        }
        comanda.setStatus("LIVRATA");
        if (comanda.getSofer() != null) {
            comanda.getSofer().setDisponibil(true);
            ServiceLivrator.getInstance().updateSofer(comanda.getSofer());
        }
        updateComanda(comanda);
    }

    public List<String> raportComenziPeClient() {
        return repositoryComanda.raportComenziPeClient();
    }

    public List<String> raportTopProduse() {
        return repositoryComanda.raportTopProduse();
    }

    public List<String> raportComenziActive() {
        return repositoryComanda.raportComenziActive();
    }
}