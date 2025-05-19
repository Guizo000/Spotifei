/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.AvaliacaoDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import model.Musica;
import view.MusicaDialog;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerMusicaDialog {
    MusicaDialog view;
    
    public ControllerMusicaDialog(MusicaDialog view){
        this.view = view;
    }
    
    public String avaliarMusica(Musica musica, String acao, String acaoPrevia){
        Conexao conexao = new Conexao();
        
        try{
            Connection conn = conexao.getConnection();
            AvaliacaoDAO dao = new AvaliacaoDAO(conn);
            
            if(acaoPrevia.equals("nenhuma") && acao.equals("curtida")){
                dao.registrarAvaliacao(musica, acao);
                JOptionPane.showMessageDialog(view, "Musica curtida!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "curtida";
                
            }else if(acaoPrevia.equals("nenhuma") && acao.equals("descurtida")){
                dao.registrarAvaliacao(musica, acao);
                JOptionPane.showMessageDialog(view, "Musica descurtida!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "descurtida";
                
            }else if(acaoPrevia.equals("curtida") && acao.equals("curtida")){
                dao.removerAvaliacao(musica);
                JOptionPane.showMessageDialog(view, "Curtida removida!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "nenhuma";
                
            }else if(acaoPrevia.equals("curtida") && acao.equals("descurtida")){
                dao.inverterAvaliacao(musica, acao);
                JOptionPane.showMessageDialog(view, "Musica descurtida!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "descurtida";
                
            }else if(acaoPrevia.equals("descurtida") && acao.equals("curtida")){
                dao.inverterAvaliacao(musica, acao);
                JOptionPane.showMessageDialog(view, "Musica curtida!","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "curtida";
                
            }else if(acaoPrevia.equals("descurtida") && acao.equals("descurtida")){
                dao.removerAvaliacao(musica);
                JOptionPane.showMessageDialog(view, "Descurtida removida","Aviso", JOptionPane.INFORMATION_MESSAGE);
                return "nenhuma"; 
            }else{
                return acaoPrevia;
            }
                    
        }catch (SQLException ex){
            System.out.println(ex);
            return acaoPrevia;
        }
    }
    
    public String verificarAvaliacaoDeMusica(Musica musica){
        Conexao conexao = new Conexao();
        
        try{
            Connection conn = conexao.getConnection();
            AvaliacaoDAO dao = new AvaliacaoDAO(conn);
            ResultSet res = dao.verificarAvaliacao(musica);
            if(res.next()){
                return res.getString("acao");
            }else{
                return "nenhuma";
            }
        }catch (SQLException ex){
            System.out.println(ex);
            return "erro";         
        }
    }
}
