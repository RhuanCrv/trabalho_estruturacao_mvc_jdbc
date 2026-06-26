package br.edu.faculdade.controller;

import br.edu.faculdade.model.Cliente;
import br.edu.faculdade.repository.ClienteRepository;

public class ClienteController {
    private final ClienteRepository repository = new ClienteRepository();

    public Cliente cadastrar(Cliente cliente) {
        System.out.println(" Cadastrando cliente: " + cliente.getNome());
        return repository.save(cliente);
    }
}