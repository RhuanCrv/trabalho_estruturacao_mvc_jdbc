package br.edu.faculdade.controller;

import br.edu.faculdade.model.Animal;
import br.edu.faculdade.service.AnimalService;
import java.util.List;

public class AnimalController {
    private final AnimalService service = new AnimalService();

    public Animal cadastrar(Animal animal) {
        try {
            Animal salvo = service.cadastrar(animal);
            System.out.println("Animal cadastrado com sucesso! ID: " + salvo.getId());
            return salvo;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar animal: " + e.getMessage());
            return null;
        }
    }

    public void listarPorTutor(int idTutor) {
        List<Animal> animais = service.buscarPorTutor(idTutor);
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal encontrado para este tutor.");
        } else {
            animais.forEach(a -> System.out.printf("   [%d] Nome: %s | Espécie: %s | Raça: %s%n", a.getId(), a.getNome(), a.getEspecie(), a.getRaca()));
        }
    }
}