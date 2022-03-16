package com.example.livraria_backend.model;

public class Livro {

    private int codigo;
    private String nome;
    private String nomeAutor;
    private double valor;
    private Genero genero;
    private Editora editora;
    private boolean ativo;

    public Livro(){}

    public Livro(int codigo){
        this.codigo = codigo;
    }

    public Livro(int codigo, String nome, String nomeAutor, double valor, Genero genero, Editora editora, boolean ativo) {
        this.codigo = codigo;
        this.nome = nome;
        this.nomeAutor = nomeAutor;
        this.valor = valor;
        this.genero = genero;
        this.editora = editora;
        this.ativo = ativo;
    }

    public Livro(String nome, String nomeAutor, double valor, Genero genero, Editora editora, boolean ativo) {
        this.nome = nome;
        this.nomeAutor = nomeAutor;
        this.valor = valor;
        this.genero = genero;
        this.editora = editora;
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
