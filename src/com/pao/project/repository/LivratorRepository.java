package com.pao.project.repository;

import com.pao.project.model.Livrator;
import com.pao.project.model.Pozitie;
import com.pao.project.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivratorRepository implements Repository<Livrator, Integer> {

    @Override
    public void save(Livrator livrator) {
        String sql = "INSERT OR REPLACE INTO livratori (id, nume, disponibil, poz_x, poz_y) VALUES (?, ?, ?, ?, ?)";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, livrator.getId());
            declaratie.setString(2, livrator.getNume());
            declaratie.setInt(3, livrator.isDisponibil() ? 1 : 0);
            declaratie.setDouble(4, livrator.getPozitie().getX());
            declaratie.setDouble(5, livrator.getPozitie().getY());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea livratorului", e);
        }
    }

    @Override
    public Optional<Livrator> findById(Integer id) {
        String sql = "SELECT id, nume, disponibil, poz_x, poz_y FROM livratori WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, id);
            try (ResultSet rezultat = declaratie.executeQuery()) {
                if (rezultat.next()) {
                    Livrator livrator = mapareLivrator(rezultat);
                    return Optional.of(livrator);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea livratorului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Livrator> findAll() {
        String sql = "SELECT id, nume, disponibil, poz_x, poz_y FROM livratori";
        List<Livrator> livratori = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                livratori.add(mapareLivrator(rezultat));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea livratorilor", e);
        }
        return livratori;
    }

    @Override
    public void update(Livrator livrator) {
        String sql = "UPDATE livratori SET nume = ?, disponibil = ?, poz_x = ?, poz_y = ? WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setString(1, livrator.getNume());
            declaratie.setInt(2, livrator.isDisponibil() ? 1 : 0);
            declaratie.setDouble(3, livrator.getPozitie().getX());
            declaratie.setDouble(4, livrator.getPozitie().getY());
            declaratie.setInt(5, livrator.getId());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea livratorului", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM livratori WHERE id = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, id);
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea livratorului", e);
        }
    }

    private Livrator mapareLivrator(ResultSet rezultat) throws SQLException {
        Pozitie pozitie = new Pozitie(rezultat.getDouble("poz_x"), rezultat.getDouble("poz_y"));
        Livrator livrator = new Livrator(
                rezultat.getInt("id"),
                rezultat.getString("nume"),
                pozitie
        );
        livrator.setDisponibil(rezultat.getInt("disponibil") == 1);
        return livrator;
    }
}
