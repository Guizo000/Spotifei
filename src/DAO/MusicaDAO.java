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
public class MusicaDAO {
    private Connection conn;

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }
    
    /*
    Metodo para buscar artistas no BD, recebe a string filtro que sera a coluna
    na qual sera feita a busca, e a váriavel busca que é o termo a ser procurado.
    Possui uma sobrecarga para que seja possivel procurar termos do tipo int (como id's) 
    e strings(nomes, termos, etc..)
    */
    public ResultSet buscar(String filtro, String busca, boolean registrar) throws SQLException {
        String sql = "select * from musicas where lower(" + filtro + ") like lower(?) order by artista_id";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, busca);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        //Registrando busca no Banco de Dados
        if(registrar){
            registrarBusca(busca, filtro);
        }
        return resultado;
    }
    
    public ResultSet buscar(String filtro, int busca, String termo_busca, boolean registrar) throws SQLException {
        String sql = "select * from musicas where " + filtro + " = ? order by artista_id";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, busca);
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        //Registrando busca no Banco de Dados
        if(registrar){
            registrarBusca(termo_busca, "artista");
        }
        
        return resultado;
    }
    
    private void registrarBusca(String termo_busca, String tipo_busca) throws SQLException{
        String sql = "insert into historico_buscas (usuario_id, termo_busca, tipo_busca) values (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, SessaoUsuario.getUsuarioLogado().getId());
        statement.setString(2, termo_busca);
        statement.setString(3, tipo_busca);
        statement.execute();
    }
     
}
