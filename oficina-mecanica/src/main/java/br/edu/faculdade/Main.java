package br.edu.faculdade;

import br.edu.faculdade.controller.ClienteController;
import br.edu.faculdade.controller.VeiculoController;
import br.edu.faculdade.controller.OrdemServicoController;
import br.edu.faculdade.model.Cliente;
import br.edu.faculdade.model.Veiculo;
import br.edu.faculdade.model.OrdemServico;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController osController = new OrdemServicoController();

        int opcao = 0;

        while (opcao != 6) {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA DE OFICINA MECÂNICA      ");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Cadastrar Veículo");
            System.out.println("3 - Abrir Ordem de Serviço (OS)");
            System.out.println("4 - Concluir Ordem de Serviço");
            System.out.println("5 - Consultar Histórico do Veículo");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Por favor, digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRO DE CLIENTE ---");
                    System.out.print("Nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone do cliente: ");
                    String telefone = scanner.nextLine();

                    if (nome.trim().isEmpty() || telefone.trim().isEmpty()) {
                        System.out.println(" Erro: Nome e telefone são obrigatórios.");
                    } else {
                        Cliente novoCliente = new Cliente(nome, telefone);
                        clienteController.cadastrar(novoCliente);
                    }
                    break;

                case 2:
                    System.out.println("\n--- CADASTRO DE VEÍCULO ---");
                    System.out.print("Placa do veículo (Ex: ABC-1234): ");
                    String placa = scanner.nextLine().toUpperCase();
                    System.out.print("Modelo do veículo (Ex: Fiat Uno): ");
                    String modelo = scanner.nextLine();
                    System.out.print("Ano do veículo: ");
                    int ano;
                    try {
                        ano = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" Ano inválido.");
                        break;
                    }
                    System.out.print("ID do Cliente proprietário: ");
                    int idCliente;
                    try {
                        idCliente = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID do cliente inválido.");
                        break;
                    }

                    Veiculo novoVeiculo = new Veiculo(placa, modelo, ano, idCliente);
                    veiculoController.cadastrar(novoVeiculo);
                    break;

                case 3:
                    System.out.println("\n--- ABRIR ORDEM DE SERVIÇO ---");
                    System.out.print("ID do Veículo: ");
                    int idVeiculo;
                    try {
                        idVeiculo = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID do veículo inválido.");
                        break;
                    }
                    System.out.print("Descrição do serviço: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Valor do serviço (Use ponto para centavos, ex: 250.50): ");
                    BigDecimal valor;
                    try {
                        valor = new BigDecimal(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" Valor inválido.");
                        break;
                    }

                    OrdemServico novaOs = new OrdemServico(idVeiculo, descricao, valor);
                    osController.abrir(novaOs);
                    break;

                case 4:
                    System.out.println("\n--- CONCLUIR ORDEM DE SERVIÇO ---");
                    System.out.print("Digite o ID da OS que deseja concluir: ");
                    int idOs;
                    try {
                        idOs = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID inválido.");
                        break;
                    }
                    osController.concluir(idOs);
                    break;

                case 5:
                    System.out.println("\n--- HISTÓRICO DO VEÍCULO ---");
                    System.out.print("Digite o ID do Veículo para buscar as OSs: ");
                    int idVeiculoBusca;
                    try {
                        idVeiculoBusca = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(" ID inválido.");
                        break;
                    }
                    osController.exibirHistorico(idVeiculoBusca);
                    break;

                case 6:
                    System.out.println("\n👋 Saindo do sistema... Até logo!");
                    break;

                default:
                    System.out.println("❌ Opção inválida! Escolha um número de 1 a 6.");
                    break;
            }
        }
        scanner.close();
    }
}