package com.example.livraria_backend.dao;

import com.example.livraria_backend.model.Estoque;
import com.example.livraria_backend.model.Filial;
import com.example.livraria_backend.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDao {

    public List<Estoque> getEstoques(){

        ArrayList<Estoque> estoques = new ArrayList<Estoque>();
        String sql;
        Statement statement;
        ResultSet resultSet;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEstoque,codFilial,codLivro,quantidade,ativo FROM estoque WHERE ativo = true";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                Estoque estoque = new Estoque();
                estoque.setCodigo(resultSet.getInt("codEstoque"));
                estoque.setFilial(new FilialDao().getFilial(resultSet.getInt("codFilial")));
                estoque.setLivro(new LivroDao().getLivro(resultSet.getInt("codLivro")));
                estoque.setQuantidade(resultSet.getInt("quantidade"));
                estoque.setAtivo(resultSet.getBoolean("ativo"));

                estoques.add(estoque);

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return estoques;

    }

    public Estoque getEstoque(int codigo){

        Estoque estoque = new Estoque();
        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEstoque,codFilial,codLivro,quantidade,ativo FROM estoque WHERE codestoque = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                estoque.setCodigo(resultSet.getInt("codEstoque"));
                estoque.setFilial(new FilialDao().getFilial(resultSet.getInt("codFilial")));
                estoque.setLivro(new LivroDao().getLivro(resultSet.getInt("codLivro")));
                estoque.setQuantidade(resultSet.getInt("quantidade"));
                estoque.setAtivo(resultSet.getBoolean("ativo"));

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return estoque;

    }

    //Verifica se tem um livro especifico no estoque
    public int getEstoqueLivro(int codigo){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;
        int contador = 0;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEstoque,codFilial,codLivro,quantidade,ativo FROM estoque WHERE codlivro = ? AND ativo = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){


                if(resultSet.getBoolean("ativo")){

                    contador ++;

                }

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return contador;

    }

    //Verifica se tem uma filial especifico no estoque
    public int getEstoqueFilial(int codigo){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status;
        int contador = 0;

        try(Connection connection = new ConectaDB().getConexao()){

            sql = "SELECT codEstoque,codFilial,codLivro,quantidade,ativo FROM estoque WHERE codfilial = ? AND ativo = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){


                if(resultSet.getBoolean("ativo")){

                    contador ++;

                }

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return contador;

    }

    public String cadastrar(Estoque estoque){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "INSERT INTO estoque(codfilial, codlivro, quantidade,ativo) VALUES (?, ?, ?,true)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,estoque.getFilial().getCodigo());
            preparedStatement.setInt(2,estoque.getLivro().getCodigo());
            preparedStatement.setInt(3,estoque.getQuantidade());

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

    public String alterar(Estoque estoque){

        String sql;
        Statement statement;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE estoque SET codfilial = ?, codlivro = ?, quantidade = ? WHERE codestoque= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,estoque.getFilial().getCodigo());
            preparedStatement.setInt(2,estoque.getLivro().getCodigo());
            preparedStatement.setInt(3,estoque.getQuantidade());
            preparedStatement.setInt(4,estoque.getCodigo());

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

    public String deletar(Estoque estoque){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try(Connection connection = new ConectaDB().getConexao()){

            connection.setAutoCommit(false);
            sql = "UPDATE estoque SET ativo = false WHERE codestoque = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,estoque.getCodigo());

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

    //Retira um livro do estoque
    public String deletarLivro(Livro livro, Connection connection){


        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try{

            if(getEstoqueLivro(livro.getCodigo())>0){

                connection.setAutoCommit(false);
                sql = "UPDATE estoque SET ativo = false WHERE codLivro = ?";
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

            }else{

                //Significa que não há registros ativos deste livro no estoque
                status = "OK";

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return status;

    }

    //Retira a filial do Estoque
    public String deletarFilial(Filial filial, Connection connection){

        String sql;
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String status = "";
        try{

            if(getEstoqueFilial(filial.getCodigo())>0){

                connection.setAutoCommit(false);
                sql = "UPDATE estoque SET ativo = false WHERE codFilial = ?";
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

            }else{

                //Significa que não há registros ativos desta filial no estoque
                status = "OK";

            }

        }catch(SQLException ex){

            ex.printStackTrace();

        }

        return status;

    }

}
