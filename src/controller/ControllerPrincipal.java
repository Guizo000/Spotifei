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
public class ControllerPrincipal {
    private PrincipalFrame view;

    public ControllerPrincipal(PrincipalFrame view) {
        this.view = view;
    }
    
    public void buscarMusica(){
        Conexao conexao = new Conexao();
     
        try{
            Connection conn = conexao.getConnection();
            MusicaDAO dao = new MusicaDAO(conn);
            ArtistaDAO daoArtista = new ArtistaDAO(conn);
            ResultSet res;
            ResultSet resArtista;
            
            if(view.getRb_busca_artista().isSelected()){
                /* Primeiro usa o metodo buscar de ArtistaDAO para retornar
                o id do artista com base no seu nome, necessario para achar 
                as musicas do artista, pois a chave estrangeira da tabela artistas
                em musicas é o artista_id */
                resArtista = daoArtista.buscar("nome", view.getTxt_busca().getText());
                /* A ação apos o : serve para que o ResultSet res nunca fique 
                nulo evitando excessões ao cheagar no while(res.next()) */
                res = resArtista.next() ? dao.buscar("artista_id", resArtista.getInt("id")) : dao.buscar("artista_id", 0);
            }else if(view.getRb_busca_genero().isSelected()){
                res = dao.buscar("genero", view.getTxt_busca().getText());
            }else{
                res = dao.buscar("titulo", view.getTxt_busca().getText());    
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
            
            //Codigo para exibir as musicas encontradas na tabela
            DefaultTableModel dtm = (DefaultTableModel) view.getTb_busca().getModel();
            dtm.setRowCount(0);
            
            for (Musica musica : musicas) {
                Object[] row = {
                    musica.getTitulo(),
                    musica.getDuracaoFormatada(),
                    musica.getGenero(),
                    musica.getDataFormatada(),
                    musica.getArtista().getNome(),
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
