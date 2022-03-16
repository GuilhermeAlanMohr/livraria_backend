package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Filial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilialDao {

    public List<Filial> getFiliais(){

        ArrayList<Filial> filiais = new ArrayList<Filial>();
        String sql;
        Statement statement;
        ResultSet resultSet;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codfilial,nomefilial,enderecofilial,emailfilial,telefonefilial,codcidade,ativa FROM filial WHERE ativa = true";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Filial filial = new Filial();
                filial.setCodigo(resultSet.getInt("codfilial"));
                filial.setNome(resultSet.getString("nomefilial"));
                filial.setEndereco(resultSet.getString("enderecofilial"));
                filial.setEmail(resultSet.getString("emailfilial"));
                filial.setTelefone(resultSet.getString("telefonefilial"));
                filial.setCidade(new CidadeDao().getCidade(resultSet.getInt("codcidade")));
                filial.setAtiva(resultSet.getBoolean("ativa"));

                filiais.add(filial);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return filiais;

    }

    public Filial getFilial(int codigo){

        Filial filial = new Filial();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codfilial,nomefilial,enderecofilial,emailfilial,telefonefilial,codcidade,ativa FROM filial WHERE codfilial = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                filial.setCodigo(resultSet.getInt("codfilial"));
                filial.setNome(resultSet.getString("nomefilial"));
                filial.setEndereco(resultSet.getString("enderecofilial"));
                filial.setEmail(resultSet.getString("emailfilial"));
                filial.setTelefone(resultSet.getString("telefonefilial"));
                filial.setCidade(new CidadeDao().getCidade(resultSet.getInt("codcidade")));
                filial.setAtiva(resultSet.getBoolean("ativa"));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return filial;

    }

    public String cadastrar(Filial filial){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "INSERT INTO filial(nomefilial, emailfilial, enderecofilial, telefonefilial, codcidade, ativa) "
                    + "VALUES (?, ?, ?, ?, ?, true)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, filial.getNome());
            preparedStatement.setString(2, filial.getEmail());
            preparedStatement.setString(3, filial.getEndereco());
            preparedStatement.setString(4, filial.getTelefone());
            preparedStatement.setInt(5, filial.getCidade().getCodigo());

            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            if(resultSet.getInt(1)>0){

                status = "OK";
                connection.commit();

            }else{

                status = "Erro";
                connection.rollback();

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return status;

    }

    public String alterar(Filial filial){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE filial SET nomefilial = ?, emailfilial = ?, enderecofilial = ?, telefonefilial = ?,"
                    + " codcidade = ? WHERE codfilial = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, filial.getNome());
            preparedStatement.setString(2, filial.getEmail());
            preparedStatement.setString(3, filial.getEndereco());
            preparedStatement.setString(4, filial.getTelefone());
            preparedStatement.setInt(5, filial.getCidade().getCodigo());
            preparedStatement.setInt(6, filial.getCodigo());

            preparedStatement.executeUpdate();
            if(preparedStatement.getUpdateCount() > 0){

                status = "OK";
                connection.commit();

            }else{

                status = "Erro";
                connection.rollback();

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return status;

    }

    public String deletar(Filial filial){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            status = new EstoqueDao().deletarFilial(filial,connection);
            if(status.equals("OK")){

                sql = "UPDATE filial SET ativa = false WHERE codfilial = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,filial.getCodigo());

                preparedStatement.executeUpdate();
                if(preparedStatement.getUpdateCount() > 0){

                    status = "OK";
                    connection.commit();

                }else{

                    status = "Erro";
                    connection.rollback();

                }

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return status;

    }

}
