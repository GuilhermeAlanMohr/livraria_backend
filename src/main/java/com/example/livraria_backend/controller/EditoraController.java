package com.example.livraria_backend.controller;

import com.example.livraria_backend.dao.EditoraDao;
import com.example.livraria_backend.model.Editora;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("editora")
public class EditoraController {

    @GetMapping("editoras")
    public List<Editora> getEditoras(){
        return new EditoraDao().getEditoras();
    }

    @PostMapping("cadastrar")
    public String cadastrarEditora(@RequestBody Editora editora){
        System.out.println("Cadastrar Editora");
        if(new EditoraDao().cadastrar(editora).equals("OK")){
            return "Editora cadastrada com sucesso";
        }else{
            return "Erro ao cadastrar Editora";
        }
    }

    @GetMapping("editora")
    public Editora cadastrarEditora(@RequestParam(value = "codigo") int codigo){
        return new EditoraDao().getEditora(codigo);
    }

    @PutMapping("alterar")
    public String alterarEditora(@RequestBody Editora editora){
        System.out.println("Alterar Editora: \n" +
                "Código: " + editora.getCodigo());
        if(new EditoraDao().alterar(editora).equals("OK")){
            return "Editora alterado com sucesso";
        }else{
            return "Erro ao alterar editora";
        }

    }

    @DeleteMapping("deletar")
    public String deletarEditora(@RequestParam (value="codigo") int codigo){
        System.out.println("Deletar Editora: \n" +
                "Código: " + codigo);
        if(new EditoraDao().deletar(new Editora(codigo)).equals("OK")){
            return "Editora deletado com sucesso";
        }else{
            return "Erro ao deletar editora";
        }

    }

}
