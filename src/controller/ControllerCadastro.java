/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.UsuarioDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.CadastroFrame;
import view.LoginFrame;

/**
 *
 * @author Guilherme Rocha
 */
public class ControllerCadastro {
    private CadastroFrame view;
    
    public ControllerCadastro(CadastroFrame view){
        this.view = view;
    }
    
    public void cadastrarUsuario() throws IllegalArgumentException{
        String nome = view.getTxt_nome_cadastro().getText();
        String login = view.getTxt_login_cadastro().getText();
        String senha = view.getTxt_senha_cadastro().getText();
        
        //Verificação para evitar que os campos estejam vazios
        if (view.getTxt_nome_cadastro().getText() == null || view.getTxt_nome_cadastro().getText().trim().isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    
        if (view.getTxt_login_cadastro().getText() == null || view.getTxt_login_cadastro().getText().trim().isEmpty()){
            throw new IllegalArgumentException("Login não pode ser vazio");
        }
        
        if (view.getTxt_senha_cadastro().getText() == null || view.getTxt_senha_cadastro().getText().trim().isEmpty()){
            throw new IllegalArgumentException("Senha não pode ser vazio");
        }
        
        //Criando o usuario e estabelecendo conexao com o banco de dados apos verificações
        Usuario usuario = new Usuario(nome, login, senha);
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            dao.inserir(usuario);
            JOptionPane.showMessageDialog(view, "Usuario Cadastrado!","Aviso", JOptionPane.INFORMATION_MESSAGE);
            
            //Voltando para tela de login caso cadastro de ceto
            LoginFrame lf = new LoginFrame();
            lf.setVisible(true);
            view.setVisible(false);
           
        } catch (SQLException ex) {
            switch(ex.getSQLState()){
                case "23505" -> JOptionPane.showMessageDialog(view, "Este login está indisponível","Erro", JOptionPane.ERROR_MESSAGE);
                default -> JOptionPane.showMessageDialog(view, "Erro de conexão!","Erro", JOptionPane.ERROR_MESSAGE);
            }         
        }
    }
}
