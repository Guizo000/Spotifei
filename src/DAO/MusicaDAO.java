/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Musica;

/**
 *
 * @author Guilherme Rocha
 */
public class MusicaDAO {
    private Connection conn;

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet buscar(String filtro, String busca) throws SQLException {
        String sql = filtro.equals("artista_id") ?  "select * from musicas where " + filtro + " = ?" : "select * from musicas where lower(" + filtro + ") like lower(?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        if(filtro.equals("artista_id")){
            statement.setInt(1, Integer.parseInt(busca));
        }else{
            statement.setString(1, busca);
        }
        
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        
        
        return resultado;
    }
}
