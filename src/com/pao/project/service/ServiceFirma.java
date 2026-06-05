package com.pao.project.service;

import com.pao.project.exception.FirmaInexistenta;
import com.pao.project.model.Firma;
import com.pao.project.model.Restaurant;
import com.pao.project.repository.FirmaRepository;
import com.pao.project.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

public class ServiceFirma{

    private static ServiceFirma INSTANCE;
    private final List<Firma> firme = new ArrayList<>();
    private final FirmaRepository repositoryFirma = new FirmaRepository();
    private final RestaurantRepository repositoryRestaurant = new RestaurantRepository();

    private ServiceFirma() {
    }

    public static ServiceFirma getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceFirma();
        }
        return INSTANCE;
    }

    public void adaugaFirma(Firma firma) {
        if (cautaFirma(firma.getCUI()) != null) {
            return;
        }
        firme.add(firma);
        repositoryFirma.save(firma);
    }

    public Firma cautaFirmaDupaCUI(int CUI) {
        for (Firma f : firme) {
            if (f.getCUI() == CUI) {
                return f;
            }
        }
        Firma firma = repositoryFirma.findById(CUI).orElse(null);
        if (firma != null) {
            firme.add(firma);
        }
        return firma;
    }

    public void stergeFirma(int CUI) {
        for (int i = 0; i < firme.size(); i++) {
            if (firme.get(i).getCUI() == CUI) {
                firme.remove(i);
                break;
            }
        }
        repositoryFirma.delete(CUI);
    }

    public Firma cautaFirma(int CUI) {
        for(Firma a : firme){
            if(a.getCUI() == CUI){
                return a;
            }
        }
        return cautaFirmaDupaCUI(CUI);
    }


    public void adaugaRestaurantFirma(int cuiFirma, Restaurant restaurant) {
        Firma firma = cautaFirma(cuiFirma);
        if (firma == null) {
            throw new FirmaInexistenta("Firma nu exista");
        }
        firma.getRestaurante().add(restaurant);
        repositoryRestaurant.save(restaurant);
    }


    public List<Restaurant> getToateRestaurantele() {
        List<Restaurant> toate = new ArrayList<>();
        for (Firma f : firme) {
            for (Restaurant r : f.getRestaurante()) {
                toate.add(r);
            }
        }
        List<Restaurant> restauranteBazaDate = repositoryRestaurant.findAll();
        for (Restaurant restaurant : restauranteBazaDate) {
            boolean exista = false;
            for (Restaurant restaurantExistent : toate) {
                if (restaurantExistent.getId() == restaurant.getId()) {
                    exista = true;
                    break;
                }
            }
            if (!exista) {
                toate.add(restaurant);
            }
        }
        return toate;
    }

    public List<Firma> getToateFirmeleReferinta() {
        List<Firma> firmeBazaDate = repositoryFirma.findAll();
        for (Firma firma : firmeBazaDate) {
            boolean exista = false;
            for (Firma firmaExistenta : firme) {
                if (firmaExistenta.getCUI() == firma.getCUI()) {
                    exista = true;
                    break;
                }
            }
            if (!exista) {
                firme.add(firma);
            }
        }
        return firme;
    }

    public List<Firma> getToateFirmeleClona() {
        List<Firma> clonaFirma = new ArrayList<>();

        for(Firma a : firme){
            Firma clona = a.clone();
            clonaFirma.add(clona);
        }
        return clonaFirma;
    }



}