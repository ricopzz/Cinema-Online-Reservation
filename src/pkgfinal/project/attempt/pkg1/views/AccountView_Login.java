/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.views;

/**
 *
 * @author Enrico
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
 
public class AccountView_Login extends JFrame {
    JLabel LoginTitle, lblUserName, lblPassword;
    JTextField txtUsername;
    JButton btnLogin, btnSignup, btnFPass;
    JPasswordField txtPassword;
    
    public String getUsername(){
        return txtUsername.getText();
    }
    public String getPassword(){
        return txtPassword.getText();
    }
    public void addSignUpListener(ActionListener a){
        btnSignup.addActionListener(a);
    }
    public void addLoginListener(ActionListener a){
        btnLogin.addActionListener(a);
    }
    public void addForgetPasswordListener(ActionListener a){
        btnFPass.addActionListener(a);
    }
    public void initComponents(){
        setTitle("LOGIN");
        setVisible(true);
        setSize(400,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        LoginTitle = new JLabel("CINEMA 21");
        LoginTitle.setForeground(Color.blue);
        LoginTitle.setFont(new Font("Lucida", Font.BOLD, 20));
 
        lblUserName = new JLabel("Username :");
        lblPassword = new JLabel("Password :");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Log In");
        btnSignup = new JButton("Sign Up");
        btnFPass = new JButton("Forget Password?");
 
        LoginTitle.setBounds(150, 30, 200, 30);
        lblUserName.setBounds(20, 70, 200, 30);
        lblPassword.setBounds(20, 110, 200, 30);
        txtUsername.setBounds(120, 70, 200, 30);
        txtPassword.setBounds(120, 110, 200, 30);
        btnLogin.setBounds(225, 160, 100, 30);
        btnSignup.setBounds(80, 160, 100, 30);
        btnFPass.setBounds(100, 205, 200, 30);
 
        add(LoginTitle);
        add(lblUserName);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(btnLogin);
        add(btnSignup);
        add(btnFPass);
        repaint();
    }
    public AccountView_Login(){
        initComponents();
    }
}
