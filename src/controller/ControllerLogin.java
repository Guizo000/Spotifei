/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.PlaylistDAO;
import DAO.UsuarioDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Playlist;
import model.Usuario;
import util.SessaoUsuario;
import view.LoginFrame;
import view.PrincipalFrame;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerLogin {
    private LoginFrame view;

    public ControllerLogin(LoginFrame view) {
        this.view = view;
    }
    
    public void logarUsuario(){
        Usuario usuario = new Usuario(null, view.getTxt_login_login().getText(), view.getTxt_senha_login().getText());
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                Connection newConn = conexao.getConnection();
                PlaylistDAO daoPlaylist = new PlaylistDAO(newConn);
                
                ResultSet playlistsUsuario = daoPlaylist.buscarPorUsuario(res.getInt("id"));
                List<Playlist> playlists = new ArrayList<>();
                while(playlistsUsuario.next()){
                    Playlist playlist = new Playlist(playlistsUsuario.getInt("id"), playlistsUsuario.getString("nome"), null);
                    playlists.add(playlist);
                }
                //Caso o login de certo, cria um usuario e o torna o usuario da sessao atual 
                Usuario usuarioAtual = new Usuario(res.getString("nome"), view.getTxt_login_login().getText(), view.getTxt_senha_login().getText());
                usuarioAtual.setId(res.getInt("id"));
                usuarioAtual.setPlaylists(playlists);
                SessaoUsuario.setUsuarioLogado(usuarioAtual);
                //passa para a tela principal
                PrincipalFrame pf = new PrincipalFrame();
                pf.setVisible(true);
                view.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(view,  "Usuario ou senha não encontrados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch(SQLException ex){  
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }   
}
