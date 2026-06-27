package br.edu.faculdade.service;

import br.edu.faculdade.model.Tutor;
import br.edu.faculdade.repository.TutorRepository;

public class TutorService {
    private final TutorRepository repository = new TutorRepository();

    public Tutor cadastrar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank() ||
                tutor.getEndereco() == null || tutor.getEndereco().isBlank() ||
                tutor.getTelefone() == null || tutor.getTelefone().isBlank()) {
            throw new IllegalArgumentException("Nome, endereço e telefone são obrigatórios.");
        }
        return repository.save(tutor);
    }
}