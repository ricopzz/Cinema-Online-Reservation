/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.views;

import pkgfinal.project.attempt.pkg1.model.AccountModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Enrico
 */
public class AccountView_SignUp extends JFrame{
    
    JLabel signTitle, lblUserName, lblPassword, lblName, lblDateOfBirth, lblEMail, lblDay, lblMonth, lblYear;
    JPasswordField txtPassword;
    JTextField txtUsername, txtName, txtEMail;
    JButton btnContinue;
    JSpinner dateSpin, monthSpin, yearSpin;
    AccountModel user = new AccountModel();
    SpinnerModel dateModel = new SpinnerNumberModel(1,1,31,1);
    SpinnerModel monthModel = new SpinnerNumberModel(1,1,12,1);
    SpinnerModel yearModel = new SpinnerNumberModel(1999,1950,2003,1);
    
    public String getUsername(){
        return txtUsername.getText();
    }
    public String getName(){
        return txtName.getText();
    }
    public String getEmail(){
        return txtEMail.getText();
    }  
    public String getPassword(){
        return txtPassword.getText();
    }
    public void addContinueListener(ActionListener a){
        btnContinue.addActionListener(a);
    }
    public String getDateOfBirth(){
        String date = yearSpin.getValue().toString() + "-"+ 
                      monthSpin.getValue().toString() + "-"+ 
                      dateSpin.getValue().toString();
        return date;
    }
    public void initComponents(){
        // SET WINDOW TITLE AND SHOW
        setTitle("SIGN UP");
        setSize(420,380);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         
        signTitle = new JLabel("SIGN UP");
        signTitle.setForeground(Color.blue);
        signTitle.setFont(new Font("Lucida", Font.BOLD, 20));
 
        lblUserName = new JLabel("Username :");
        lblPassword = new JLabel("Password :");
        lblName = new JLabel("Name :");
        lblEMail = new JLabel("E-mail :");
        lblDateOfBirth = new JLabel("Date of Birth :");
        lblDay = new JLabel("Day");
        lblMonth = new JLabel("Month");
        lblYear = new JLabel ("Year");
        txtName = new JTextField();
        txtEMail = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnContinue = new JButton("Continue");
        dateSpin = new JSpinner(dateModel);
        monthSpin = new JSpinner(monthModel);
        yearSpin = new JSpinner(yearModel);
        
        // SETTING ALL POSITION
        signTitle.setBounds(150, 30, 200, 30);
        lblName.setBounds(20, 70, 200, 30);
        lblEMail.setBounds(20, 110, 200, 30);
        lblDateOfBirth.setBounds(20, 150, 200, 30);
        lblUserName.setBounds(20, 190, 200, 30);
        lblPassword.setBounds(20, 230, 200, 30);
        dateSpin.setBounds(120,150,35,30);
        ((JSpinner.NumberEditor) dateSpin.getEditor()).getFormat().setMaximumFractionDigits(4);
        lblDay.setBounds(160,150,50,30);
        monthSpin.setBounds(200,150,35,30);
        ((JSpinner.NumberEditor) dateSpin.getEditor()).getFormat().setMaximumFractionDigits(4);
        lblMonth.setBounds(240,150,50,30);
        yearSpin.setBounds(290,150,65,30);
        ((JSpinner.NumberEditor) dateSpin.getEditor()).getFormat().setMaximumFractionDigits(4);
        lblYear.setBounds(360,150,50,30);
        txtName.setBounds(120, 70, 240, 30);
        txtEMail.setBounds(120, 110, 240, 30);
        txtUsername.setBounds(120, 190, 240, 30);
        txtPassword.setBounds(120, 230, 240, 30);
        btnContinue.setBounds(25, 300, 100, 30);
        
        add(signTitle);
        add(lblName);
        add(lblEMail);
        add(lblDateOfBirth);
        add(lblUserName);
        add(lblPassword);
        add(lblDay);
        add(lblMonth);
        add(lblYear);
        add(dateSpin);
        add(monthSpin);
        add(yearSpin);
        add(txtName);
        add(txtEMail);
        add(txtUsername);
        add(txtPassword);
        add(btnContinue);
    }
    public AccountView_SignUp(){
        initComponents();
    }
    
}
