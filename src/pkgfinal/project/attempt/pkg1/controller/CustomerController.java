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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.model.CustomerModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_ChooseSeat;
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
    
    private String currentUser = "";
    public static void main(String[] args){
        
        CustomerController m = new CustomerController("yosuatest",new CustomerView_Interface() ,new CustomerView_ChangeEmail() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() ,new CustomerView_HistoryOfPurchase(), new CustomerModel() );
    }
    public CustomerController(String currentUser, CustomerView_Interface theInterface, CustomerView_ChangeEmail theEditEmail, CustomerView_ChangePassword theEditPassword, CustomerView_AddBalance theAddBalance, CustomerView_ChooseSeat theChooseSeat, CustomerView_HistoryOfPurchase theHistory, CustomerModel theModel){
        this.currentUser = currentUser;
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
        theInterface.txtName.setText(theModel.getName(currentUser));
        theInterface.txtDOB.setText(theModel.getDOB(currentUser).toString());
        theInterface.txtEmail.setText(theModel.getEmail(currentUser));
        theInterface.txtBalance.setText(Integer.toString(theModel.getBalance(currentUser)));
        
        setLocationComboBox();
        
    }
       
    public String[] toArray (ArrayList<String> a){
        String[] b = new String[a.size()];
        for (int x=0;x<a.size();x++){
            b[x] = a.get(x);
        }
        return b;
    }
    public void buildInterfaceActionListener(){
        theInterface.addChooseSeatListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theChooseSeat = new CustomerView_ChooseSeat();
                int location_id = theInterface.getCurrentLocationID().get(theInterface.getSelectedLocation());
                int movie_id = theInterface.getCurrentMovieID().get(theInterface.getSelectedMovie());
                String time = theInterface.getSelectedTime();
                int theater = theInterface.getSelectedTheater();
                
                int schedule_id = theModel.getScheduleID(location_id, movie_id, time, theater);         
                System.out.println("schedule id" + schedule_id);
                ArrayList<String> seats = theModel.getTakenSeats(schedule_id);
                theChooseSeat.setScheduleID(schedule_id);
                
                theChooseSeat.setSeats(seats);
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
                // resets all the boxes
                
                theInterface.setMovieComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theInterface.setTimeComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theInterface.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theInterface.enableChooseSeat(false);
                
                try {
                    int index  = theInterface.getSelectedLocation();
                    int location_id = theInterface.getCurrentLocationID().get(index);
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet(location_id);
                    ArrayList<Integer> movie_id = new ArrayList<Integer>();
                    while (rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!movie_id.contains(id))
                            movie_id.add(id);
                    }
                    
                    theInterface.setCurrentMovieID(movie_id);
                    ArrayList<String> movie_name = new ArrayList<String>();
                    for (int x=0;x<movie_id.size();x++){
                        String name = theModel.getMovieNames(movie_id.get(x));
                        System.out.println(name);
                        movie_name.add(name);
                    }
                    
                    theInterface.setMovieComboBoxModel(new DefaultComboBoxModel<>(toArray(movie_name)));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
}
        });
        theInterface.addMovieListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    theInterface.setTimeComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theInterface.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theInterface.enableChooseSeat(false);
                    int location_id = theInterface.getCurrentLocationID().get(theInterface.getSelectedLocation());
                    int movie_id = theInterface.getCurrentMovieID().get(theInterface.getSelectedMovie());
                    
                    ResultSet rs = theModel.getCurrentShowingTime(movie_id, location_id);
                    ArrayList<String> times = new ArrayList<String>();
                    while (rs.next()){
                        String b = rs.getTime("Time").toString();
                        
                        times.add(b);
                    }
                    theInterface.setTimeComboBoxModel(new DefaultComboBoxModel<>(toArray(times)));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        theInterface.addTimeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theInterface.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theInterface.enableChooseSeat(false);
                    
                    int location_id = theInterface.getCurrentLocationID().get(theInterface.getSelectedLocation());
                    int movie_id = theInterface.getCurrentMovieID().get(theInterface.getSelectedMovie());
                    String time = theInterface.getSelectedTime();
                    
                    ResultSet rs = theModel.getCurrentShowingTheater(location_id, movie_id, time);
                    ArrayList<String> theater = new ArrayList<String>();
                    
                    while(rs.next()){
                        int theater_no = rs.getInt("Theater_Number");
                        System.out.println(theater_no);
                        theater.add(Integer.toString(theater_no));
                    }
                    theInterface.setTheaterComboBoxModel(new DefaultComboBoxModel<>(toArray(theater)));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theInterface.addTheaterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                theInterface.enableChooseSeat(true);
                
            }
        });
    }

    public void buildChooseSeatActionListener(){
        theChooseSeat.addContinueListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               int schedule_id = theChooseSeat.getScheduleID();
               String username = currentUser;
               java.util.Date currentDate = new java.util.Date();
               Date date_of_purchase = new Date(currentDate.getTime());
               Time time_of_purchase = new Time(currentDate.getTime());
               
               ArrayList<String> allSelectedSeats = theChooseSeat.getSelectedSeats();
               ArrayList<String> takenSeats = theModel.getTakenSeats(schedule_id);
               System.out.println(allSelectedSeats.toString());
               System.out.println(takenSeats.toString());
               for (int x=0;x<takenSeats.size();x++){
                   allSelectedSeats.remove(takenSeats.get(x));
               }
               
               for (int x=0;x<allSelectedSeats.size();x++){
                   theModel.addPurchase(schedule_id, currentUser, date_of_purchase, time_of_purchase, allSelectedSeats.get(x));
               }
               
               theChooseSeat.dispose();
               theChooseSeat = new CustomerView_ChooseSeat();
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

    private void setLocationComboBox() {
        try {
            ResultSet rs = theModel.getCurrentShowingLocationIDResultSet();
            ArrayList<Integer> location_id = new ArrayList<Integer>();
            while(rs.next()){
                int id = rs.getInt("Location_ID");
                if (!location_id.contains(id))
                    location_id.add(id);
            }
            
           
            
            ArrayList<String> location_name      = new ArrayList<String>();
            for (int x=0;x<location_id.size();x++){
                location_name.add(theModel.getLocationNames(location_id.get(x)));
            }
            theInterface.setLocationComboBoxModel(new DefaultComboBoxModel<>(location_name.toArray(new String[0])));
            theInterface.setCurrentLocationID(location_id);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
