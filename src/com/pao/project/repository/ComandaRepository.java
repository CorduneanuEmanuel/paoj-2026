package com.pao.project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pao.project.model.Client;
import com.pao.project.model.Comanda;
import com.pao.project.model.Livrator;
import com.pao.project.model.Locatie;
import com.pao.project.model.Pozitie;
import com.pao.project.model.Produs;
import com.pao.project.model.Restaurant;
import com.pao.project.util.DatabaseConnection;

public class ComandaRepository implements Repository<Comanda, String> {

    private final ClientRepository repositoryClient = new ClientRepository();
    private final RestaurantRepository repositoryRestaurant = new RestaurantRepository();
    private final LivratorRepository repositoryLivrator = new LivratorRepository();

    @Override
    public void save(Comanda comanda) {
        salveazaCuTranzactie(comanda);
    }

    public void salveazaCuTranzactie(Comanda comanda) {
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        String sqlComanda = "INSERT OR REPLACE INTO comenzi (id, client_id, restaurant_id, sofer_id, status, oras_livrare, adresa_livrare, poz_x_livrare, poz_y_livrare, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlProdus = "INSERT INTO produse (id, restaurant_id, nume, pret) VALUES (?, ?, ?, ?) ON CONFLICT(id) DO UPDATE SET restaurant_id = excluded.restaurant_id, nume = excluded.nume, pret = excluded.pret";
        String sqlLegatura = "INSERT INTO comanda_produse (comanda_id, produs_id, cantitate) VALUES (?, ?, 1) ON CONFLICT(comanda_id, produs_id) DO UPDATE SET cantitate = cantitate + 1";
        String sqlRestaurant = "UPDATE restaurante SET nr_vanzari = nr_vanzari + 1 WHERE id = ?";

        try {
            conexiune.setAutoCommit(false);

            try (PreparedStatement declaratieComanda = conexiune.prepareStatement(sqlComanda)) {
                declaratieComanda.setString(1, comanda.getId());
                declaratieComanda.setInt(2, comanda.getClient().getId());
                declaratieComanda.setInt(3, comanda.getRestaurant().getId());

                Livrator sofer = comanda.getSofer();
                if (sofer == null) {
                    declaratieComanda.setNull(4, java.sql.Types.INTEGER);
                } else {
                    declaratieComanda.setInt(4, sofer.getId());
                }

                declaratieComanda.setString(5, comanda.getStatus());
                declaratieComanda.setString(6, comanda.getLocatieDomiciliu().getOras());
                declaratieComanda.setString(7, comanda.getLocatieDomiciliu().getAdresa());
                declaratieComanda.setDouble(8, comanda.getLocatieDomiciliu().getPozitie().getX());
                declaratieComanda.setDouble(9, comanda.getLocatieDomiciliu().getPozitie().getY());
                declaratieComanda.setDouble(10, calculeazaTotal(comanda.getProduse()));
                declaratieComanda.executeUpdate();
            }

            for (Produs produs : comanda.getProduse()) {
                try (PreparedStatement declaratieProdus = conexiune.prepareStatement(sqlProdus)) {
                    declaratieProdus.setInt(1, produs.getId());
                    declaratieProdus.setInt(2, comanda.getRestaurant().getId());
                    declaratieProdus.setString(3, produs.getNume());
                    declaratieProdus.setDouble(4, produs.getPret());
                    declaratieProdus.executeUpdate();
                }

                try (PreparedStatement declaratieLegatura = conexiune.prepareStatement(sqlLegatura)) {
                    declaratieLegatura.setString(1, comanda.getId());
                    declaratieLegatura.setInt(2, produs.getId());
                    declaratieLegatura.executeUpdate();
                }
            }

            try (PreparedStatement declaratieRestaurant = conexiune.prepareStatement(sqlRestaurant)) {
                declaratieRestaurant.setInt(1, comanda.getRestaurant().getId());
                declaratieRestaurant.executeUpdate();
            }

            conexiune.commit();
        
        } catch (SQLException e) {
            try {
                conexiune.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Eroare la rollback comanda", ex);
            }
            throw new RuntimeException("Eroare la salvarea comenzii", e);
        } finally {
            try {

                conexiune.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la resetare autocommit", e);
            }
        }
    }

    @Override
    public Optional<Comanda> findById(String id) {
        
        String sql = "SELECT id, client_id, restaurant_id, sofer_id, status, oras_livrare, adresa_livrare, poz_x_livrare, poz_y_livrare FROM comenzi WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setString(1, id);
            try (ResultSet rezultat = declaratie.executeQuery()) {
                if (rezultat.next()) {
                    return Optional.of(mapareComanda(rezultat));
                }
            }
        }
         catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea comenzii", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Comanda> findAll() {
        String sql = "SELECT id FROM comenzi";
        List<Comanda> comenzi = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                Optional<Comanda> comanda = findById(rezultat.getString("id"));
                comanda.ifPresent(comenzi::add);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea comenzilor", e);
        }
        return comenzi;
    }

    @Override
    public void update(Comanda comanda) {
        String sql = "UPDATE comenzi SET sofer_id = ?, status = ? WHERE id = ?";
        
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        
        
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            if (comanda.getSofer() == null) {
                declaratie.setNull(1, java.sql.Types.INTEGER);
            } else {
                declaratie.setInt(1, comanda.getSofer().getId());
            }
            declaratie.setString(2, comanda.getStatus());
            declaratie.setString(3, comanda.getId());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea comenzii", e);
        }
    }

    @Override
    public void delete(String id) {
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        String sqlLegaturi = "DELETE FROM comanda_produse WHERE comanda_id = ?";
        String sqlComanda = "DELETE FROM comenzi WHERE id = ?";

        
        try {
            conexiune.setAutoCommit(false);

            try (PreparedStatement declaratieLegaturi = conexiune.prepareStatement(sqlLegaturi)) {
                declaratieLegaturi.setString(1, id);
                declaratieLegaturi.executeUpdate();
            }

            try (PreparedStatement declaratieComanda = conexiune.prepareStatement(sqlComanda)) {
                declaratieComanda.setString(1, id);
                declaratieComanda.executeUpdate();
            }

            conexiune.commit();
        } catch (SQLException e) {
            try {
                conexiune.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Eroare la rollback stergere comanda", ex);
            }
            throw new RuntimeException("Eroare la stergerea comenzii", e);
        } finally {
            try {
                conexiune.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la resetare autocommit", e);
            }
        }
    }

    public List<String> raportComenziPeClient() {
        String sql = "SELECT c.id, c.nume, COUNT(co.id) AS numar_comenzi FROM clienti c LEFT JOIN comenzi co ON c.id = co.client_id GROUP BY c.id, c.nume ORDER BY numar_comenzi DESC";
        List<String> raport = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                raport.add(
                        rezultat.getInt("id") + " | " +
                        rezultat.getString("nume") + " | " +
                        rezultat.getInt("numar_comenzi")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la raportul clientilor", e);
        }

        return raport;
    }

    public List<String> raportTopProduse() {
        String sql = "SELECT p.id, p.nume AS nume_produs, r.nume AS nume_restaurant, SUM(cp.cantitate) AS total_bucati FROM comanda_produse cp JOIN produse p ON cp.produs_id = p.id JOIN comenzi co ON cp.comanda_id = co.id JOIN restaurante r ON co.restaurant_id = r.id GROUP BY p.id, p.nume, r.nume ORDER BY total_bucati DESC";
        List<String> raport = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                raport.add(
                        rezultat.getInt("id") + " | " +
                        rezultat.getString("nume_produs") + " | " +
                        rezultat.getString("nume_restaurant") + " | " +
                        rezultat.getInt("total_bucati")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la raportul produselor", e);
        }

        return raport;
    }

    public List<String> raportComenziActive() {
        String sql = "SELECT co.id, c.nume AS nume_client, r.nume AS nume_restaurant, COALESCE(l.nume, 'NEASIGNAT') AS nume_sofer, co.status FROM comenzi co JOIN clienti c ON co.client_id = c.id JOIN restaurante r ON co.restaurant_id = r.id LEFT JOIN livratori l ON co.sofer_id = l.id WHERE co.status <> ?";
        List<String> raport = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setString(1, "LIVRATA");
            try (ResultSet rezultat = declaratie.executeQuery()) {
                while (rezultat.next()) {
                    raport.add(
                            rezultat.getString("id") + " | " +
                            rezultat.getString("nume_client") + " | " +
                            rezultat.getString("nume_restaurant") + " | " +
                            rezultat.getString("nume_sofer") + " | " +
                            rezultat.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la raportul comenzilor active", e);
        }

        return raport;
    }

    private Comanda mapareComanda(ResultSet rezultat) throws SQLException {
        int idClient = rezultat.getInt("client_id");
        int idRestaurant = rezultat.getInt("restaurant_id");

        Client client = repositoryClient.findById(idClient).orElse(null);
        Restaurant restaurant = repositoryRestaurant.findById(idRestaurant).orElse(null);

        if (client == null || restaurant == null) {
            throw new RuntimeException("Comanda are referinte invalide");
        }

        Pozitie pozitie = new Pozitie(
                rezultat.getDouble("poz_x_livrare"),
                rezultat.getDouble("poz_y_livrare")
        );

        Locatie locatie = new Locatie(
                rezultat.getString("oras_livrare"),
                rezultat.getString("adresa_livrare"),
                pozitie
        );

        Comanda comanda = new Comanda(rezultat.getString("id"), client, restaurant, locatie);
        comanda.setStatus(rezultat.getString("status"));

        int idSofer = rezultat.getInt("sofer_id");
        if (!rezultat.wasNull()) {
            repositoryLivrator.findById(idSofer).ifPresent(comanda::setSofer);
        }

        incarcaProduseComanda(comanda);
        return comanda;
    }

    private void incarcaProduseComanda(Comanda comanda) {
        String sql = "SELECT p.id, p.nume, p.pret, cp.cantitate FROM comanda_produse cp JOIN produse p ON cp.produs_id = p.id WHERE cp.comanda_id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setString(1, comanda.getId());
            try (ResultSet rezultat = declaratie.executeQuery()) {
                while (rezultat.next()) {
                    Produs produs = new Produs(
                            rezultat.getInt("id"),
                            rezultat.getString("nume"),
                            rezultat.getDouble("pret")
                    );

                    int cantitate = rezultat.getInt("cantitate");
                    for (int i = 0; i < cantitate; i++) {
                        comanda.adaugaProdus(produs);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la incarcarea produselor comenzii", e);
        }
    }

    private double calculeazaTotal(List<Produs> produse) {
        double total = 0;
        for (Produs produs : produse) {
            total += produs.getPret();
        }
        return total;
    }
}
