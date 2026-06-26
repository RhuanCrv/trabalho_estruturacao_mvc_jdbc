package br.edu.faculdade.model;

import java.math.BigDecimal;

public class OrdemServico {
    private int id;
    private int idVeiculo;
    private String descricao;
    private BigDecimal valor;
    private String status;

    public OrdemServico(int idVeiculo, String descricao, BigDecimal valor) {
        this.idVeiculo = idVeiculo;
        this.descricao = descricao;
        this.valor = valor;
        this.status = "ABERTA";
    }

    public OrdemServico(int id, int idVeiculo, String descricao, BigDecimal valor, String status) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVeiculo() { return idVeiculo; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
