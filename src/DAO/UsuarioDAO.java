/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Usuario;

/**
 *
 * @author Guilherme Rocha
 */
public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(Usuario usuario) throws SQLException{
        String sql = "insert into usuarios (nome, login, senha) values ('"
                      + usuario.getNome()    + "', '"
                      + usuario.getLogin() + "', '"
                      + usuario.getSenha()   + "')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
}
