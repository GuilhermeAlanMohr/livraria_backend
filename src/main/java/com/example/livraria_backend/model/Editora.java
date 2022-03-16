package com.example.livraria_backend.model;

public class Editora {

    private int codigo;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private Cidade cidade;
    private boolean ativa;

    public Editora(){}

    public Editora(int codigo){
        this.codigo = codigo;
    }

    public Editora(int codigo, String nome, String telefone, String email, String endereco, Cidade cidade, boolean ativa) {
        this.codigo = codigo;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.cidade = cidade;
        this.ativa = ativa;
    }

    public Editora( String nome, String telefone, String email, String endereco, Cidade cidade, boolean ativa) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.cidade = cidade;
        this.ativa = ativa;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

}
