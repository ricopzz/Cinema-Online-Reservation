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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import pkgfinal.project.attempt.pkg1.model.AdminModel;
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
    private AdminView_AddVoucher theAddVoucher;
    private AdminModel theModel;
    
    private String currentUser ="";
    public static void main(String[] args){
        AdminController myAdminController = new AdminController("yosuatest19",new AdminView_MainScreen(), new AdminView_AddLocation(), new AdminView_AddMovie(),new AdminView_EditMovie(), new AdminView_AddSchedule(), new AdminView_AddAccount(),new AdminView_AddVoucher(), new AdminModel());   
    }

    public AdminController(String currentUser, AdminView_MainScreen theInterface, AdminView_AddLocation theAddLocation, AdminView_AddMovie theAddMovie, AdminView_EditMovie theEditMovie, AdminView_AddSchedule theAddSchedule, AdminView_AddAccount theAddAccount, AdminView_AddVoucher theAddVoucher, AdminModel theModel) {
        this.currentUser = currentUser;
        this.theInterface = theInterface;
        this.theAddLocation = theAddLocation;
        this.theAddMovie = theAddMovie;
        this.theEditMovie = theEditMovie;
        this.theAddSchedule = theAddSchedule;
        this.theAddAccount = theAddAccount;
        this.theAddVoucher = theAddVoucher;
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
        theInterface.tblLocation.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
        theInterface.tblSchedule.setModel(theModel.buildTableModel(theModel.getSchedulesResultSet()));
        theInterface.tblAccounts.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
        theInterface.tblVoucher.setModel(theModel.buildTableModel(theModel.getVouchersResultSet()));
        
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
                theAddMovie = new AdminView_AddMovie();
                buildAddMovieListeners();
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
                try{
                    String movie_id = theInterface.getSelectedIDMovies();
                    if (!(movie_id == null)){
                        ResultSet rs = theModel.getScheduleFromMovie(Integer.parseInt(movie_id));
                        if (rs.next()){
                            int choice = JOptionPane.showOptionDialog(null, 
                                        "Schedule found with the selected movie, delete those schedule as well ?", 
                                        "Movie still used", 
                                        JOptionPane.YES_NO_OPTION, 
                                        JOptionPane.QUESTION_MESSAGE, 
                                        null, null, null);
                            if (choice == JOptionPane.YES_OPTION){
                                ArrayList<Integer> schedule_ids = new ArrayList<Integer>();
                                schedule_ids.add(rs.getInt("ID"));
                                while(rs.next()){
                                    schedule_ids.add(rs.getInt("ID"));
                                }
                                for (int x=0;x<schedule_ids.size();x++){
                                    theModel.deleteSchedules(schedule_ids.get(x).toString());
                                }        
                                theModel.deleteMovies(movie_id);
                                theInterface.tblMovies.setModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                            }else{
                                JOptionPane.showMessageDialog(null, "No movie deleted");
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"No movie selected");
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        theInterface.addMovieSearchKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println(theInterface.getMovieSearchKey());
               theInterface.tblMovies.setModel(theModel.buildTableModel(theModel.getMoviesSearchResultSet(theInterface.getMovieSearchKey())));
            }
        });
        theInterface.addEditMovieActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditMovie = new AdminView_EditMovie();
                buildEditMovieListeners();
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
                theAddLocation = new AdminView_AddLocation();
                buildAddLocationListeners();
                theAddLocation.setVisible(true);
            }
        });
        theInterface.addDeleteLocationActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDLocations();
                if (index != null){
                    theModel.deleteLocations(theInterface.getSelectedIDLocations());
                    theInterface.tblLocation.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No location selected");
                }   
            }   
        });
        theInterface.addAddScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    theAddSchedule = new AdminView_AddSchedule();
                    buildAddScheduleListeners();
                    ResultSet rs = theModel.getMoviesResultSet();
                    
                    ArrayList<String> movies = new ArrayList<String>();
                    while(rs.next()){
                        movies.add(rs.getString("Name"));
                    }
                    
                    rs = theModel.getLocationsResultSet();
                    
                    ArrayList<String> locations = new ArrayList<String>();
                    while(rs.next()){
                        locations.add(rs.getString("Name"));
                    }
                    
                    theAddSchedule.setMovieComboBoxModel(movies.toArray(new String[0]));
                    theAddSchedule.setLocationComboBoxModel(locations.toArray(new String[0]));
                    
                    rs = theModel.getLocationsSearchResultSet(locations.get(0));
                    
                    System.out.println(locations.get(0));
                    int max = 1;
                    int min = 0;
                    
                    if(rs.next()){
                        max = rs.getInt("ID");
                        min = 1;
                    }
                    
                    theAddSchedule.setTheaterSpinnerModel(new SpinnerNumberModel(1, min, max, 1));
                    theAddSchedule.setVisible(true);
               
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theInterface.addLocationSearchKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println(theInterface.getLocationSearchKey());
               theInterface.tblLocation.setModel(theModel.buildTableModel(theModel.getLocationsSearchResultSet(theInterface.getLocationSearchKey())));
            }
        });
        theInterface.addDeleteScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String index = theInterface.getSelectedIDSchedules();
                if (index != null){
                    theModel.deleteSchedules(theInterface.getSelectedIDSchedules());
                    theInterface.tblSchedule.setModel(theModel.buildTableModel(theModel.getSchedulesResultSet()));
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
        theInterface.addAccountSearchKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println(theInterface.getAccountSearchKey());
               theInterface.tblAccounts.setModel(theModel.buildTableModel(theModel.getAccountsSearchResultSet(theInterface.getAccountSearchKey())));
            }
        });
        theInterface.addDeleteAccountActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String index = theInterface.getSelectedIDAccounts();
                if (index != null){
                    theModel.deleteAccount(theInterface.getSelectedIDAccounts());
                    theInterface.tblAccounts.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No accounts selected");
                }
            }
        });
        
        theInterface.addAddVouchersActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAddVoucher = new AdminView_AddVoucher();
                buildAddVoucherListeners();
                theAddVoucher.setVisible(true);
            }
        });
        theInterface.addVoucherSearchKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
               System.out.println(theInterface.getVoucherSearchKey());
               theInterface.tblVoucher.setModel(theModel.buildTableModel(theModel.getVouchersSearchResultSet(theInterface.getVoucherSearchKey())));
            }
        });
        theInterface.addDeleteVouchersActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = theInterface.getSelectedCodeVoucher();
                if (code != null){
                    theModel.deleteVoucher(code);
                    theInterface.tblVoucher.setModel(theModel.buildTableModel(theModel.getVouchersResultSet()));
                }else{
                    JOptionPane.showMessageDialog(null,"No voucher selected");
                }
            }
        });
        
    }
    
    public void buildAddMovieListeners(){
        theAddMovie.addAddMoviesActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                if (theAddMovie.complete()){
                    theModel.addMovie(theAddMovie.getMovie());
                    theInterface.setMovieTabelModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                    theModel.makeMoviePoster((Integer)theInterface.tblMovies.getValueAt(theInterface.tblMovies.getRowCount()-1, 0));
                    theAddMovie.dispose();
                    
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
                
                    theInterface.setMovieTabelModel(theModel.buildTableModel(theModel.getMoviesResultSet()));
                    
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
                    theInterface.tblLocation.setModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
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
                try {
                    System.out.println("test 2");
                    String movie = theAddSchedule.getMovie();
                    String location = theAddSchedule.getLocations();
                    Date date = theAddSchedule.getDate();
                    
                    System.out.println("opopop"  +date.getYear());
                    System.out.println(date.toString());
                    
                    Time time = theAddSchedule.getTime();
                    int theaterNumber = theAddSchedule.getTheaterNumber();
                    
                    boolean isPM = theAddSchedule.isPM();
                    if (isPM){
                        time.setHours(time.getHours() + 12);
                    }
                    boolean isRepeat = theAddSchedule.isRepeat();
                    
                    int repeatFor = theAddSchedule.getRepeatFor();
                    Time interval = theAddSchedule.getInterval();
                    Time []repetition = null;
                    if (isRepeat){
                        System.out.println("is repeat");
                        Time buffer = new Time(0,0,0);
                        buffer.setHours(time.getHours());
                        buffer.setMinutes(time.getMinutes());
                                
                        repetition = new Time[repeatFor];
                        for (int x=0;x<repeatFor;x++){
                            int newHour = buffer.getHours() + interval.getHours();
                            if (newHour > 24){
                                newHour = newHour -24;
                            }
                            buffer.setHours(newHour);
                            
                            int newMinute =  buffer.getMinutes() + interval.getMinutes();
                            if (newMinute > 59){
                                newMinute = newMinute - 60;
                            }
                            buffer.setMinutes(newMinute);
                            
                            repetition[x] = new Time(0,0,0);
                            repetition[x].setHours(buffer.getHours());
                            repetition[x].setMinutes(buffer.getMinutes());
                                    
                            
                            System.out.println(repetition[x].toString() + x);
                        }
                        for (int x=0;x<repeatFor;x++){
                            System.out.println(repetition[x].toString() + " this is");
                        }
                    }
                    ResultSet rs = theModel.getMoviesSearchResultSet(movie);
                    
                    int movieID = -1;
                    if (rs.next()){
                        movieID = rs.getInt("ID");
                    }else{
                        JOptionPane.showMessageDialog(null, "No such movie");
                    }
                    
                    rs = theModel.getLocationsSearchResultSet(location);
                    int locationID = -1;
                    if (rs.next()){
                        locationID = rs.getInt("ID");
                    }else{
                        JOptionPane.showMessageDialog(null, "No such location");
                    }
                    
                    if (locationID >= 0 || movieID >= 0){
                        System.out.println("hahahhaa");
                        theModel.addSchedules(date, time, locationID, movieID, theaterNumber );
                        if (isRepeat){
                            for(int x=0;x<repeatFor;x++){
                                theModel.addSchedules(date, repetition[x], locationID, movieID, theaterNumber);
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "An error occured, please checkk input");
                    }
                    System.out.println("success");
                    theInterface.tblSchedule.setModel(theModel.buildTableModel(theModel.getSchedulesResultSet()));
                    theAddSchedule.dispose();
                    theInterface.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theAddSchedule.addLocationActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedLocation = theAddSchedule.getLocations();
                    ResultSet rs = theModel.getLocationsSearchResultSet(selectedLocation);
                    int max = 1;
                    int min = 0;
                    if(rs.next()){
                        max = rs.getInt("theater_amount");
                        min = 0;
                    }
                    
                    theAddSchedule.setTheaterSpinnerModel(new SpinnerNumberModel(1, min, max, 1));
                } catch (SQLException ex) {
                    Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theAddSchedule.addCancelScheduleActionListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theAddSchedule.dispose();
                theInterface.setVisible(true);
            }
        });
        theAddSchedule.addRepeatActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAddSchedule.setRepeatPanelVisible(theAddSchedule.isRepeat());
            }
        });
        theAddSchedule.addTimeButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theAddSchedule.isPM()){
                    theAddSchedule.timeButtonSetText("PM");
                }else{
                    theAddSchedule.timeButtonSetText("AM");
                }
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
                    theInterface.tblAccounts.setModel(theModel.buildTableModel(theModel.getAccountsResultSet()));
                    theAddAccount.dispose();
                    theAddAccount = new AdminView_AddAccount();
                    buildAddAccountListeners();
                }else{
                    JOptionPane.showMessageDialog(null, "Not all fields have been filled");
                }
                    
            }
        });
    }
    
    public void buildAddVoucherListeners(){
        theAddVoucher.addAddVoucherActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = theAddVoucher.getAmount();
                int nominal = theAddVoucher.getNominal();
                for (int x=0;x<amount;x++){
                    if (!theModel.addVoucher(theModel.generateRandomCode(10), nominal)) x--;
                }
                theInterface.tblVoucher.setModel(theModel.buildTableModel(theModel.getVouchersResultSet()));
                JOptionPane.showMessageDialog(null, "Voucher added");
                
                theAddVoucher.dispose();
            }
        });
        theAddVoucher.addCancelScheduleActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAddVoucher.dispose();
            }
        });
        
    }
    
}
