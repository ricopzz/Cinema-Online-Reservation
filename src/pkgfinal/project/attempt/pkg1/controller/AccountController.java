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
import javax.swing.UIManager;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.*;
import pkgfinal.project.attempt.pkg1.SendEmail;
/**
 *
 * @author Yosua
 */
public class AccountController {
    private AccountModel theModel;
    
    private AccountView_Login theLogin;
    private AccountView_SignUp theSignUp;
    private AccountView_ForgetPassword theForgetPassword;
    private SendEmail emailSender = new SendEmail();

   
    
    
    public static void main(String[] args){
        try{
         UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        AccountController myAccountController = new AccountController(new AccountModel(),
               new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
    }
    public AccountController(AccountModel theModel, AccountView_Login theLogin, AccountView_SignUp theSignUp, AccountView_ForgetPassword theForgetPassword) {
        this.theModel = theModel;
        this.theLogin = theLogin;
        this.theSignUp = theSignUp;
        this.theForgetPassword = theForgetPassword;
        start();
    }
    public void start(){
        theModel.establishConnection();
        buildLoginActionListeners();
        buildSignUpActionListeners();
        buildForgetPasswordActionListeners();
        theLogin.setVisible(true);
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
                String username = theLogin.getUsername();
                String password = theLogin.getPassword();
                if(theModel.login(username,password)){
                    JOptionPane.showMessageDialog(null, "Login Succesful");
                    theLogin.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Login failed");
                };
                
            }
        });
        theLogin.addForgetPasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
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
                if(theModel.signUp(name,email,dateOfBirth,username,password,balance)){
                    
                    JOptionPane.showMessageDialog(null, "Sign up succesful");
                    theSignUp.setVisible(false);
                    theSignUp.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Username or Email already exists");
                }
                
            }
        });
    }
    public void buildForgetPasswordActionListeners(){
        theForgetPassword.addContinueActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(ActionEvent e) {
                 String email = theForgetPassword.getText();
                System.out.println("hahahah");
                theModel.setRandomCode(emailSender.sendEmailRegister(email, theModel.getFullname(email))); 
          
                theForgetPassword.getBtnContinue().setVisible(false);
                theForgetPassword.remove(theForgetPassword.getBtnContinue());
                theForgetPassword.getLblEmail().setText("Verification Code");
        
                theForgetPassword.getVerifyButton().setText("Authorize");
                theForgetPassword.getVerifyButton().setBounds(148, 172, 111, 36);
                theForgetPassword.add(theForgetPassword.getVerifyButton());
                theForgetPassword.resetText();
            }
            
        });
        theForgetPassword.addVerifyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = theModel.getRandomCode();
                if (theForgetPassword.getText().equals(code)){
                    JOptionPane.showMessageDialog(null, "Code verified");
                    theForgetPassword.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Code entered is incorect");
                    theForgetPassword.dispose();
                }
                
            }
        });
        
    }
}
