package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Cidade;
import com.example.livraria_backend.model.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CidadeDao {

    public List<Cidade> getCidades(){

        ArrayList<Cidade> cidades = new ArrayList<Cidade>();
        String sql;
        Statement statement;
        ResultSet resultSet;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codCidade,nomeCidade,sigla FROM cidade";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            Estado estado;

            while(resultSet.next()){

                Cidade cidade = new Cidade();
                cidade.setCodigo(resultSet.getInt("codCidade"));
                cidade.setNome(resultSet.getString("nomeCidade"));
                cidade.setEstado(new EstadoDao().getEstado(resultSet.getString("sigla")));

                cidades.add(cidade);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return cidades;

    }

    public Cidade getCidade(int codigo){

        Cidade cidade = new Cidade();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codCidade,nomeCidade,sigla FROM cidade WHERE codcidade = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                cidade.setCodigo(resultSet.getInt("codCidade"));
                cidade.setNome(resultSet.getString("nomeCidade"));
                cidade.setEstado(new EstadoDao().getEstado(resultSet.getString("sigla")));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return cidade;

    }

    public List<Cidade> getCidadesEstados(String sigla){

        ArrayList<Cidade> cidades = new ArrayList<Cidade>();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codCidade, nomeCidade, sigla FROM cidade WHERE sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sigla);
            resultSet = preparedStatement.executeQuery();
            Estado estado = new EstadoDao().getEstado("sigla");

            while(resultSet.next()){

                Cidade cidade = new Cidade();
                cidade.setCodigo(resultSet.getInt("codCidade"));
                cidade.setNome(resultSet.getString("nomeCidade"));
                cidade.setEstado(estado);
                cidades.add(cidade);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return cidades;

    }

}
