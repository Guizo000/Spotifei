/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.PlaylistDAO;
import java.sql.Connection;
import java.util.List;
import model.Musica;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Artista;
import model.Musica;
import view.PrincipalFrame;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerPrincipalPlaylists {
    private PrincipalFrame view;

    public ControllerPrincipalPlaylists(PrincipalFrame view) {
        this.view = view;
    }
    
    public List<Musica> buscarMusicasPlaylist(String nomePlaylist){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscarMusicas(nomePlaylist);
            List<Musica> musicas = new ArrayList();
            while(res.next()){
                Artista artista = new Artista(res.getInt("artista_id"), res.getString("artista_nome"), res.getString("artista_genero"));
                Musica musica = new Musica(res.getInt("id"), res.getString("titulo"), res.getInt("duracao"), res.getString("genero"), res.getDate("data_lancamento"), artista);
                musicas.add(musica);
            }
            return musicas;
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void deletarPlaylist(String playlistNome){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            dao.deletar(playlistNome);
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
