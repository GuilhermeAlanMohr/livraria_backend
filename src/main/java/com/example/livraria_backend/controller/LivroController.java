package com.example.livraria_backend.controller;

import com.example.livraria_backend.dao.GeneroDao;
import com.example.livraria_backend.dao.LivroDao;
import com.example.livraria_backend.model.Genero;
import com.example.livraria_backend.model.Livro;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("livro")
public class LivroController {

    @GetMapping("/generos")
    public List<Genero> getGeneros(){
        return new GeneroDao().getGeneros();
    }

    @GetMapping("/genero")
    public Genero getGenero(@RequestParam (value="codigo") int codigo){
        return new GeneroDao().getGenero(codigo);
    }

    @GetMapping("/livros")
    public List<Livro> getLivros(){
        return new LivroDao().getLivros();
    }

    @GetMapping("/livro")
    public Livro getLivro(@RequestParam(value="codigo") int codigo){
        return new LivroDao().getLivro(codigo);
    }

    @PostMapping("/cadastrar")
    public String cadastrarLivro(@RequestBody Livro livro){

        System.out.println("Cadastro de Livro");
        if(new LivroDao().cadastrar(livro).equals("OK")){
            return "Livro cadastrado com sucesso";
        }else {
            return "Erro ao cadastrar livro";
        }
    }

    @PutMapping("/alterar")
    public String alterarLivro(@RequestBody Livro livro){

        System.out.println("Alterar dados do livro: /n" +
                "Código: " + livro.getCodigo() +
                "Nome: " + livro.getNome());
        if(new LivroDao().alterar(livro).equals("OK")){
            return "Livro alterado com sucesso";
        }else{
            return "Erro ao alterar livro";
        }

    }

    @DeleteMapping("/deletar")
    public String deletarLivro(@RequestParam (value="codigo") int codigo){

        System.out.println("Deletar Livro: \n" +
                "Código do Livro: " + codigo);
        if(new LivroDao().deletar(new Livro(codigo)).equals("OK")){
            return "Livro deletado com sucesso";
        }else{
            return "Erro ao deletar livro";
        }

    }

}
