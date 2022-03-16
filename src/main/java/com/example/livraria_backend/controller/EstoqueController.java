package com.example.livraria_backend.controller;

import com.example.livraria_backend.dao.EstoqueDao;
import com.example.livraria_backend.model.Estoque;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estoque")
public class EstoqueController {

    @GetMapping("estoques")
    public List<Estoque> getEstoques(){
        return new EstoqueDao().getEstoques();
    }

    @PostMapping("cadastrar")
    public String cadastrarEstoque(@RequestBody Estoque estoque){
        System.out.println("Cadastrar Estoque");
        if(new EstoqueDao().cadastrar(estoque).equals("OK")){
            return "Item do Estoque cadastrado com sucesso";
        }else{
            return "Erro ao cadastrar Item do Estoque";
        }
    }

    @GetMapping("estoque")
    public Estoque getEstoque(@RequestParam(value = "codigo") int codigo){
        return new EstoqueDao().getEstoque(codigo);
    }

    @PutMapping("alterar")
    public String alterarEstoque(@RequestBody Estoque estoque){
        System.out.println("Alterar Estoque: \n" +
                "Código Estoque: " + estoque.getCodigo());
        if(new EstoqueDao().alterar(estoque).equals("OK")){
            return "Estoque alterado com sucesso";
        }else{
            return "Erro ao alterar estoque";
        }

    }

    @DeleteMapping("deletar")
    public String deletarEstoque(@RequestParam (value="codigo") int codigo){
        System.out.println("Deletar Estoque: \n" +
                "Código Estoque: " + codigo);
        if(new EstoqueDao().deletar(new Estoque(codigo)).equals("OK")){
            return "Estoque deletado com sucesso";
        }else{
            return "Erro ao deletar estoque";
        }

    }

}
