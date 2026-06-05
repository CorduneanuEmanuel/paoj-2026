package com.pao.project.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializareBazaDate {

    private static InitializareBazaDate INSTANCE;

    private InitializareBazaDate() {
    }

    public static InitializareBazaDate getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InitializareBazaDate();
        }
        return INSTANCE;
    }

    public void initializare() {
        String continutSchema = citesteSchema();
        String[] comenziSql = continutSchema.split(";");
        Connection conexiune = DatabaseConnection.getInstance().getConnection();

        for (String comandaSql : comenziSql) {
            String instructiune = comandaSql.trim();
            if (instructiune.isEmpty()) {
                continue;
            }
            try (PreparedStatement declaratie = conexiune.prepareStatement(instructiune)) {
                declaratie.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la initializarea bazei de date", e);
            }
        }
    }

    private String citesteSchema() {
        InputStream flux = InitializareBazaDate.class.getClassLoader().getResourceAsStream("schema.sql");
        if (flux != null) {
            return citesteDinFlux(flux);
        }

        Path cale = Path.of("resources", "schema.sql");
        if (Files.exists(cale)) {
            try {
                return Files.readString(cale, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("Eroare la citirea fisierului schema.sql", e);
            }
        }

        throw new RuntimeException("Fisierul schema.sql nu a fost gasit");
    }

    private String citesteDinFlux(InputStream flux) {
        StringBuilder continut = new StringBuilder();
        try (BufferedReader cititor = new BufferedReader(new InputStreamReader(flux, StandardCharsets.UTF_8))) {
            String linie;
            while ((linie = cititor.readLine()) != null) {
                continut.append(linie).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea fisierului schema.sql", e);
        }
        return continut.toString();
    }
}
