package com.example.livraria_backend.model;

public class Estado {

    private String sigla;
    private String nome;

    public Estado(){}

    public Estado(String sigla) {
        this.sigla = sigla;
    }

    public Estado(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
