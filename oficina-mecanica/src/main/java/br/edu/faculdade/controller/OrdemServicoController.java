package br.edu.faculdade.controller;

import br.edu.faculdade.model.OrdemServico;
import br.edu.faculdade.service.OrdemServicoService;
import java.util.List;

public class OrdemServicoController {
    private final OrdemServicoService service = new OrdemServicoService();

    public OrdemServico abrir(OrdemServico os) {
        try {
            OrdemServico novaOs = service.abrirOrdem(os);
            System.out.println(" OS #" + novaOs.getId() + " aberta com sucesso!");
            return novaOs;
        } catch (IllegalArgumentException e) {
            System.out.println(" ERRO DE VALIDAÇÃO: " + e.getMessage());
            return os;
        }
    }

    public void concluir(int idOrdem) {
        try {
            service.concluirOrdem(idOrdem);
        } catch (IllegalArgumentException e) {
            System.out.println(" ERRO: " + e.getMessage());
        }
    }

    public void exibirHistorico(int idVeiculo) {
        System.out.println("\n -Histórico de Manutenções do Veículo #" + idVeiculo + " ---");
        List<OrdemServico> historico = service.buscarHistoricoPorVeiculo(idVeiculo);
        if (historico.isEmpty()) {
            System.out.println("Nenhuma ordem encontrada para este veículo.");
        } else {
            for (OrdemServico os : historico) {
                System.out.println("- OS #" + os.getId() + " | Descrição: " + os.getDescricao() + " | Valor: R$ " + os.getValor() + " | Status: " + os.getStatus());
            }
        }
    }
}