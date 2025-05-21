/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.PrincipalFrame;
import DAO.ArtistaDAO;
import DAO.Conexao;
import DAO.HistoricoDAO;
import DAO.MusicaDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Artista;
import model.Musica;
/**
 *
 * @author Guilherme Rocha
 */
public class ControllerPrincipalHistorico {
    private PrincipalFrame view;
    
    public ControllerPrincipalHistorico(PrincipalFrame view){
        this.view = view;
    }
    
    public void exibirHistoricoBuscas(){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            HistoricoDAO daoHistorico = new HistoricoDAO(conn);
            ResultSet res = daoHistorico.buscarHistoricoDeBuscas(10);
            
            DefaultTableModel dtm = (DefaultTableModel) view.getHistorico_tb_historico_buscas().getModel();
            dtm.setRowCount(0);
            while(res.next()){
                Object[] row = {
                    res.getString("termo_busca"),
                    res.getString("tipo_busca")
                };
                dtm.addRow(row);
            }
            
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void exibirHistoricoMusicasAvaliadas(String acao){
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            HistoricoDAO daoHistorico = new HistoricoDAO(conn);
            ResultSet res = daoHistorico.buscarMusicasAvaliadas(10, acao); 
            
            ResultSet resMusica;
            ResultSet resArtista;
            Artista artista;
            Musica m;
            MusicaDAO daoMusica = new MusicaDAO(conn);
            ArtistaDAO daoArtista = new ArtistaDAO(conn);
            List<Musica> musicas = new ArrayList<>();
            while(res.next()){
                resMusica = daoMusica.buscar("id", res.getInt("musica_id"), null, false);
                resMusica.next();
                
                resArtista = daoArtista.buscar("id", resMusica.getInt("artista_id"));
                resArtista.next();
                artista = new Artista(resArtista.getInt("id"), resArtista.getString("nome"), resArtista.getString("genero"));
                
                m = new Musica(resMusica.getInt("id"), resMusica.getString("titulo"), resMusica.getInt("duracao"), resMusica.getString("genero"), resMusica.getDate("data_lancamento"), artista);
                musicas.add(m);
            }
            
            //Codigo para exibir as musicas encontradas na tabela
            DefaultTableModel dtm = acao.equals("curtida") ? (DefaultTableModel) view.getHistorico_tb_musicas_curtidas().getModel() : (DefaultTableModel) view.getHistorico_tb_musicas_descurtidas().getModel();
            dtm.setRowCount(0);
            for (Musica musica : musicas) {
                Object[] row = {
                    musica.getTitulo(),
                    musica
                };
                dtm.addRow(row);
            }  
            
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
