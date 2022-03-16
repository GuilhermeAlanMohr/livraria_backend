package com.example.livraria_backend.controller;

import com.example.livraria_backend.dao.CidadeDao;
import com.example.livraria_backend.dao.EstadoDao;
import com.example.livraria_backend.dao.FilialDao;
import com.example.livraria_backend.model.Cidade;
import com.example.livraria_backend.model.Estado;
import com.example.livraria_backend.model.Filial;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filial")
public class FilialController {

    @GetMapping("filiais")
    public List<Filial> getFiliais(){
        return new FilialDao().getFiliais();
    }

    @GetMapping("cidades")
    public List<Cidade> getCidades(){
        return new CidadeDao().getCidades();
    }

    @GetMapping("cidade")
    public Cidade getCidade(@RequestParam (value="codigo") int codigo){
        return new CidadeDao().getCidade(codigo);
    }

    @GetMapping("estados")
    public List<Estado> getEstados(){
        return new EstadoDao().getEstados();
    }

    @GetMapping("filial")
    public Filial getFilial(@RequestParam (value="codigo") int codigo){
        return new FilialDao().getFilial(codigo);
    }

    @PostMapping("cadastrar")
    public String cadastrarFilial(@RequestBody Filial filial){
        System.out.println("Cadastro de Filial");
        if(new FilialDao().cadastrar(filial).equals("OK")){
            return "Filial cadastrada com sucesso";
        }else{
            return "Erro ao cadastrar filial";
        }
    }

    @PutMapping("alterar")
    public String alterarFilial(@RequestBody Filial filial){
        System.out.println("Alterar Filial: \n" +
                "Código Filial: " + filial.getCodigo());
        if(new FilialDao().alterar(filial).equals("OK")){
            return "Filial alterado com sucesso";
        }else{
            return "Erro ao alterar filial";
        }

    }

    @DeleteMapping("deletar")
    public String deletarFilial(@RequestParam (value="codigo") int codigo){
        System.out.println("Deletar Filial: \n" +
                "Código Filial: " + codigo);
        if(new FilialDao().deletar(new Filial(codigo)).equals("OK")){
            return "Filial deletado com sucesso";
        }else{
            return "Erro ao deletar filial";
        }

    }

}
