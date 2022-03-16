package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoDao {

    public List<Estado> getEstados() {

        ArrayList<Estado> estados = new ArrayList<Estado>();
        String sql;
        Statement statement;
        ResultSet resultSet;
        String status;

        try (Connection connection = new ConectaDB().getConexao()) {

            sql = "SELECT sigla,nomeEstado FROM estado";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Estado estado = new Estado();
                estado.setSigla(resultSet.getString("sigla"));
                estado.setNome(resultSet.getString("nomeEstado"));
                estados.add(estado);

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return estados;

    }

    public Estado getEstado(String sigla) {

        Estado estado = new Estado();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;

        try (Connection connection = new ConectaDB().getConexao()) {

            sql = "SELECT sigla,nomeEstado FROM estado WHERE estado.sigla = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sigla);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                estado.setSigla(resultSet.getString("sigla"));
                estado.setNome(resultSet.getString("nomeEstado"));

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return estado;

    }

}