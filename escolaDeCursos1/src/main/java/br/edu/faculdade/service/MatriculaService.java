package br.edu.faculdade.service;

import br.edu.faculdade.model.Aluno;
import br.edu.faculdade.model.Curso;
import br.edu.faculdade.model.Matricula;
import br.edu.faculdade.repository.AlunoRepository;
import br.edu.faculdade.repository.CursoRepository;
import br.edu.faculdade.repository.MatriculaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService() {
        this.matriculaRepository = new MatriculaRepository();
        this.alunoRepository = new AlunoRepository();
        this.cursoRepository = new CursoRepository();
    }

    public Matricula realizarMatricula(Matricula matricula) {
        Optional<Aluno> aluno = alunoRepository.findById(matricula.getIdAluno());
        if (aluno.isEmpty()) {
            throw new IllegalArgumentException("Aluno não encontrado. Cadastre o aluno primeiro.");
        }

        Optional<Curso> curso = cursoRepository.findById(matricula.getIdCurso());
        if (curso.isEmpty()) {
            throw new IllegalArgumentException("Curso não encontrado. Cadastre o curso primeiro.");
        }

        Curso cursoAtual = curso.get();

        if (matriculaRepository.existeMatricula(matricula.getIdAluno(), matricula.getIdCurso())) {
            throw new IllegalArgumentException(
                    "O aluno " + aluno.get().getNome() + " já está matriculado no curso " + cursoAtual.getNome() + "."
            );
        }

        if (cursoAtual.getVagasDisponiveis() <= 0) {
            throw new IllegalArgumentException(
                    "Curso " + cursoAtual.getNome() + " não tem vagas disponíveis."
            );
        }

        if (matricula.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor da matrícula não pode ser negativo.");
        }

        Matricula salva = matriculaRepository.save(matricula);
        cursoRepository.decrementarVaga(cursoAtual.getId());

        return salva;
    }

    public List<Matricula> buscarPorAluno(int idAluno) {
        return matriculaRepository.findByAlunoId(idAluno);
    }

    public List<Matricula> buscarPorCurso(int idCurso) {
        return matriculaRepository.findByCursoId(idCurso);
    }
}