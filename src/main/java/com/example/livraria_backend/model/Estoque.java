package com.example.livraria_backend.model;

public class Estoque {

    private int codigo;
    private Livro livro;
    private Filial filial;
    private int quantidade;
    private boolean ativo;

    public Estoque(){}

    public Estoque(int codigo){
        this.codigo = codigo;
    }

    public Estoque(int codigo, Livro livro, Filial filial, int quantidade, boolean ativo) {
        this.codigo = codigo;
        this.livro = livro;
        this.filial = filial;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }

    public Estoque(Livro livro, Filial filial, int quantidade, boolean ativo) {
        this.livro = livro;
        this.filial = filial;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
