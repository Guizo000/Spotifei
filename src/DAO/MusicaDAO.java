/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    na qual sera feita a busca, e váriavel busca que é o termo a ser procurado.
    Possui uma sobrecarga para que seja possivel procurar termos do tipo int ou string
    */
    public ResultSet buscar(String filtro, String busca) throws SQLException {
        String sql = "select * from musicas where lower(" + filtro + ") like lower(?) order by artista_id";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, busca);
       
        statement.execute();
        ResultSet resultado = statement.getResultSet();

        return resultado;
    }
    
    public ResultSet buscar(String filtro, int busca) throws SQLException {
        String sql = "select * from musicas where " + filtro + " = ? order by artista_id";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, busca);
       
        statement.execute();
        ResultSet resultado = statement.getResultSet();

        return resultado;
    }
     
}
