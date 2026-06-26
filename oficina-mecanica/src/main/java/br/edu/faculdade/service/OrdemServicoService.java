package br.edu.faculdade.service;

import br.edu.faculdade.model.OrdemServico;
import br.edu.faculdade.model.Veiculo;
import br.edu.faculdade.repository.OrdemServicoRepository;
import br.edu.faculdade.repository.VeiculoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrdemServicoService {

    private final OrdemServicoRepository osRepository;
    private final VeiculoRepository veiculoRepository;

    public OrdemServicoService() {
        this.osRepository = new OrdemServicoRepository();
        this.veiculoRepository = new VeiculoRepository();
    }

    public OrdemServico abrirOrdem(OrdemServico os) {

        Optional<Veiculo> veiculo = veiculoRepository.findById(os.getIdVeiculo());
        if (veiculo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Veículo com id " + os.getIdVeiculo() + " não cadastrado. Cadastre o veículo primeiro."
            );
        }

        if (os.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        }

        return osRepository.save(os);
    }

    public void concluirOrdem(int idOrdem) {
        Optional<OrdemServico> os = osRepository.findById(idOrdem);
        if (os.isEmpty()) {
            throw new IllegalArgumentException("Ordem de serviço com id " + idOrdem + " não encontrada.");
        }
        OrdemServico ordemAtual = os.get();
        ordemAtual.setStatus("CONCLUIDA");
        osRepository.update(ordemAtual);
        System.out.println(" Ordem #" + idOrdem + " marcada como CONCLUIDA.");
    }

    public List<OrdemServico> buscarHistoricoPorVeiculo(int idVeiculo) {
        return osRepository.findByVeiculoId(idVeiculo);
    }
}
