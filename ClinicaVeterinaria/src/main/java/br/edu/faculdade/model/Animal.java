package br.edu.faculdade.model;

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idTutor;

    public Animal(String nome, String especie, String raca, int idTutor) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idTutor = idTutor;
    }

    public Animal(int id, String nome, String especie, String raca, int idTutor) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idTutor = idTutor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public int getIdTutor() { return idTutor; }
}