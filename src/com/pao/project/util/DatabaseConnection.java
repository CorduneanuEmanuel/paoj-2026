package com.pao.project.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection INSTANCE;
    private Connection connection;
    private String url;

    private DatabaseConnection() {
        try {
            Properties proprietati = new Properties();
            InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new IOException("Fisierul db.properties nu a fost gasit");
            }
            proprietati.load(input);
            this.url = proprietati.getProperty("db.url");
            String driver = proprietati.getProperty("db.driver");
            if (driver != null && !driver.isEmpty()) {
                Class.forName(driver);
            }
            this.connection = DriverManager.getConnection(url);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Eroare la conectare la baza de date", e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnection();
        }
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                this.connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la obtinerea conexiunii", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inchiderea conexiunii", e);
        }
    }
}
