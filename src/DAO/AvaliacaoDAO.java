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
    
    public void registrarAvaliacao(Musica musica, String acao) throws SQLException{
        String sql = "insert into usuario_musica_curtidas (usuario_id, musica_id, acao) values ('"
                      + SessaoUsuario.getUsuarioLogado().getId() + "', '"
                      + musica.getId() + "', '"
                      + acao + "')";
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    
    public void removerAvaliacao(Musica musica) throws SQLException{
        String sql = "delete from usuario_musica_curtidas where usuario_id = "
                      + SessaoUsuario.getUsuarioLogado().getId() + " and musica_id = "
                      + musica.getId();
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();    
    }
    
    public ResultSet verificarAvaliacao(Musica musica) throws SQLException{
        String sql = "select acao from usuario_musica_curtidas where usuario_id = "
                      + SessaoUsuario.getUsuarioLogado().getId() + " and musica_id = "
                      + musica.getId();
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();  
        ResultSet res = statement.getResultSet();
        return res;
    }
    
    public void inverterAvaliacao(Musica musica, String acao) throws SQLException{
        String sql = "update usuario_musica_curtidas set acao = '" + acao + "' where usuario_id = "
                      + SessaoUsuario.getUsuarioLogado().getId() + " and musica_id = "
                      + musica.getId();
        
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();  
        conn.close();
    }
    
}
