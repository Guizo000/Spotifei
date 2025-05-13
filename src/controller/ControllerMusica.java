/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.ArtistaDAO;
import java.sql.Connection;
import DAO.Conexao;
import DAO.MusicaDAO;
import java.sql.ResultSet;
import view.PrincipalFrame;
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
public class ControllerMusica {
    private PrincipalFrame view;

    public ControllerMusica(PrincipalFrame view) {
        this.view = view;
    }
    
    public void buscarMusica(){
        Conexao conexao = new Conexao();
     
        try{
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ArtistaDAO daoArtista = new ArtistaDAO(conn);
            ResultSet res = null;
            ResultSet resArtista;
            
            if(view.getRb_busca_artista().isSelected()){
                
                resArtista = daoArtista.buscar("nome", view.getTxt_busca().getText());
                if(resArtista.next()){
                    res = dao.buscar("artista_id", Integer.toString(resArtista.getInt("id")));
                }else{
                    res = dao.buscar("artista_id", "0");
                }
                
            }else if(view.getRb_busca_genero().isSelected()){
                res = dao.buscar("genero", view.getTxt_busca().getText());
                
            }else{
                res = dao.buscar("titulo", view.getTxt_busca().getText());
                
            }
            
            List<Musica> musicas = new ArrayList<>();
            while(res.next()) {
                resArtista = daoArtista.buscar("id", Integer.toString(res.getInt("artista_id")));
                Artista artista = null;
                if(resArtista.next()){
                    artista = new Artista(resArtista.getInt("id"), resArtista.getString("nome"), resArtista.getString("genero"));
                }
                Musica musica = new Musica(res.getInt("id"), res.getString("titulo"), res.getInt("duracao"), res.getString("genero"), res.getDate("data_lancamento"), artista);
                
                musicas.add(musica);
            }
            
            DefaultTableModel dtm = (DefaultTableModel) view.getTb_busca().getModel();
            dtm.setRowCount(0);
            
            for (Musica musica : musicas) {
                Object[] row = {
                    musica.getTitulo(),
                    musica.getDuracaoFormatada(),
                    musica.getGenero(),
                    musica.getDataFormatada(),
                    musica.getArtista().getNome()
                };
                dtm.addRow(row);
            }     

        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }  
    }
}
