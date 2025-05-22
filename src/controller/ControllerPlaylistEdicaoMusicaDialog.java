/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.ArtistaDAO;
import DAO.Conexao;
import DAO.MusicaDAO;
import DAO.PlaylistDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Artista;
import model.Musica;
import model.Playlist;
import view.PlaylistEdicaoMusicaDialog;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerPlaylistEdicaoMusicaDialog {
    PlaylistEdicaoMusicaDialog view;

    public ControllerPlaylistEdicaoMusicaDialog(PlaylistEdicaoMusicaDialog view) {
        this.view = view;
    }
    
    public List<Musica> buscarMusica(){
        Conexao conexao = new Conexao();
     
        try{
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ArtistaDAO daoArtista = new ArtistaDAO(conn);
            ResultSet res;
            ResultSet resArtista;
            
            if(view.getRb_artista().isSelected()){
                /* Primeiro usa o metodo buscar de ArtistaDAO para retornar
                o id do artista com base no seu nome, necessario para achar 
                as musicas do artista, pois a chave estrangeira da tabela artistas
                em musicas é o artista_id */
                resArtista = daoArtista.buscar("nome", view.getTxt_busca().getText());
                /* A ação apos o : serve para que o ResultSet res nunca fique 
                nulo evitando excessões ao cheagar no while(res.next()) */
                res = resArtista.next() ? dao.buscar("artista_id", resArtista.getInt("id"), view.getTxt_busca().getText(), true) : dao.buscar("artista_id", 0, view.getTxt_busca().getText(), true);
            }else if(view.getRb_genero().isSelected()){
                res = dao.buscar("genero", view.getTxt_busca().getText(), true);
            }else{
                res = dao.buscar("titulo", view.getTxt_busca().getText(), true);    
            }
            
            List<Musica> musicas = new ArrayList<>();
            Artista artista;
            while(res.next()) {
                /* Retorna o nome do artista com base no id para cada musica encontrada
                   depois cria o artista para poder associa-lo a musica
                */
                resArtista = daoArtista.buscar("id", res.getInt("artista_id"));
                artista = resArtista.next() ? new Artista(resArtista.getInt("id"), resArtista.getString("nome"), resArtista.getString("genero")) : null;
                
                Musica musica = new Musica(res.getInt("id"), res.getString("titulo"), res.getInt("duracao"), res.getString("genero"), res.getDate("data_lancamento"), artista);
                musicas.add(musica);
            }
            
            return musicas;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }  
    }
    
    public Playlist buscarPlaylist(String playlistNome){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscar(playlistNome);
            res.next();
            Playlist playlist = new Playlist(res.getInt("id"), res.getString("nome"), null);
            return playlist;
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void adicionarMusicaPlaylist(Musica musica, String playlistNome){
        Conexao conexao = new Conexao();
        
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscar(playlistNome);
            res.next();
            dao.adicionarMusica(musica.getId(), res.getInt("id"));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removerMusicaPlaylist(Musica musica, String playlistNome){
        Conexao conexao = new Conexao();
        
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            ResultSet res = dao.buscar(playlistNome);
            res.next();
            dao.removerMusica(musica.getId(), res.getInt("id"));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
