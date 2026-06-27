package br.edu.faculdade.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Consulta {
    private int id;
    private int idAnimal;
    private LocalDate data;
    private String motivo;
    private BigDecimal valor;

    public Consulta(int idAnimal, LocalDate data, String motivo, BigDecimal valor) {
        this.idAnimal = idAnimal;
        this.data = data;
        this.motivo = motivo;
        this.valor = valor;
    }

    public Consulta(int id, int idAnimal, LocalDate data, String motivo, BigDecimal valor) {
        this.id = id;
        this.idAnimal = idAnimal;
        this.data = data;
        this.motivo = motivo;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAnimal() { return idAnimal; }
    public LocalDate getData() { return data; }
    public String getMotivo() { return motivo; }
    public BigDecimal getValor() { return valor; }
}
