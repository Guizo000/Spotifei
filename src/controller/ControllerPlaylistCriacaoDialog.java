/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.PlaylistDAO;
import java.sql.Connection;
import view.PlaylistCriacaoDialog;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Guilherme Rocha
 */
public class ControllerPlaylistCriacaoDialog {
    private PlaylistCriacaoDialog view;

    public ControllerPlaylistCriacaoDialog(PlaylistCriacaoDialog view) {
        this.view = view;
    }
    
    public boolean criarPlaylist(String nomePlaylist){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            PlaylistDAO dao = new PlaylistDAO(conn);
            dao.criar(nomePlaylist);
            return true;
        }catch(SQLException ex){
            if(ex.getSQLState().equals("23505")){
               JOptionPane.showMessageDialog(view, "Nome de playlist já existente!", "Erro", JOptionPane.ERROR_MESSAGE);
               return false;
            }else{
                JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        }
    }
}
