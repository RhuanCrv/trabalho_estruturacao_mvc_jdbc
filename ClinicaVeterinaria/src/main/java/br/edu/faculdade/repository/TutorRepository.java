package br.edu.faculdade.repository;

import br.edu.faculdade.model.Tutor;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.Optional;

public class TutorRepository {
    public Tutor save(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tutor.getNome());
            ps.setString(2, tutor.getEndereco());
            ps.setString(3, tutor.getTelefone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tutor.setId(rs.getInt("id"));
            return tutor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tutor: " + e.getMessage(), e);
        }
    }

    public Optional<Tutor> findById(int id) {
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Tutor(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco"), rs.getString("telefone")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}