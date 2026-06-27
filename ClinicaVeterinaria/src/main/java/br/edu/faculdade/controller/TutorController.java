package br.edu.faculdade.controller;

import br.edu.faculdade.model.Tutor;
import br.edu.faculdade.service.TutorService;

public class TutorController {
    private final TutorService service = new TutorService();

    public Tutor cadastrar(Tutor tutor) {
        try {
            Tutor salvo = service.cadastrar(tutor);
            System.out.println("Tutor cadastrado com sucesso! ID: " + salvo.getId());
            return salvo;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar tutor: " + e.getMessage());
            return null;
        }
    }
}