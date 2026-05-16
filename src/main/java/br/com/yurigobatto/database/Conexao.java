package br.com.yurigobatto.database;

import java.sql.*;

public class Conexao {

    private static Conexao instancia;

    private final Connection database;

    public Conexao() {
        try {
            database = DriverManager.getConnection("jdbc:sqlite:meu_banco.db");
            new Migracao(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Statement createStatement() {
        try {
            return database.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement createPreparedStatement(String sql) {
        try {
            return database.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet execute(String sql) {
        try {
            return createStatement().executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet execute(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Conexao getInstancia() {
        if (instancia == null)
            instancia = new Conexao();
        return instancia;
    }
}
