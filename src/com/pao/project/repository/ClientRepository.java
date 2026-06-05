package com.pao.project.repository;

import com.pao.project.model.Client;
import com.pao.project.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, Integer> {

    @Override
    public void save(Client client) {
        String sql = "INSERT OR REPLACE INTO clienti (id, nume) VALUES (?, ?)";

        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {

            declaratie.setInt(1, client.getId());
            declaratie.setString(2, client.getNume());

            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea clientului", e);
        }
    }

    @Override
    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM clienti WHERE id = ?";

        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {

            declaratie.setInt(1, id);
            try (ResultSet rezultat = declaratie.executeQuery()) {
                if (rezultat.next()) {
                    Client client = mapRowToClient(rezultat);
                    return Optional.of(client);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea clientului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clienti";
        List<Client> clienti = new ArrayList<>();

        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {

            while (rezultat.next()) {
                clienti.add(mapRowToClient(rezultat));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea clientilor", e);
        }
        return clienti;
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE clienti SET nume = ? WHERE id = ?";

        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {

            declaratie.setString(1, client.getNume());
            declaratie.setInt(2, client.getId());

            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea clientului", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM clienti WHERE id = ?";

        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {

            declaratie.setInt(1, id);
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea clientului", e);
        }
    }

    private Client mapRowToClient(ResultSet rs) throws SQLException {
        return new Client(rs.getInt("id"), rs.getString("nume"));
    }
}
