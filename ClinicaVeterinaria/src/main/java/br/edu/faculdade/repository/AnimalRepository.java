package br.edu.faculdade.repository;

import br.edu.faculdade.model.Animal;
import br.edu.faculdade.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {
    public Animal save(Animal animal) {
        String sql = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, animal.getNome());
            ps.setString(2, animal.getEspecie());
            ps.setString(3, animal.getRaca());
            ps.setInt(4, animal.getIdTutor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) animal.setId(rs.getInt("id"));
            return animal;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage(), e);
        }
    }

    public Optional<Animal> findById(int id) {
        String sql = "SELECT * FROM animal WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Animal(rs.getInt("id"), rs.getString("nome"), rs.getString("especie"), rs.getString("raca"), rs.getInt("id_tutor")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Animal> findByTutorId(int idTutor) {
        String sql = "SELECT * FROM animal WHERE id_tutor = ?";
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTutor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Animal(rs.getInt("id"), rs.getString("nome"), rs.getString("especie"), rs.getString("raca"), rs.getInt("id_tutor")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animais: " + e.getMessage(), e);
        }
        return lista;
    }
}