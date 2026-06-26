package br.edu.faculdade.controller;

import br.edu.faculdade.model.Matricula;
import br.edu.faculdade.service.MatriculaService;
import java.util.List;

public class MatriculaController {
    private final MatriculaService matriculaService = new MatriculaService();

    public void realizar(Matricula matricula) {
        try {
            matriculaService.realizarMatricula(matricula);
            System.out.println("Sucesso: Matrícula efetuada com sucesso!");
        } catch (Exception e) {
            System.out.println("REGRA DISPARADA: " + e.getMessage());
        }
    }

    public void exibirAlunosDoCurso(int idCurso) {
        System.out.println("\n--- Alunos Matriculados no Curso ID: " + idCurso + " ---");
        List<Matricula> lista = matriculaService.buscarPorCurso(idCurso);
        if (lista.isEmpty()) System.out.println("Nenhum aluno matriculado.");
        for (Matricula m : lista) {
            System.out.println("Aluno ID: " + m.getIdAluno() + " | Data: " + m.getDataMatricula() + " | Pago: R$" + m.getValor());
        }
    }

    public void exibirCursosDoAluno(int idAluno) {
        System.out.println("\n--- Cursos do Aluno ID: " + idAluno + " ---");
        List<Matricula> lista = matriculaService.buscarPorAluno(idAluno);
        if (lista.isEmpty()) System.out.println("Aluno não está em nenhum curso.");
        for (Matricula m : lista) {
            System.out.println("Curso ID: " + m.getIdCurso() + " | Data: " + m.getDataMatricula());
        }
    }
}