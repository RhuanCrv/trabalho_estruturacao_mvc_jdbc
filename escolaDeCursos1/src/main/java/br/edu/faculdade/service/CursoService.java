package br.edu.faculdade.service;

import br.edu.faculdade.model.Curso;
import br.edu.faculdade.repository.CursoRepository;

public class CursoService {
    private final CursoRepository cursoRepository = new CursoRepository();

    public Curso cadastrarCurso(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        }
        if (curso.getVagasTotais() <= 0) {
            throw new IllegalArgumentException("O curso deve possuir pelo menos 1 vaga.");
        }
        return cursoRepository.save(curso);
    }
}