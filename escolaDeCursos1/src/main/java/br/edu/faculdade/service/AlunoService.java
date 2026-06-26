package br.edu.faculdade.service;

import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.repository.AlunoRepository;

public class AlunoService {
    private final AlunoRepository alunoRepository = new AlunoRepository();

    public Aluno cadastrarAluno(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        }
        if (aluno.getEmail() == null || !aluno.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        return alunoRepository.save(aluno);
    }
}