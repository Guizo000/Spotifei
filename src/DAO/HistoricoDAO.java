/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.SessaoUsuario;
/**
 *
 * @author Guilherme Rocha
 */
public class HistoricoDAO {
    private Connection conn;
    
    public HistoricoDAO(Connection conn){
        this.conn = conn;
    }
    
    public ResultSet buscarHistoricoDeBuscas(int qtd) throws SQLException {
        String sql = "select termo_busca, tipo_busca from historico_buscas where usuario_id = ? order by id desc limit 10";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        return resultado;
    }
    
    public ResultSet buscarMusicasAvaliadas(int qtd, String acao) throws SQLException {
        String sql = "select musica_id from usuario_musica_curtidas where usuario_id = ? and acao = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.setString(2, acao);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        return resultado;
    }
}
