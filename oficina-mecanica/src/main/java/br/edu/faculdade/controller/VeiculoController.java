package br.edu.faculdade.controller;

import br.edu.faculdade.model.Veiculo;
import br.edu.faculdade.repository.VeiculoRepository;

public class VeiculoController {
    private final VeiculoRepository repository = new VeiculoRepository();

    public Veiculo cadastrar(Veiculo veiculo) {
        System.out.println(" Cadastrando veículo: " + veiculo.getModelo() + " [" + veiculo.getPlaca() + "]");
        return repository.save(veiculo);
    }
}