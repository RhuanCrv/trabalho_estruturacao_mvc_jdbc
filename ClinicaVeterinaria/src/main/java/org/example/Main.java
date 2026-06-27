package br.edu.faculdade;

import br.edu.faculdade.controller.TutorController;
import br.edu.faculdade.controller.AnimalController;
import br.edu.faculdade.controller.ConsultaController;
import br.edu.faculdade.model.Tutor;
import br.edu.faculdade.model.Animal;
import br.edu.faculdade.model.Consulta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        System.out.println("=== SIMULAÇÃO: Sistema de Clínica Veterinária ===\n");
        
        Tutor tutor = new Tutor("Maria Silva", "Rua das Flores, 123", "(44) 99999-1111");
        Tutor tutorSalvo = tutorController.cadastrar(tutor);

        Animal animal = new Animal("Rex", "Cachorro", "Labrador", tutorSalvo.getId());
        Animal animalSalvo = animalController.cadastrar(animal);

        Consulta consulta = new Consulta(
                animalSalvo.getId(),
                LocalDate.now(),
                "Vacinação anual e check-up geral",
                new BigDecimal("150.00")
        );
        consultaController.registrar(consulta);

        System.out.println("\n--- Testando regra: valor negativo ---");
        Consulta consultaInvalida = new Consulta(
                animalSalvo.getId(),
                LocalDate.now(),
                "Consulta inválida",
                new BigDecimal("-50.00")
        );
        consultaController.registrar(consultaInvalida); // Deve exibir erro

        consultaController.exibirHistorico(animalSalvo.getId());

        System.out.println("\n--- Animais do tutor " + tutorSalvo.getNome() + " ---");
        animalController.listarPorTutor(tutorSalvo.getId());

        System.out.println("\n=== FIM DA SIMULAÇÃO ===");
    }
}
