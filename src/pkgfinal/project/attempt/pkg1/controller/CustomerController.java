/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.model.CustomerModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_HistoryOfPurchase;
import pkgfinal.project.attempt.pkg1.views.m.*;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Interface;

/**
 *
 * @author Enrico
 */
public class CustomerController 
{
    
    
    private CustomerView_Interface theInterface;
    private CustomerView_ChangeEmail theEditEmail;
    private CustomerView_ChangePassword theEditPassword;
    private CustomerView_AddBalance theAddBalance;
    private CustomerView_ChooseSeat theChooseSeat;
    private CustomerView_HistoryOfPurchase theHistory;
    private CustomerModel theModel;
    private AccountView_Login theLogin;
    
    private Connection conn = null;
    private Statement stmt = null;
    public static void main(String[] args){
        CustomerController m = new CustomerController(new CustomerView_Interface() ,new CustomerView_ChangeEmail() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() ,new CustomerView_HistoryOfPurchase(), new CustomerModel() );
    }
    public CustomerController(CustomerView_Interface theInterface, CustomerView_ChangeEmail theEditEmail, CustomerView_ChangePassword theEditPassword, CustomerView_AddBalance theAddBalance, CustomerView_ChooseSeat theChooseSeat, CustomerView_HistoryOfPurchase theHistory, CustomerModel theModel){
        this.theInterface = theInterface;
        this.theEditEmail = theEditEmail;
        this.theEditPassword = theEditPassword;
        this.theAddBalance = theAddBalance;
        this.theChooseSeat = theChooseSeat;
        this.theHistory = theHistory;
        this.theModel = theModel;
        start();
    }
    
    public void start(){
        theModel.establishConnection();
        buildInterfaceActionListener();
        buildChooseSeatActionListener();
        buildAddBalanceActionListener();
        buildChangeEmailActionListener();
        buildChangePasswordActionListener();
        buildHistoryActionListener();
        theInterface.setVisible(true);
        theInterface.txtName.setText(theModel.getName("yosuatest"));
        theInterface.txtDOB.setText(theModel.getDOB("yosuatest").toString());
        theInterface.txtEmail.setText(theModel.getEmail("yosuatest"));
        theInterface.txtBalance.setText(Integer.toString(theModel.getBalance("yosuatest")));
        
        theInterface.locationBox.setModel(new DefaultComboBoxModel<>(theModel.getCurrentShowingLocation()));
    }
       
    
    public void buildInterfaceActionListener(){
        theInterface.addChooseSeatListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theChooseSeat = new CustomerView_ChooseSeat();
                buildChooseSeatActionListener();
                theChooseSeat.setVisible(true);
            }
        });
        
        theInterface.addAddBalanceListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theAddBalance = new CustomerView_AddBalance();
               buildAddBalanceActionListener();
               theAddBalance.setVisible(true);
           } 
        });
        
        theInterface.addChangePasswordListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theEditPassword = new CustomerView_ChangePassword();
               buildChangePasswordActionListener();
               theEditPassword.setVisible(true);
               theModel.changePassword(theEditPassword.getOldPassword(), theEditPassword.getNewPassword());
           } 
        });
        
        theInterface.addChangeInformationListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theEditEmail = new CustomerView_ChangeEmail();
               buildChangeEmailActionListener();
               theEditEmail.setVisible(true);
               theModel.changeEmail(theEditEmail.getOldEmail(), theEditEmail.getNewEmail());
           } 
        });
        
        theInterface.addSignOutListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theInterface.dispose();
            }
        });
        
        theInterface.addHistoryListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theInterface.dispose();
                theHistory.setVisible(true);
            }
        });
        
        
        theInterface.addLocationListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theInterface.movieBox.setModel(new DefaultComboBoxModel<>(theModel.getCurrentShowingMovies(theInterface.getSelectedLocation())));
            }
        });
        theInterface.addMovieListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theInterface.timeBox.setModel(new DefaultComboBoxModel<>(theModel.getCurrentShowingTime(theInterface.getSelectedMovie(), theInterface.getSelectedLocation())));
            }
        });
        theInterface.addTimeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theInterface.btnChooseSeat.enable();
            }
        });
    }

    public void buildChooseSeatActionListener(){
        theChooseSeat.addContinueListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theChooseSeat.dispose();
               theInterface.setVisible(true);
           } 
        });
        
        theChooseSeat.addCancelActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theChooseSeat.dispose();
           } 
        });
    }
    
    public void buildAddBalanceActionListener(){
        theAddBalance.addContinueListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theAddBalance.dispose();
                theInterface.setVisible(true);
            }
        });
        
        theAddBalance.addCancelListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theAddBalance.dispose();
            }
        });
    }
    
    public void buildChangeEmailActionListener(){
        theEditEmail.addChangeListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditEmail.dispose();
                theInterface.setVisible(true);
            }
        });
        
        theEditEmail.addBackListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditEmail.dispose();
                theInterface.setVisible(true);
            }
        });
    }
    
    public void buildChangePasswordActionListener(){
        theEditPassword.addChangeListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditPassword.dispose();
                theInterface.setVisible(true);
            }
        });
        
        theEditPassword.addBackListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditPassword.dispose();
                theInterface.setVisible(true);
            }
        });
    }
    
    public void buildHistoryActionListener(){
        theHistory.addContinueListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theHistory.dispose();
                theInterface.setVisible(true);
            }
        });
    }
}
