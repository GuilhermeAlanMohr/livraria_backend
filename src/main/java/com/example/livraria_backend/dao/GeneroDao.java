package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneroDao {

    public List<Genero> getGeneros(){

        ArrayList<Genero> generos = new ArrayList<Genero>();
        String sql;
        ResultSet resultSet;
        Statement statement;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codGenero,nomeGenero FROM genero";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Genero genero = new Genero();
                genero.setCodigo(resultSet.getInt("codGenero"));
                genero.setNome(resultSet.getString("nomeGenero"));

                generos.add(genero);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return generos;

    }

    public Genero getGenero(int codigo){

        Genero genero = new Genero();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;
        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codGenero,nomeGenero FROM genero WHERE genero.codGenero= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                genero.setCodigo(resultSet.getInt("codGenero"));
                genero.setNome(resultSet.getString("nomeGenero"));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return genero;

    }

}
