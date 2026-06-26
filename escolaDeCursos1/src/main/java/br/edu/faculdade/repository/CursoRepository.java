package br.edu.faculdade.repository;

import br.edu.faculdade.model.Curso;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.Optional;

public class CursoRepository {

    public Curso save(Curso curso) {
        String sql = "INSERT INTO curso (nome, descricao, carga_horaria, vagas_totais, vagas_disponiveis) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getDescricao());
            ps.setInt(3, curso.getCargaHoraria());
            ps.setInt(4, curso.getVagasTotais());
            ps.setInt(5, curso.getVagasDisponiveis());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                curso.setId(rs.getInt("id"));
            }
            return curso;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar curso: " + e.getMessage(), e);
        }
    }

    public Optional<Curso> findById(int id) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getInt("carga_horaria"),
                            rs.getInt("vagas_totais"),
                            rs.getInt("vagas_disponiveis")
                    );
                    return Optional.of(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public void decrementarVaga(int idCurso) {
        String sql = "UPDATE curso SET vagas_disponiveis = vagas_disponiveis - 1 WHERE id = ? AND vagas_disponiveis > 0";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new IllegalStateException("Nenhuma vaga decrementada. Verifique se há vagas disponíveis.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao decrementar vaga: " + e.getMessage(), e);
        }
    }
}