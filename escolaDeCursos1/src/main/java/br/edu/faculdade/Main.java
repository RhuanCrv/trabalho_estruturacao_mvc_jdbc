package br.edu.faculdade;

import br.edu.faculdade.controller.AlunoController;
import br.edu.faculdade.controller.CursoController;
import br.edu.faculdade.controller.MatriculaController;
import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;
import br.edu.faculdade.model.Matricula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AlunoController alunoController = new AlunoController();
        CursoController cursoController = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        int opcao = 0;

        do {
            System.out.println("\n=============================================");
            System.out.println("      SISTEMA DE GESTÃO DE MATRÍCULAS        ");
            System.out.println("=============================================");
            System.out.println("1 - Cadastrar Novo Aluno");
            System.out.println("2 - Cadastrar Novo Curso");
            System.out.println("3 - Realizar Nova Matrícula");
            System.out.println("4 - Consultar Alunos Matriculados em um Curso");
            System.out.println("5 - Consultar Cursos de um Aluno");
            System.out.println("6 - Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome completo: ");
                    String nome = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();

                    Aluno novoAluno = new Aluno(nome, email, telefone);
                    Aluno alunoSalvo = alunoController.cadastrar(novoAluno);
                    if (alunoSalvo.getId() > 0) {
                        System.out.println("Sucesso: Aluno cadastrado com ID: " + alunoSalvo.getId());
                    }
                    break;

                case 2:
                    System.out.println("\n--- CADASTRO DE CURSO ---");
                    System.out.print("Nome do Curso: ");
                    String nomeCurso = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    int cargaHoraria;
                    int vagasTotais;
                    try {
                        System.out.print("Carga Horária (horas): ");
                        cargaHoraria = Integer.parseInt(scanner.nextLine());
                        System.out.print("Quantidade Total de Vagas: ");
                        vagasTotais = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: Carga horária e vagas devem ser números inteiros.");
                        break;
                    }

                    Curso novoCurso = new Curso(nomeCurso, descricao, cargaHoraria, vagasTotais);
                    Curso cursoSalvo = cursoController.cadastrar(novoCurso);
                    if (cursoSalvo.getId() > 0) {
                        System.out.println("Sucesso: Curso cadastrado com ID: " + cursoSalvo.getId());
                    }
                    break;

                case 3:
                    System.out.println("\n--- REALIZAR MATRÍCULA ---");
                    int idAluno, idCurso;
                    BigDecimal valorMatricula;

                    try {
                        System.out.print("ID do Aluno: ");
                        idAluno = Integer.parseInt(scanner.nextLine());
                        System.out.print("ID do Curso: ");
                        idCurso = Integer.parseInt(scanner.nextLine());
                        System.out.print("Valor da Matrícula (ex: 299.90): ");
                        valorMatricula = new BigDecimal(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Erro: Os IDs devem ser inteiros e o valor deve ser numérico.");
                        break;
                    }

                    Matricula novaMatricula = new Matricula(idAluno, idCurso, LocalDate.now(), valorMatricula);
                    matriculaController.realizar(novaMatricula);
                    break;

                case 4:
                    System.out.println("\n--- CONSULTA: ALUNOS POR CURSO ---");
                    try {
                        System.out.print("Digite o ID do Curso para pesquisar: ");
                        int pesquisaCurso = Integer.parseInt(scanner.nextLine());
                        matriculaController.exibirAlunosDoCurso(pesquisaCurso);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: O ID deve ser um número inteiro.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- CONSULTA: CURSOS POR ALUNO ---");
                    try {
                        System.out.print("Digite o ID do Aluno para pesquisar: ");
                        int pesquisaAluno = Integer.parseInt(scanner.nextLine());
                        matriculaController.exibirCursosDoAluno(pesquisaAluno);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro: O ID deve ser um número inteiro.");
                    }
                    break;

                case 6:
                    System.out.println("\nEncerrando o sistema... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um número de 1 a 6.");
                    break;
            }

        } while (opcao != 6);

        scanner.close();
    }
}