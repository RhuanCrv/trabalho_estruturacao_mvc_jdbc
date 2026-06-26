package br.edu.faculdade.controller;

import br.edu.faculdade.model.Curso;
import br.edu.faculdade.service.CursoService;

public class CursoController {
    private final CursoService cursoService = new CursoService();

    public Curso cadastrar(Curso curso) {
        try {
            return cursoService.cadastrarCurso(curso);
        } catch (Exception e) {
            System.out.println("LOG ERRO [Curso]: " + e.getMessage());
            return curso;
        }
    }
}