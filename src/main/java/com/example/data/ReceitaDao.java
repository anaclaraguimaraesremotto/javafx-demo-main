package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Receita;

public class ReceitaDao {
    
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM97898";
    private static final String PASS = "210904";
    
    public void inserir(Receita receita) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);
           
            var sql = "INSERT INTO TBL_RECEITAS_RM97898 (TITULO, INGREDIENTES, MODO_PREPARO) VALUES (?, ?, ?)";
            var comando = conexao.prepareStatement(sql);
            comando.setString(1, receita.titulo());
            comando.setString(2, receita.ingrediente());
            comando.setString(3, receita.modoPreparo()); 
            comando.executeUpdate();
            conexao.close();
    }

    public List<Receita> listarTodos() throws SQLException{
        //conectar com o bd
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var receitas = new ArrayList<Receita>();

        //fazer o select
        var result = conexao
                        .createStatement()
                        .executeQuery("SELECT * FROM TBL_RECEITAS_RM97898");

        //para cada registro
        while(result.next()){
            //adicionar o aluno na lista
            receitas.add(new Receita( 
                result.getString("titulo"), 
                result.getString("ingrediente"),
                result.getString("modo de preparo") 
            ));
        }

        //retotnar a lista
        conexao.close();
        return receitas;
    }
}
