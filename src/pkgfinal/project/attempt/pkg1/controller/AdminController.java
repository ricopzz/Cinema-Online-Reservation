/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;

import admin.AdminView_AddAccount;
import admin.AdminView_AddSchedule;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.model.AdminModel;
import pkgfinal.project.attempt.pkg1.model.Location;
import pkgfinal.project.attempt.pkg1.views.AccountView_ForgetPassword;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.AccountView_SignUp;
import pkgfinal.project.attempt.pkg1.views.admin.*;

/**
 *
 * @author Enrico
 */
public class AdminController {
    
    private AdminView_MainScreen theInterface;
    private AdminView_AddLocation theAddLocation;
    private AdminView_AddMovie theAddMovie;
    private AdminView_EditMovie theEditMovie;
    private AdminView_AddSchedule theAddSchedule;
    private AdminView_AddAccount theAddAccount;
    private AdminModel theModel;
    
    public static void main(String[] args){
        AdminController myAdminController = new AdminController(new AdminView_MainScreen(), new AdminView_AddLocation(), new AdminView_AddMovie(),new AdminView_EditMovie(), new AdminView_AddSchedule(), new AdminView_AddAccount(), new AdminModel());   
    }

    public AdminController(AdminView_MainScreen theInterface, AdminView_AddLocation theAddLocation, AdminView_AddMovie theAddMovie, AdminView_EditMovie theEditMovie, AdminView_AddSchedule theAddSchedule, AdminView_AddAccount theAddAccount, AdminModel theModel) {
        this.theInterface = theInterface;
        this.theAddLocation = theAddLocation;
        this.theAddMovie = theAddMovie;
        this.theEditMovie = theEditMovie;
        this.theAddSchedule = theAddSchedule;
        this.theAddAccount = theAddAccount;
        this.theModel = theModel;
        start();
    }
    
    
    public void start(){
        theModel.establishConnection();
        buildIntefaceListeners();
        buildAddMovieListeners();
        buildEditMovieListeners();
        buildAddLocationListeners();
        buildAddScheduleListeners();
        buildAddAccountListeners();
        theInterface.tblMovies.setModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
        theInterface.jTable2.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
        theInterface.jTable3.setModel(theModel.buildTableModel(theModel.getSchedulesResultSet()));
        theInterface.jTable4.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
        makePosters();
        theInterface.setVisible(true);
    }
    
    public void makePosters(){
        for (int x=0;x<theInterface.tblMovies.getRowCount();x++){
            theModel.makeMoviePoster((Integer)theInterface.tblMovies.getValueAt(x, 0));
        }
    }
    
    public void buildIntefaceListeners(){
        theInterface.addAddMoviesActionListeners(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                theAddMovie.setVisible(true);
            }
        });
        theInterface.addMovieTableClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                int index = Integer.parseInt(theInterface.getSelectedIDMovies());
                System.out.println(index);
               
                theInterface.setPoster(index);
            }
        });
        theInterface.addDeleteMovieActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDMovies();
                if (!(index == null)){
                    theModel.deleteMovies(index);
                    theInterface.tblMovies.setModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No movie selected");
                }
            }
        });
        theInterface.addMovieSearchKeyListenerer(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println(theInterface.getMovieSearchKey());
               theInterface.tblMovies.setModel(theModel.buildTableModel(theModel.getMoviesSearchResultSet(theInterface.getMovieSearchKey())));
            
            }
        });
        theInterface.addEditMovieActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDMovies();
                
                if (!(index == null)){
                    theEditMovie.setMovie(theModel.getMoviesSearchResultSet(Integer.toString(Integer.parseInt(index))));
                    theEditMovie.setChoosenIndex(Integer.parseInt(index));
                    theEditMovie.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"No movie selected");
                }
                
            }
        });
        
        theInterface.addAddLocationActionListeners(new ActionListener() {
            public void actionPerformed(ActionEvent e){
               
                theAddLocation.setVisible(true);
            }
        });
        
        theInterface.addDeleteLocationActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDLocations();
                if (index != null){
                    theModel.deleteLocations(theInterface.getSelectedIDLocations());
                    theInterface.jTable2.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No location selected");
                }   
            }   
        });
        
        theInterface.addEditLocationActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
                theAddLocation.setVisible(true);
            }
        });
        
        theInterface.addAddScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theInterface.dispose();
                theAddSchedule.setVisible(true);
            }
        });
        
        theInterface.addDeleteScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDSchedules();
                if (index != null){
                    theModel.deleteSchedules(theInterface.getSelectedIDSchedules());
                    theInterface.jTable3.setModel(theModel.buildTableModel(theModel.getSchedulesResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No schedule selected");
                }
            }
        });
        
        theInterface.addAddAccountActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                theAddAccount.setVisible(true);
            }
        });
        
        theInterface.addDeleteAccountActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String index = theInterface.getSelectedIDAccounts();
                if (index != null){
                    theModel.deleteAccount(theInterface.getSelectedIDAccounts());
                    theInterface.jTable4.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No accounts selected");
                }
            }
        });
        
    }
    
    public void buildAddMovieListeners(){
        theAddMovie.addAddMoviesActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if (theAddMovie.complete()){
                    theModel.addMovie(theAddMovie.getMovie());
                    theInterface.setMovIETabelModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                    theModel.makeMoviePoster((Integer)theInterface.tblMovies.getValueAt(theInterface.tblMovies.getRowCount(), 0));
                    theAddMovie.dispose();
                    theAddMovie = new AdminView_AddMovie();
                    buildAddMovieListeners();
                }else{
                    JOptionPane.showMessageDialog(null, "Not all fields filled");
                }
                
            }
        });
        theAddMovie.addChooseFileListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                if (c.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    theAddMovie.setChoosenFilePoster(c.getSelectedFile());
                }
            }
        });
    }
    
    public void buildEditMovieListeners(){
        System.out.println("phase 1");
        theEditMovie.addEditMoviesActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theEditMovie.complete()){
                    int choosenIndex = theEditMovie.getChoosenIndex();
                    theModel.editMovies(choosenIndex, theEditMovie.getMovie());
                
                    theInterface.setMovIETabelModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                    
                    theModel.makeMoviePoster(choosenIndex);
                    
                    theEditMovie.dispose();
                    theEditMovie = new AdminView_EditMovie();
                }else{
                    JOptionPane.showMessageDialog(theAddAccount, "Not all fields filled");
                }       
            }
        });
        theEditMovie.addChooseFileListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JFileChooser c = new JFileChooser();
                if (c.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    theEditMovie.setChoosenFilePoster(c.getSelectedFile());
                }
            }
        });
    }
    
    public void buildAddLocationListeners(){
        theAddLocation.addAddLocationActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String locationName = theAddLocation.getLocationName();
                String address = theAddLocation.getAddress();
                int theater_no = theAddLocation.getTheater_No();
                if (!(locationName.equals("") || address.equals("") || theater_no <0)){
                    theModel.addLocation(locationName, address, theater_no);
                    theInterface.jTable2.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                    theAddLocation.dispose();
                    buildAddLocationListeners();
                    theInterface.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                }  
            }
        });
    }
    
    public void buildAddScheduleListeners(){
        theAddSchedule.addAddScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theAddSchedule.dispose();
                theInterface.setVisible(true);
            }
        });
        
        theAddSchedule.addCancelScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theAddSchedule.dispose();
                theInterface.setVisible(true);
            }
        });
    }
    
    public void buildAddAccountListeners(){
        theAddAccount.addAddAccountActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String name = theAddAccount.getName();
                String email = theAddAccount.getEmail();
                Date dateOfBirth = theAddAccount.getDateOfBirth();
                String username = theAddAccount.getUsername();
                String password = theAddAccount.getPassword();
                String type = theAddAccount.getAccountType();
                int balance = 0;
                if (!(name.equals("") || email.equals("") || dateOfBirth.equals("") || username.equals("") || password.equals("")))
                {
                    theModel.addAccount(name,email,dateOfBirth.toString(),username,password,balance,type);
                    theInterface.jTable4.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
                    theAddAccount.dispose();
                    theAddAccount = new AdminView_AddAccount();
                    buildAddAccountListeners();
                }else{
                    JOptionPane.showMessageDialog(null, "Not all fields have been filled");
                }
                    
            }
        });
    }
    
}
