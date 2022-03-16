package com.example.livraria_backend.security;

import com.example.livraria_backend.dao.UsuarioDao;
import com.example.livraria_backend.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceCustomizado implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username no UserDetailsService: " + username);

        Usuario usuario = new UsuarioDao().getUsuario(username);
        System.out.println("Permissões do Usuário no UserDetailsService: " + usuario.getPermissao());

        if(usuario == null) {
            throw new UsernameNotFoundException("Usuário ou senha inválidos ");
        }else{
            UserDetails user = User.withUsername(usuario.getLogin())
                    .password(usuario.getSenha())
                    .authorities(usuario.getPermissao()).build();
            return user;
        }

    }

}
