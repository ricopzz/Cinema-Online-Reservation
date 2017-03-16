/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.*;

/**
 *
 * @author Yosua
 */
public class AccountController {
    private AccountModel theModel;
    
    private AccountView_Login theLogin;
    private AccountView_SignUp theSignUp;
    private AccountView_ForgetPassword theForgetPassword;

    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USER = "root";
    private final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;
    
    
    public AccountController(AccountModel theModel, AccountView_Login theLogin, AccountView_SignUp theSignUp, AccountView_ForgetPassword theForgetPassword) {
        this.theModel = theModel;
        this.theLogin = theLogin;
        this.theSignUp = theSignUp;
        this.theForgetPassword = theForgetPassword;
        start();
    }
    public void start(){
        establishConnection();
        buildLoginActionListeners();
        buildSignUpActionListeners();
        buildForgetPasswordActionListeners();
        theLogin.setVisible(true);
    }
    public void login(){
        try {
            String username = theLogin.getUsername();
            String password = theLogin.getPassword();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts WHERE Username='" + username +
                    "' AND Password = '" + password + "'");
            if (rs.next()){
                JOptionPane.showMessageDialog(null, "Login succesful");
            }else{
                JOptionPane.showMessageDialog(null, "Login failed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void establishConnection(){
        try {
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = (Statement) conn.createStatement();
            stmt.executeQuery("USE final_project_programming_languages_db");
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error establishing connection to database");
        }
    }
    public void buildLoginActionListeners(){
        theLogin.addSignUpListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theSignUp = new AccountView_SignUp();
                buildSignUpActionListeners();
                theSignUp.setVisible(true);
            }
        });
        theLogin.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        theLogin.addForgetPasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theForgetPassword = new AccountView_ForgetPassword();
                theForgetPassword.setVisible(true);
            }
        });
    }
    public void buildSignUpActionListeners(){
        theSignUp.addContinueListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = theSignUp.getName();
                String email = theSignUp.getEmail();
                String dateOfBirth = theSignUp.getDateOfBirth ();
                String username = theSignUp.getUsername();
                String password = theSignUp.getPassword();
                int balance = 0;
                try {
                    String query = "INSERT INTO Accounts (`Username`,`Password`,`Name`,`Email`,`DOB`,`Balance`) VALUES (" +
                            "'"+ username +"', "+"'"+ password +"', "+"'"+ name+"', "+"'"+email +"', "+"'"+ dateOfBirth +"', "+
                            balance+")";
                    System.out.println(query);
                    stmt.executeUpdate(query);
                } catch (SQLException ex) {
                    Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Username or Email already exists");
                }
                theSignUp.setVisible(false);
                theSignUp.dispose();
            }
        });
    }
    public void buildForgetPasswordActionListeners(){
        theForgetPassword.addContinueActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theForgetPassword.getBtnContinue().setVisible(false);
                theForgetPassword.remove(theForgetPassword.getBtnContinue());
                theForgetPassword.getLblEmail().setText("Verification Code");
        
                theForgetPassword.setVerifyButton(new JButton("Authorize"));
                theForgetPassword.getVerifyButton().setBounds(148, 172, 111, 36);
                theForgetPassword.add(theForgetPassword.getVerifyButton());
                theForgetPassword.getVerifyButton().addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                        theForgetPassword.dispose();
                    }
                });
            }
        });
    }
}
