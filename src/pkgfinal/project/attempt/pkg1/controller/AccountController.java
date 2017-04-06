/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;


import admin.AdminView_AddAccount;
import admin.AdminView_AddSchedule;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.*;
import pkgfinal.project.attempt.pkg1.SendEmail;
import pkgfinal.project.attempt.pkg1.model.AdminModel;
import pkgfinal.project.attempt.pkg1.model.CashierModel;
import pkgfinal.project.attempt.pkg1.model.CustomerModel;
import pkgfinal.project.attempt.pkg1.views.admin.AdminView_AddLocation;
import pkgfinal.project.attempt.pkg1.views.admin.AdminView_AddMovie;
import pkgfinal.project.attempt.pkg1.views.admin.AdminView_AddVoucher;
import pkgfinal.project.attempt.pkg1.views.admin.AdminView_EditMovie;
import pkgfinal.project.attempt.pkg1.views.admin.AdminView_MainScreen;
import pkgfinal.project.attempt.pkg1.views.cashier.CashierView_Interface;
import pkgfinal.project.attempt.pkg1.views.cashier.CashierView_VerifyPrint;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_ChooseSeat;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Interface_V2;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_AddBalance;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_ChangeEmail;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_ChangePassword;
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
    
    // builds action listeners for the views
    
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
                // checks if login succesful
                if(theModel.login(username,password)){
                    JOptionPane.showMessageDialog(null, "Login Succesful");
                    theLogin.dispose();
                    String type = theModel.getType(username);
                    // opens respective controllers for each user type
                    if (type.equals("Customer")){
                        CustomerController m = new CustomerController(username,new CustomerView_Interface_V2() ,new CustomerView_ChangeEmail() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() , new CustomerModel() );
                    }else if (type.equals("Admin")){
                        AdminController myAdminController = new AdminController(username,new AdminView_MainScreen(), new AdminView_AddLocation(), new AdminView_AddMovie(),new AdminView_EditMovie(), new AdminView_AddSchedule(), new AdminView_AddAccount(),new AdminView_AddVoucher(), new AdminModel());   
                    }else if (type.equals("Cashier")){
                        CashierController myCashier = new CashierController(username,new CashierView_Interface(),new CashierView_VerifyPrint(), new CashierModel());                                
                    }else{
                        JOptionPane.showMessageDialog(null, "Error logging in : User type undefined");
                     }
                }else{
                    JOptionPane.showMessageDialog(null, "Login failed");
                };
                
            }
        });
        theLogin.addForgetPasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theForgetPassword = new AccountView_ForgetPassword();
                theForgetPassword.setVerificationCodePanelVisible(false);
                theForgetPassword.setNewPasswordPanelVisible(false);
                buildForgetPasswordActionListeners();
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
                // checks if all the fields have been filled
                if (!(name.equals("") || email.equals("") || dateOfBirth.equals("") || username.equals("") || password.equals(""))){
                    if(theModel.signUp(name,email,dateOfBirth,username,password,balance)){
                        JOptionPane.showMessageDialog(null, "Sign up succesful");
                        theSignUp.setVisible(false);
                        theSignUp.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Username or Email already exists");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                }
            }
        });
    }
    
    public void buildForgetPasswordActionListeners(){
        theForgetPassword.addContinueActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gets email from the forget password
                String email = theForgetPassword.getEmail();
                // sends code for email
                String code = emailSender.sendEmailRegister(email, theModel.getFullname(email));
                if (code != null){
                    theModel.setRandomCode(code); 
                    theForgetPassword.setEmailPanelVisible(false);
                    theForgetPassword.setVerificationCodePanelVisible(true);
                }
            }
        });
        theForgetPassword.addVerifyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // gets the random code
                String code = theModel.getRandomCode();
                // verifies the random code
                if (theForgetPassword.getVerificationCode().equals(code)){
                    JOptionPane.showMessageDialog(null, "Code verified");
                    theForgetPassword.setVerificationCodePanelVisible(false);
                    theForgetPassword.setNewPasswordPanelVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Code entered is incorect");
                }
            }
        });
        theForgetPassword.addFinishActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // sets new password
                String newPassword = theForgetPassword.getNewPassword();
                String email = theForgetPassword.getEmail();
                theModel.changePassword(newPassword, email );
                theForgetPassword.dispose();
            }
        });
    }
}
