/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistaDAO {
    private Connection conn;

    public ArtistaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ResultSet buscar(String filtro, String busca) throws SQLException {
        String sql = filtro.equals("id") ? "select * from artistas where " + filtro + " = ?" : "select * from artistas where lower(" + filtro + ") like lower(?)";
        PreparedStatement statement = conn.prepareStatement(sql);
           
        if(filtro.equals("id")){
            statement.setInt(1, Integer.parseInt(busca));
        }else{
            statement.setString(1, busca);
        }
        
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }
    
}
