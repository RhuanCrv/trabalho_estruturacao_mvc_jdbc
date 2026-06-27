package br.edu.faculdade.repository;

import br.edu.faculdade.model.Consulta;
import br.edu.faculdade.util.Conexao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepository {

    public Consulta save(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_animal, data, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consulta.getIdAnimal());
            ps.setDate(2, Date.valueOf(consulta.getData())); // LocalDate → java.sql.Date
            ps.setString(3, consulta.getMotivo());
            ps.setBigDecimal(4, consulta.getValor());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta.setId(rs.getInt("id"));
            }
            return consulta;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage(), e);
        }
    }

    public List<Consulta> findByAnimalId(int idAnimal) {
        String sql = "SELECT * FROM consulta WHERE id_animal = ? ORDER BY data DESC";
        List<Consulta> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Consulta c = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_animal"),
                        rs.getDate("data").toLocalDate(), // java.sql.Date → LocalDate
                        rs.getString("motivo"),
                        rs.getBigDecimal("valor")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage(), e);
        }
        return lista;
    }

    public Optional<Consulta> findById(int id) {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_animal"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("motivo"),
                        rs.getBigDecimal("valor")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Consulta> findAll() {
        String sql = "SELECT * FROM consulta ORDER BY data DESC";
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Consulta(
                        rs.getInt("id"),
                        rs.getInt("id_animal"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("motivo"),
                        rs.getBigDecimal("valor")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return lista;
    }

    public void update(Consulta consulta) {
        String sql = "UPDATE consulta SET id_animal=?, data=?, motivo=?, valor=? WHERE id=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, consulta.getIdAnimal());
            ps.setDate(2, Date.valueOf(consulta.getData()));
            ps.setString(3, consulta.getMotivo());
            ps.setBigDecimal(4, consulta.getValor());
            ps.setInt(5, consulta.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage(), e);
        }
    }
}

