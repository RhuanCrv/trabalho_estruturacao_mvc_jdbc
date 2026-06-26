package br.edu.faculdade.controller;

import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.service.AlunoService;

public class AlunoController {
    private final AlunoService alunoService = new AlunoService();

    public Aluno cadastrar(Aluno aluno) {
        try {
            return alunoService.cadastrarAluno(aluno);
        } catch (Exception e) {
            System.out.println("LOG ERRO [Aluno]: " + e.getMessage());
            return aluno;
        }
    }
}