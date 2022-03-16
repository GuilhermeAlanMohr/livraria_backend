package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditoraDao {

    public List<Editora> getEditoras(){

        ArrayList<Editora> editoras = new ArrayList<Editora>();
        String sql;
        Statement statement;
        ResultSet resultSet;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEditora,nomeEditora,enderecoEditora,emailEditora,telefoneEditora,ativa,codCidade FROM editora";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Editora editora = new Editora();
                editora.setCodigo(resultSet.getInt("codEditora"));
                editora.setNome(resultSet.getString("nomeEditora"));
                editora.setEndereco(resultSet.getString("enderecoEditora"));
                editora.setEmail(resultSet.getString("emailEditora"));
                editora.setTelefone(resultSet.getString("telefoneEditora"));
                editora.setAtiva(resultSet.getBoolean("ativa"));
                editora.setCidade(new CidadeDao().getCidade(resultSet.getInt("codCidade")));
                editoras.add(editora);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return editoras;

    }

    public Editora getEditora(int codigo){

        Editora editora = new Editora();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEditora,nomeEditora,enderecoEditora,emailEditora,telefoneEditora,ativa,codCidade FROM editora WHERE codEditora = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                editora.setCodigo(resultSet.getInt("codEditora"));
                editora.setNome(resultSet.getString("nomeEditora"));
                editora.setEndereco(resultSet.getString("enderecoEditora"));
                editora.setEmail(resultSet.getString("emailEditora"));
                editora.setTelefone(resultSet.getString("telefoneEditora"));
                editora.setAtiva(resultSet.getBoolean("ativa"));
                editora.setCidade(new CidadeDao().getCidade(resultSet.getInt("codCidade")));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return editora;

    }

    public String cadastrar(Editora editora){

        String sql;
        Statement statement;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "INSERT INTO editora(nomeeditora, emaileditora, enderecoeditora, telefoneeditora, ativa, codcidade) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, editora.getNome());
            preparedStatement.setString(2, editora.getEmail());
            preparedStatement.setString(3, editora.getEndereco());
            preparedStatement.setString(4, editora.getTelefone());
            preparedStatement.setBoolean(5, editora.isAtiva());
            preparedStatement.setInt(6, editora.getCidade().getCodigo());

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

    public String alterar(Editora editora){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE editora SET nomeeditora = ?, emaileditora = ?, enderecoeditora = ?, telefoneeditora = ?,"
                    + " codcidade = ? WHERE codeditora = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, editora.getNome());
            preparedStatement.setString(2, editora.getEmail());
            preparedStatement.setString(3, editora.getEndereco());
            preparedStatement.setString(3, editora.getEndereco());
            preparedStatement.setString(3, editora.getEndereco());
            preparedStatement.setString(4, editora.getTelefone());
            preparedStatement.setInt(5, editora.getCidade().getCodigo());
            preparedStatement.setInt(6, editora.getCodigo());

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

    public String deletar(Editora editora){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE editora SET ativa = false WHERE codeditora = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, editora.getCodigo());

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

}
