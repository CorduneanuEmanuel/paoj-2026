package com.pao.project.repository;

import com.pao.project.model.Locatie;
import com.pao.project.model.Pozitie;
import com.pao.project.model.Restaurant;
import com.pao.project.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements Repository<Restaurant, Integer> {

    @Override
    public void save(Restaurant restaurant) {
        String sql = "INSERT OR REPLACE INTO restaurante (id, cui_firma, nume, oras, adresa, poz_x, poz_y, nr_vanzari) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, restaurant.getId());
            declaratie.setInt(2, restaurant.getCUI());
            declaratie.setString(3, restaurant.getNume());
            declaratie.setString(4, restaurant.getLocatie().getOras());
            declaratie.setString(5, restaurant.getLocatie().getAdresa());
            declaratie.setDouble(6, restaurant.getLocatie().getPozitie().getX());
            declaratie.setDouble(7, restaurant.getLocatie().getPozitie().getY());
            declaratie.setInt(8, restaurant.getNrVanzari());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea restaurantului", e);
        }
    }

    @Override
    public Optional<Restaurant> findById(Integer id) {
        String sql = "SELECT id, cui_firma, nume, oras, adresa, poz_x, poz_y, nr_vanzari FROM restaurante WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, id);
            try (ResultSet rezultat = declaratie.executeQuery()) {
                if (rezultat.next()) {
                    Restaurant restaurant = mapareRestaurant(rezultat);
                    return Optional.of(restaurant);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea restaurantului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Restaurant> findAll() {
        String sql = "SELECT id, cui_firma, nume, oras, adresa, poz_x, poz_y, nr_vanzari FROM restaurante";
        List<Restaurant> restaurante = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                restaurante.add(mapareRestaurant(rezultat));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea restaurantelor", e);
        }
        return restaurante;
    }

    @Override
    public void update(Restaurant restaurant) {
        String sql = "UPDATE restaurante SET cui_firma = ?, nume = ?, oras = ?, adresa = ?, poz_x = ?, poz_y = ?, nr_vanzari = ? WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, restaurant.getCUI());
            declaratie.setString(2, restaurant.getNume());
            declaratie.setString(3, restaurant.getLocatie().getOras());
            declaratie.setString(4, restaurant.getLocatie().getAdresa());
            declaratie.setDouble(5, restaurant.getLocatie().getPozitie().getX());
            declaratie.setDouble(6, restaurant.getLocatie().getPozitie().getY());
            declaratie.setInt(7, restaurant.getNrVanzari());
            declaratie.setInt(8, restaurant.getId());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea restaurantului", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM restaurante WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, id);
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea restaurantului", e);
        }
    }

    private Restaurant mapareRestaurant(ResultSet rezultat) throws SQLException {
        Pozitie pozitie = new Pozitie(rezultat.getDouble("poz_x"), rezultat.getDouble("poz_y"));
        Locatie locatie = new Locatie(rezultat.getString("oras"), rezultat.getString("adresa"), pozitie);
        Restaurant restaurant = new Restaurant(
                rezultat.getInt("cui_firma"),
                rezultat.getInt("id"),
                rezultat.getString("nume"),
                locatie
        );

        int numarVanzari = rezultat.getInt("nr_vanzari");
        for (int i = 0; i < numarVanzari; i++) {
            restaurant.incrementNrVanzari();
        }
        return restaurant;
    }
}
