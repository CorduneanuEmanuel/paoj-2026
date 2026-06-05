package com.pao.project.repository;

import com.pao.project.model.Firma;
import com.pao.project.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FirmaRepository implements Repository<Firma, Integer> {

    @Override
    public void save(Firma firma) {
        String sql = "INSERT OR REPLACE INTO firme (cui, nr_ordine, nume) VALUES (?, ?, ?)";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, firma.getCUI());
            declaratie.setString(2, firma.getNumar_ordine_Registru_Comert());
            declaratie.setString(3, firma.getNume());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea firmei", e);
        }
    }

    @Override
    public Optional<Firma> findById(Integer cui) {
        String sql = "SELECT cui, nr_ordine, nume FROM firme WHERE cui = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, cui);
            try (ResultSet rezultat = declaratie.executeQuery()) {
                if (rezultat.next()) {
                    Firma firma = new Firma(
                            rezultat.getInt("cui"),
                            rezultat.getString("nr_ordine"),
                            rezultat.getString("nume")
                    );
                    return Optional.of(firma);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea firmei", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Firma> findAll() {
        String sql = "SELECT cui, nr_ordine, nume FROM firme";
        List<Firma> firme = new ArrayList<>();
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql);
             ResultSet rezultat = declaratie.executeQuery()) {
            while (rezultat.next()) {
                firme.add(new Firma(
                        rezultat.getInt("cui"),
                        rezultat.getString("nr_ordine"),
                        rezultat.getString("nume")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea firmelor", e);
        }
        return firme;
    }

    @Override
    public void update(Firma firma) {
        String sql = "UPDATE firme SET nr_ordine = ?, nume = ? WHERE cui = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setString(1, firma.getNumar_ordine_Registru_Comert());
            declaratie.setString(2, firma.getNume());
            declaratie.setInt(3, firma.getCUI());
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea firmei", e);
        }
    }

    @Override
    public void delete(Integer cui) {
        String sql = "DELETE FROM firme WHERE cui = ?";
        Connection conexiune = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement declaratie = conexiune.prepareStatement(sql)) {
            declaratie.setInt(1, cui);
            declaratie.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea firmei", e);
        }
    }
}
