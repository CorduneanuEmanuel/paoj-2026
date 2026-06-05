package com.pao.project.service;

import com.pao.project.model.Restaurant;
import com.pao.project.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ServiceRestaurant {

    private static ServiceRestaurant INSTANCE;
    private final Map<Integer, Restaurant> restaurante = new TreeMap<>();
    private final RestaurantRepository repositoryRestaurant = new RestaurantRepository();

    private ServiceRestaurant() {
    }


    public static ServiceRestaurant getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceRestaurant();
        }
        return INSTANCE;
    }

    public void adaugaRestaurant(Restaurant restaurant) {
        restaurante.put(restaurant.getId(), restaurant);
        repositoryRestaurant.save(restaurant);
    }

    public Restaurant cautaRestaurant(int idRestaurant) {
        Restaurant restaurant = restaurante.get(idRestaurant);
        if (restaurant != null) {
            return restaurant;
        }
        Restaurant restaurantBazaDate = repositoryRestaurant.findById(idRestaurant).orElse(null);
        if (restaurantBazaDate != null) {
            restaurante.put(idRestaurant, restaurantBazaDate);
        }
        return restaurantBazaDate;
    }

    public List<Restaurant> getRestaurante() {
        List<Restaurant> restauranteBazaDate = repositoryRestaurant.findAll();
        for (Restaurant restaurant : restauranteBazaDate) {
            restaurante.put(restaurant.getId(), restaurant);
        }
        return new ArrayList<>(restaurante.values());
    }

    public void updateRestaurant(Restaurant restaurant) {
        restaurante.put(restaurant.getId(), restaurant);
        repositoryRestaurant.update(restaurant);
    }

    public void stergeRestaurant(int idRestaurant) {
        restaurante.remove(idRestaurant);
        repositoryRestaurant.delete(idRestaurant);
    }

}
