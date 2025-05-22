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
public class PlaylistDAO {
    private Connection conn;

    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void criar(String nome) throws SQLException{
        String sql = "insert into playlists (nome, usuario_id) values (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setInt(2, SessaoUsuario.getUsuarioLogado().getId());
        statement.execute();
        conn.close();
    }
    
    public void deletar(String nome) throws SQLException{
        String sql = "delete from playlists where nome = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nome);
        statement.execute();
        conn.close();
    }
    
    public ResultSet buscar(String nome)throws SQLException{
        String sql = "select * from playlists where nome like ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nome);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        
        return resultado;
    }
    
    public ResultSet buscarPorUsuario(int usuario_id)throws SQLException{
        String sql = "select * from playlists where usuario_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, usuario_id);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        
        return resultado;
    }
    
    public ResultSet buscarMusicas(String playlistNome)throws SQLException{
        String sql = "select m.id, m.titulo, m.duracao, m.genero, m.data_lancamento,"
                + " a.id as artista_id, a.nome as artista_nome, a.genero as artista_genero"
                + " from playlists p "
                + " join playlist_musicas pm on p.id = pm.playlist_id"
                + " join musicas m on pm.musica_id = m.id"
                + " join artistas a on m.artista_id = a.id"
                + " where p.nome like ? and p.usuario_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, playlistNome);
        statement.setInt(2, SessaoUsuario.getUsuarioLogado().getId());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
           
        return resultado;
    }
    
    public void adicionarMusica(int musicaId, int playlistId) throws SQLException{
        String sql = "insert into playlist_musicas (playlist_id, musica_id) values (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, playlistId);
        statement.setInt(2, musicaId);
        statement.execute();
        conn.close();
    }
    
    public void removerMusica(int musicaId, int playlistId)throws SQLException{
        String sql = "delete from playlist_musicas where playlist_id = ? and musica_id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, playlistId);
        statement.setInt(2, musicaId);
        statement.execute();
        conn.close();
    }
}
