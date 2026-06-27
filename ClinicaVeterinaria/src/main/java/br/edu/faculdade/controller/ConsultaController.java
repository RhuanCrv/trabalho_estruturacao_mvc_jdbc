package br.edu.faculdade.controller;

import br.edu.faculdade.model.Consulta;
import br.edu.faculdade.service.ConsultaService;

import java.util.List;

public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController() {
        this.consultaService = new ConsultaService();
    }

    public Consulta registrar(Consulta consulta) {
        try {
            Consulta salva = consultaService.registrarConsulta(consulta);
            System.out.println("Consulta registrada com sucesso! ID: " + salva.getId());
            return salva;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao registrar consulta: " + e.getMessage());
            return null;
        }
    }

    public void exibirHistorico(int idAnimal) {
        try {
            List<Consulta> historico = consultaService.buscarHistoricoPorAnimal(idAnimal);
            System.out.println("\nHistórico de consultas do animal ID " + idAnimal + ":");
            if (historico.isEmpty()) {
                System.out.println("   Nenhuma consulta encontrada.");
            } else {
                historico.forEach(c -> System.out.printf(
                        "   [%d] Data: %s | Motivo: %s | Valor: R$ %.2f%n",
                        c.getId(), c.getData(), c.getMotivo(), c.getValor()
                ));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("erro " + e.getMessage());
        }
    }
}
