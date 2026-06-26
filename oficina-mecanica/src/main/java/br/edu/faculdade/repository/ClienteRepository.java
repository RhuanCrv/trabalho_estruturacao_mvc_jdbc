package br.edu.faculdade.repository;

import br.edu.faculdade.model.Cliente;
import br.edu.faculdade.util.Conexao;
import java.sql.*;

public class ClienteRepository {
    public Cliente save(Cliente c) {
        String sql = "INSERT INTO cliente (nome, telefone) VALUES (?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) c.setId(rs.getInt("id"));
            return c;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}