package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioDao {

    public Usuario getUsuario(String login){

        //Criar uma tabela no BD e fazer a consulta
        if (login.equals("gui@teste")){

            return new Usuario("gui@teste",
                    new BCryptPasswordEncoder().encode("123"),
                    "ADMIN");

        }else if(login.equals("alan@teste")){

            return new Usuario("alan@teste",
                    new BCryptPasswordEncoder().encode("456"),
                    "USER");

        }else{

            return null;

        }

    }

}
