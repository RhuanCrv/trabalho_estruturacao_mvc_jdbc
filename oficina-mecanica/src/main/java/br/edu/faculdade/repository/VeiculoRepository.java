package br.edu.faculdade.repository;

import br.edu.faculdade.model.Veiculo;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.Optional;

public class VeiculoRepository {
    public Veiculo save(Veiculo v) {
        String sql = "INSERT INTO veiculo (placa, modelo, ano, id_cliente) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getAno());
            ps.setInt(4, v.getIdCliente());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) v.setId(rs.getInt("id"));
            return v;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Optional<Veiculo> findById(int id) {
        String sql = "SELECT * FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Veiculo(rs.getInt("id"), rs.getString("placa"), rs.getString("modelo"), rs.getInt("ano"), rs.getInt("id_cliente")));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }
}