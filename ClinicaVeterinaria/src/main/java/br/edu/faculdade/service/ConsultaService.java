package br.edu.faculdade.service;

import br.edu.faculdade.model.Animal;
import br.edu.faculdade.model.Consulta;
import br.edu.faculdade.repository.AnimalRepository;
import br.edu.faculdade.repository.ConsultaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;

    public ConsultaService() {
        this.consultaRepository = new ConsultaRepository();
        this.animalRepository = new AnimalRepository();
    }

    public Consulta registrarConsulta(Consulta consulta) {
        Optional<Animal> animal = animalRepository.findById(consulta.getIdAnimal());
        if (animal.isEmpty()) {
            throw new IllegalArgumentException(
                    "Animal com id " + consulta.getIdAnimal() + " não encontrado. Cadastre o animal primeiro."
            );
        }

        if (consulta.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");
        }

        return consultaRepository.save(consulta);
    }

    public List<Consulta> buscarHistoricoPorAnimal(int idAnimal) {
        Optional<Animal> animal = animalRepository.findById(idAnimal);
        if (animal.isEmpty()) {
            throw new IllegalArgumentException("Animal com id " + idAnimal + " não encontrado.");
        }
        return consultaRepository.findByAnimalId(idAnimal);
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }
}
