/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller.Customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_HistoryOfPurchase;
import pkgfinal.project.attempt.pkg1.views.m.*;

/**
 *
 * @author Enrico
 */
public class CustomerController 
{
    
    
    private CustomerView_Interface theInterface;
    private CustomerView_ChangeInformation theEditInformation;
    private CustomerView_ChangePassword theEditPassword;
    private CustomerView_AddBalance theAddBalance;
    private CustomerView_ChooseSeat theChooseSeat;
    private CustomerView_HistoryOfPurchase theHistory;
    
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USER = "root";
    private final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;
    public static void main(String[] args){
        CustomerController m = new CustomerController(new CustomerView_Interface() ,new CustomerView_ChangeInformation() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() ,new CustomerView_HistoryOfPurchase() );
    }
    public CustomerController(CustomerView_Interface theInterface, CustomerView_ChangeInformation theEditInformation, CustomerView_ChangePassword theEditPassword, CustomerView_AddBalance theAddBalance, CustomerView_ChooseSeat theChooseSeat, CustomerView_HistoryOfPurchase theHistory){
        this.theInterface = theInterface;
        this.theEditInformation = theEditInformation;
        this.theEditPassword = theEditPassword;
        this.theAddBalance = theAddBalance;
        this.theChooseSeat = theChooseSeat;
        this.theHistory = theHistory;
        start();
    }
    
    public void start(){
        establishConnection();
        buildInterfaceActionListener();
        buildChooseSeatActionListener();
        buildAddBalanceActionListener();
        buildChangeInformationActionListener();
        buildChangePasswordActionListener();
        buildHistoryActionListener();
        theInterface.setVisible(true);
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
           } 
        });
        
        theInterface.addChangeInformationListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theEditInformation = new CustomerView_ChangeInformation();
               buildChangeInformationActionListener();
               theEditInformation.setVisible(true);
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
    
    public void buildChangeInformationActionListener(){
        theEditInformation.addChangeListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditInformation.dispose();
                theInterface.setVisible(true);
            }
        });
        
        theEditInformation.addBackListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditInformation.dispose();
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
