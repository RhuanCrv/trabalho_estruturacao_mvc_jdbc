package br.edu.faculdade.repository;

import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.Optional;

public class AlunoRepository {

    public Aluno save(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, email, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setString(3, aluno.getTelefone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aluno.setId(rs.getInt("id"));
            }
            return aluno;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    public Optional<Aluno> findById(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone")
                    );
                    return Optional.of(aluno);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}