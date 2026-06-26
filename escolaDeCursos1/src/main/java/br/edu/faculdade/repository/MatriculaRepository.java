package br.edu.faculdade.repository;

import br.edu.faculdade.model.Matricula;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaRepository {

    public Matricula save(Matricula m) {
        String sql = "INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getIdAluno());
            ps.setInt(2, m.getIdCurso());
            ps.setDate(3, Date.valueOf(m.getDataMatricula()));
            ps.setBigDecimal(4, m.getValor());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) m.setId(rs.getInt("id"));
            return m;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage(), e);
        }
    }

    public boolean existeMatricula(int idAluno, int idCurso) {
        String sql = "SELECT COUNT(*) FROM matricula WHERE id_aluno = ? AND id_curso = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAluno);
            ps.setInt(2, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar matrícula: " + e.getMessage(), e);
        }
        return false;
    }

    public List<Matricula> findByAlunoId(int idAluno) {
        String sql = "SELECT * FROM matricula WHERE id_aluno = ?";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrículas do aluno: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Matricula> findByCursoId(int idCurso) {
        String sql = "SELECT * FROM matricula WHERE id_curso = ?";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrículas do curso: " + e.getMessage(), e);
        }
        return lista;
    }

    private Matricula mapRow(ResultSet rs) throws SQLException {
        return new Matricula(
                rs.getInt("id"),
                rs.getInt("id_aluno"),
                rs.getInt("id_curso"),
                rs.getDate("data_matricula").toLocalDate(),
                rs.getBigDecimal("valor")
        );
    }
}