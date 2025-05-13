/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.Conexao;
import DAO.UsuarioDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.LoginFrame;
import view.PrincipalFrame;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerLogin {
    private LoginFrame view;

    public ControllerLogin(LoginFrame view) {
        this.view = view;
    }
    
    public void logarUsuario(){
        Usuario usuario = new Usuario(null, view.getTxt_login_login().getText(), view.getTxt_senha_login().getText());
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                JOptionPane.showMessageDialog(view, "Login efetuado!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                PrincipalFrame pf = new PrincipalFrame();
                pf.setVisible(true);
                view.setVisible(false);
                
            }else{
                JOptionPane.showMessageDialog(view,  "Login NÃO efetuado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch(SQLException ex){  
            System.out.println(ex);
            JOptionPane.showMessageDialog(view, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }   
}
