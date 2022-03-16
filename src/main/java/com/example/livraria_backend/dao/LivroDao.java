package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDao {

    public List<Livro> getLivros(){

        ArrayList<Livro> livros = new ArrayList<Livro>();
        String sql;
        Statement statement;
        ResultSet resultSet;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codLivro,nomeLivro,nomeAutor,valor,codGenero,codEditora,ativo FROM livro WHERE ativo = true";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Livro livro = new Livro();
                livro.setCodigo(resultSet.getInt("codLivro"));
                livro.setNome(resultSet.getString("nomeLivro"));
                livro.setNomeAutor(resultSet.getString("nomeAutor"));
                livro.setValor(resultSet.getDouble("valor"));
                livro.setGenero(new GeneroDao().getGenero(resultSet.getInt("codGenero")));
                livro.setEditora(new EditoraDao().getEditora(resultSet.getInt("codEditora")));
                livro.setAtivo(resultSet.getBoolean("ativo"));

                livros.add(livro);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return livros;

    }

    public Livro getLivro(int codigo){

        Livro livro = new Livro();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;

        try(Connection connection = new ConectaDB().getConexao()){


            sql = "SELECT codLivro,nomeLivro,nomeAutor,valor,codGenero,codEditora,ativo FROM livro WHERE codLivro = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                livro.setCodigo(resultSet.getInt("codLivro"));
                livro.setNome(resultSet.getString("nomeLivro"));
                livro.setNomeAutor(resultSet.getString("nomeAutor"));
                livro.setValor(resultSet.getDouble("valor"));
                livro.setGenero(new GeneroDao().getGenero(resultSet.getInt("codGenero")));
                livro.setEditora(new EditoraDao().getEditora(resultSet.getInt("codEditora")));
                livro.setAtivo(resultSet.getBoolean("ativo"));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return livro;

    }

    public String cadastrar(Livro livro){

        String sql;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String status = "";

        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "INSERT INTO livro(nomeLivro,nomeAutor,valor,codeditora,codgenero,ativo) VALUES (?, ?, ?, ?, ?,true)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,livro.getNome());
            preparedStatement.setString(2,livro.getNomeAutor());
            preparedStatement.setDouble(3,livro.getValor());
            preparedStatement.setInt(4,livro.getEditora().getCodigo());
            preparedStatement.setInt(5,livro.getGenero().getCodigo());

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

    public String alterar(Livro livro){

        String sql;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE livro SET nomeLivro = ?, nomeAutor = ?,valor = ?, codeditora = ?,"
                    + " codgenero = ? WHERE codlivro = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,livro.getNome());
            preparedStatement.setString(2,livro.getNomeAutor());
            preparedStatement.setDouble(3,livro.getValor());
            preparedStatement.setInt(4,livro.getEditora().getCodigo());
            preparedStatement.setInt(5,livro.getGenero().getCodigo());
            preparedStatement.setInt(6,livro.getCodigo());

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

    public String deletar(Livro livro){

        String sql;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            status = new EstoqueDao().deletarLivro(livro,connection);
            if(status.equals("OK")){

                sql = "UPDATE livro SET ativo = false WHERE codlivro = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,livro.getCodigo());

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
