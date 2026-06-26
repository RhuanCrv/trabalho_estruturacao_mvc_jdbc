package br.edu.faculdade.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL  = "jdbc:postgresql://localhost:5432/oficina-mecanica";
    private static final String USER = "postgres";
    private static final String PASS = "1234";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }
}
