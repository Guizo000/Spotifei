/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Musica;
import util.SessaoUsuario;

/**
 *
 * @author Guilherme Rocha
 */
public class AvaliacaoDAO {
    private Connection conn;

    public AvaliacaoDAO(Connection conn) {
        this.conn = conn;
    }
    
    //Adiciona uma avaliação na tabela
    public void registrarAvaliacao(Musica musica, String acao) throws SQLException{
        String sql = "insert into usuario_musica_curtidas (usuario_id, musica_id, acao) values (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.setInt(2, musica.getId());
        statement.setString(3, acao);
        statement.execute();
        conn.close();
    }
    
    //Remove uma avaliação da tabela
    public void removerAvaliacao(Musica musica) throws SQLException{
        String sql = "delete from usuario_musica_curtidas where usuario_id = ? and musica_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.setInt(2, musica.getId());
        statement.execute(); 
        conn.close();
    }
    
    //Verifica se a musica do parâmetro foi avaliada pelo usuario logado e caso tenha sido retorna a avaliação
    public ResultSet verificarAvaliacao(Musica musica) throws SQLException{
        String sql = "select acao from usuario_musica_curtidas where usuario_id = ? and musica_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.setInt(2, musica.getId());
        statement.execute();  
        ResultSet res = statement.getResultSet();
        conn.close();
        
        return res;
    }
    
    //Inverte a avaliação da musica, curtida vira descurtida e vice versa
    public void inverterAvaliacao(Musica musica, String acao) throws SQLException{
        String sql = "update usuario_musica_curtidas set acao = ? where usuario_id = ? and musica_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, acao);
        statement.setInt(2, SessaoUsuario.getUsuarioLogado().getId());
        statement.setInt(3, musica.getId());
        statement.execute();  
        conn.close();
    }
    
}
