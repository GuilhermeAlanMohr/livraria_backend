package com.example.livraria_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /*
    public DaoAuthenticationProvider authProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;

    }
    */
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("************** WebSecurityConfig");
        auth.authenticationProvider(this.authProvider());
    }
    */

    @Autowired
    public void configureAutenticacao(AuthenticationManagerBuilder auth) throws Exception{

        System.out.println("... configurando o AuthenticationManager ****");
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Bean
    public FiltroAutenticacao filtroAutenticacao() throws Exception{
        return new FiltroAutenticacao();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //.authenticationProvider(this.authProvider())
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/livro/livros").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/editora/editoras").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/filial/filiais").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/estoque/estoques").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/livro/livro").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/editora/editora").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/filial/filial").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/estoque/estoque").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/livro/cadastrar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/editora/cadastrar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/filial/cadastrar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/estoque/cadastrar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/livro/alterar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/editora/alterar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/filial/alterar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/estoque/alterar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/livro/deletar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/editora/deletar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/filial/deletar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/estoque/deletar").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/livro/generos").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/filial/cidades").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/livro/genero").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/filial/cidade").hasAnyAuthority("ADMIN","USER")
                .antMatchers(HttpMethod.GET, "/filial/estados").hasAnyAuthority("ADMIN","USER");
                //.and().formLogin();

        http.addFilterBefore(this.filtroAutenticacao(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsFilter corsFilter(){
        System.out.println("*** Configurando o CORS **** ");
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);

    }

}
