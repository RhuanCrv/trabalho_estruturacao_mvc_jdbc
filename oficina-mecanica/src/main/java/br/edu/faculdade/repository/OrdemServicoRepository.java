package br.edu.faculdade.repository;

import br.edu.faculdade.model.OrdemServico;
import br.edu.faculdade.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    public OrdemServico save(OrdemServico os) {
        String sql = "INSERT INTO ordem_servico (id_veiculo, descricao, valor, status) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, os.getIdVeiculo());
            ps.setString(2, os.getDescricao());
            ps.setBigDecimal(3, os.getValor());
            ps.setString(4, os.getStatus());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) os.setId(rs.getInt("id"));
            return os;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar OS: " + e.getMessage(), e);
        }
    }

    public List<OrdemServico> findByVeiculoId(int idVeiculo) {
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo = ? ORDER BY id DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVeiculo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new OrdemServico(
                        rs.getInt("id"),
                        rs.getInt("id_veiculo"),
                        rs.getString("descricao"),
                        rs.getBigDecimal("valor"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar OSs: " + e.getMessage(), e);
        }
        return lista;
    }

    public Optional<OrdemServico> findById(int id) {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new OrdemServico(
                        rs.getInt("id"), rs.getInt("id_veiculo"),
                        rs.getString("descricao"), rs.getBigDecimal("valor"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar OS: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public void update(OrdemServico os) {
        String sql = "UPDATE ordem_servico SET id_veiculo=?, descricao=?, valor=?, status=? WHERE id=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, os.getIdVeiculo());
            ps.setString(2, os.getDescricao());
            ps.setBigDecimal(3, os.getValor());
            ps.setString(4, os.getStatus());
            ps.setInt(5, os.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar OS: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar OS: " + e.getMessage(), e);
        }
    }

    public List<OrdemServico> findAll() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new OrdemServico(
                        rs.getInt("id"), rs.getInt("id_veiculo"),
                        rs.getString("descricao"), rs.getBigDecimal("valor"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar OSs: " + e.getMessage(), e);
        }
        return lista;
    }
}
