/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Vector;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.model.CustomerModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_ForgetPassword;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.AccountView_SignUp;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Accounts;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_BookTicket;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_ChooseSeat;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Cinemas;
import pkgfinal.project.attempt.pkg1.views.m.*;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Interface;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Interface_V2;
import pkgfinal.project.attempt.pkg1.views.customer.CustomerView_Movies;

/**
 *
 * @author Enrico
 */
public class CustomerController 
{
    
    //old
    //private CustomerView_Interface theInterface;
    //new
    private CustomerView_ChangeEmail theEditEmail;
    private CustomerView_ChangePassword theEditPassword;
    private CustomerView_AddBalance theAddBalance;
    private CustomerView_ChooseSeat theChooseSeat;
    private CustomerModel theModel;
    
    private CustomerView_Interface_V2 theInterface2 = new CustomerView_Interface_V2();
    private CustomerView_Movies theMovie = new CustomerView_Movies();
    private CustomerView_Cinemas theCinema = new CustomerView_Cinemas();
    private CustomerView_BookTicket theTicket = new CustomerView_BookTicket();
    private CustomerView_Accounts theAccount = new CustomerView_Accounts();
    
    private String currentUser = "";
    public static void main(String[] args){
        CustomerController m = new CustomerController("yosuatest19",new CustomerView_Interface() ,new CustomerView_ChangeEmail() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() , new CustomerModel() );
    }
    public CustomerController(String currentUser, CustomerView_Interface theInterface, CustomerView_ChangeEmail theEditEmail, CustomerView_ChangePassword theEditPassword, CustomerView_AddBalance theAddBalance, CustomerView_ChooseSeat theChooseSeat, CustomerModel theModel){
        this.currentUser = currentUser;
        //this.theInterface = theInterface;
        this.theEditEmail = theEditEmail;
        this.theEditPassword = theEditPassword;
        this.theAddBalance = theAddBalance;
        this.theChooseSeat = theChooseSeat;
        this.theModel = theModel;
        start();
    }
    
    public void start(){
        theModel.establishConnection();
        buildChooseSeatActionListener();
        buildAddBalanceActionListener();
        buildChangeEmailActionListener();
        buildChangePasswordActionListener();
        
        // new
        buildInterface2ActionListener();
        theInterface2.setVisible(true);
        
        makePosters();
        
    }
       
   
    public void buildInterface2ActionListener(){
        theInterface2.addCinemasListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theCinema = new CustomerView_Cinemas();
                theCinema.setCinemaTableModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                buildCinemasActionListeners();
                theCinema.setVisible(true);
                theInterface2.dispose();
            }
        });
        theInterface2.addMoviesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theMovie = new CustomerView_Movies();
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    System.out.println(id_list.toString());
                    Vector<Vector<String>> data = new Vector<Vector<String>>();
                    
                    for (int x=0;x<id_list.size();x++){
                        Vector<String> entry = new Vector<String>();
                        entry.add(theModel.getMovieNames(id_list.get(x)));
                        entry.add(theModel.getMovieDuration(id_list.get(x)));
                        data.add(entry);
                    }
                    
                    Vector<String> columnNames = new Vector<String>();
                    columnNames.add("Name");
                    columnNames.add("Duration");
                    theMovie.setMovieTableModel(new DefaultTableModel(data, columnNames));
                    
                    buildMoviesActionListener();
                    theMovie.setVisible(true);
                    
                    theInterface2.dispose();
                    } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theInterface2.addTicketsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                theTicket = new CustomerView_BookTicket();
                buildTicketsActionListeners();
                try {
                    ResultSet rs = theModel.getCurrentShowingLocationIDResultSet();
                    ArrayList<Integer> location_id = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Location_ID");
                        if (!location_id.contains(id))
                            location_id.add(id);
                    }

                    ArrayList<String> location_name = new ArrayList<String>();
                    for (int x=0;x<location_id.size();x++){
                        location_name.add(theModel.getLocationNames(location_id.get(x)));
                    }
                    theTicket.setLocationComboBoxModel(new DefaultComboBoxModel<>(location_name.toArray(new String[0])));
                    theTicket.setCurrentLocationID(location_id);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                theTicket.setBalanceLabel(theModel.getBalance(currentUser));
                theTicket.setVisible(true);
                
                theInterface2.dispose();
            }

            
        });
        theInterface2.addAccountListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(currentUser);
                theInterface2.dispose();
                buildAccountActionListener();
                theAccount.setVisible(true);
                theAccount.setHistoryTableModel(theModel.buildTableModel(theModel.getHistoryOfPurchase(currentUser)));
                theAccount.setTxtName(theModel.getName(currentUser));
                theAccount.setTxtBirthDate(theModel.getDOB(currentUser).toString());
                theAccount.setTxtEmail(theModel.getEmail(currentUser));
                theAccount.setTxtBalance(Integer.toString(theModel.getBalance(currentUser)));
            }
        });
        theInterface2.addSignOutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theInterface2.dispose();
                AccountController myAccountController = new AccountController(new AccountModel(),
                new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
            }
        });
    }
    
    public void buildTicketsActionListeners() {
        theTicket.addLocationListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // resets all the boxes
                
                theTicket.setMovieComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theTicket.setTimeComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theTicket.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                theTicket.enableChooseSeat(false);
                
                try {
                    int index  = theTicket.getSelectedLocation();
                    int location_id = theTicket.getCurrentLocationID().get(index);
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet(location_id);
                    ArrayList<Integer> movie_id = new ArrayList<Integer>();
                    while (rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!movie_id.contains(id)){
                            movie_id.add(id);
                            theModel.makeMoviePoster(id);
                        }
                    }
                    
                    theTicket.setCurrentMovieID(movie_id);
                    ArrayList<String> movie_name = new ArrayList<String>();
                    for (int x=0;x<movie_id.size();x++){
                        String name = theModel.getMovieNames(movie_id.get(x));
                        System.out.println(name);
                        movie_name.add(name);
                    }
                    
                    theTicket.setMovieComboBoxModel(new DefaultComboBoxModel<>(toArray(movie_name)));
                    // set movie info
                    rs = theModel.getCurrentShowingLocationIDResultSet();
                    ArrayList<Integer> location_ids = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Location_ID");
                        if (!location_ids.contains(id))
                            location_ids.add(id);
                    }

                    rs = theModel.getLocationsResultSet(location_ids.get(index));
                    rs.next();
                    String locationName = rs.getString("Name");
                    String address = rs.getString("Address");
                    
                    theTicket.setlblLocationNameText(locationName);
                    theTicket.setlblAddressText(address);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
}
        });
        theTicket.addMovieListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    theTicket.setTimeComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theTicket.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theTicket.enableChooseSeat(false);
                    int location_id = theTicket.getCurrentLocationID().get(theTicket.getSelectedLocation());
                    int movie_id = theTicket.getCurrentMovieID().get(theTicket.getSelectedMovie());
                    
                    theTicket.setPoster(movie_id);
                    ResultSet rs = theModel.getCurrentShowingTime(movie_id, location_id);
                    ArrayList<String> times = new ArrayList<String>();
                    while (rs.next()){
                        String b = rs.getTime("Time").toString();
                        
                        times.add(b);
                    }
                    theTicket.setTimeComboBoxModel(new DefaultComboBoxModel<>(toArray(times)));
                    // set movie info
                    rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    int index = theTicket.getSelectedMovieIndex();
                    theTicket.setPoster(id_list.get(index));
                    
                    rs = theModel.getMoviesData(id_list.get(index));
                    rs.next();
                    String movieName = rs.getString("Name");
                    String director = rs.getString("Director");
                    String genres = rs.getString("G1")  +", " +  rs.getString("G2") +", " + rs.getString("G3");
                    double rating = rs.getDouble("Rating");
                    Time duration = rs.getTime("Duration");
                    
                    theTicket.setlblMovieNameText(movieName);
                    theTicket.setlblDirectorNameText("Director: "+director);
                    theTicket.setlblGenreNameText(genres);
                    theTicket.setlblRatingNameText("Rating: "+rating + "/10");
                    theTicket.setlblDurationNameText("Duration: " + duration.getHours() + " hour "+ duration.getMinutes() + " minutes");
                    
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        theTicket.addViewTrailerListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    int index = theTicket.getSelectedMovieIndex();
                    theTicket.setPoster(id_list.get(index));
                    
                    rs = theModel.getMoviesData(id_list.get(index));
                    rs.next();
                    
                    String url = rs.getString("Trailer_URL");
                    if (!url.equals("")){
                        openWebpage(new URL(url));
                    }else{
                        JOptionPane.showMessageDialog(null, "No trailer available");
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theTicket.addTimeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theTicket.setTheaterComboBoxModel(new DefaultComboBoxModel<>(new String[]{"--"}));
                    theTicket.enableChooseSeat(false);
                    
                    int location_id = theTicket.getCurrentLocationID().get(theTicket.getSelectedLocation());
                    int movie_id = theTicket.getCurrentMovieID().get(theTicket.getSelectedMovie());
                    String time = theTicket.getSelectedTime();
                    
                    ResultSet rs = theModel.getCurrentShowingTheater(location_id, movie_id, time);
                    ArrayList<String> theater = new ArrayList<String>();
                    
                    while(rs.next()){
                        int theater_no = rs.getInt("Theater_Number");
                        System.out.println(theater_no);
                        theater.add(Integer.toString(theater_no));
                    }
                    theTicket.setTheaterComboBoxModel(new DefaultComboBoxModel<>(toArray(theater)));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theTicket.addTheaterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theChooseSeat = new CustomerView_ChooseSeat();
                int location_id = theTicket.getCurrentLocationID().get(theTicket.getSelectedLocation());
                int movie_id = theTicket.getCurrentMovieID().get(theTicket.getSelectedMovie());
                String time = theTicket.getSelectedTime();
                int theater = theTicket.getSelectedTheater();
                
                int schedule_id = theModel.getScheduleID(location_id, movie_id, time, theater);         
                System.out.println("schedule id" + schedule_id);
                ArrayList<String> seats = theModel.getTakenSeats(schedule_id);
                theChooseSeat.setScheduleID(schedule_id);
                
                theChooseSeat.setSeats(seats);
                buildChooseSeatActionListener();
                
                theTicket.enableChooseSeat(true);
                
            }
        });
        theTicket.addChooseSeatListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                theChooseSeat.setVisible(true);
                
            }
        });
        theTicket.addBookTicketListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (theTicket.isChooseSeatEnabled()){
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
                    if (!allSelectedSeats.isEmpty()){
                        // purchase
                        int price = allSelectedSeats.size() * 70000;
                        int userBalance = theModel.getBalance(currentUser) - price;
                        if (userBalance >= 0){
                            for (int x=0;x<allSelectedSeats.size();x++){
                               theModel.addPurchase(schedule_id, currentUser, date_of_purchase, time_of_purchase, allSelectedSeats.get(x));
                            }
                            System.out.println(userBalance);
                            theModel.setBalance(currentUser, userBalance);
                            JOptionPane.showMessageDialog(null, "Purchase succesful, " + allSelectedSeats.size() + " were bought.\n  Total : Rp"+price);
                            // build choose seat again
                            theChooseSeat.dispose();
                            theChooseSeat = new CustomerView_ChooseSeat();
                            int location_id = theTicket.getCurrentLocationID().get(theTicket.getSelectedLocation());
                            int movie_id = theTicket.getCurrentMovieID().get(theTicket.getSelectedMovie());
                            String time = theTicket.getSelectedTime();
                            int theater = theTicket.getSelectedTheater();       
                            System.out.println("schedule id" + schedule_id);
                            ArrayList<String> seats = theModel.getTakenSeats(schedule_id);
                            theChooseSeat.setScheduleID(schedule_id);

                            theChooseSeat.setSeats(seats);
                            buildChooseSeatActionListener();
                        }else{
                            JOptionPane.showMessageDialog(null, "Insufficient balance");
                        }   
                    }else{
                        JOptionPane.showMessageDialog(null, "No seats selected");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please select options");
                }

            }
        });
        
        theTicket.addCinemasListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theCinema = new CustomerView_Cinemas();
                theCinema.setCinemaTableModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                buildCinemasActionListeners();
                theCinema.setVisible(true);
                theTicket.dispose();
            }
        });
        theTicket.addMoviesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theMovie = new CustomerView_Movies();
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    System.out.println(id_list.toString());
                    Vector<Vector<String>> data = new Vector<Vector<String>>();
                    
                    for (int x=0;x<id_list.size();x++){
                        Vector<String> entry = new Vector<String>();
                        entry.add(theModel.getMovieNames(id_list.get(x)));
                        entry.add(theModel.getMovieDuration(id_list.get(x)));
                        data.add(entry);
                    }
                    
                    Vector<String> columnNames = new Vector<String>();
                    columnNames.add("Name");
                    columnNames.add("Duration");
                    theMovie.setMovieTableModel(new DefaultTableModel(data, columnNames));
                    
                    buildMoviesActionListener();
                    theMovie.setVisible(true);
                    
                    theTicket.dispose();
                    } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        theTicket.addAccountListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theTicket.dispose();
                theAccount = new CustomerView_Accounts();
                buildAccountActionListener();
                theAccount.setHistoryTableModel(theModel.buildTableModel(theModel.getHistoryOfPurchase(currentUser)));
                theAccount.setVisible(true);
                theAccount.setTxtName(theModel.getName(currentUser));
                theAccount.setTxtBirthDate(theModel.getDOB(currentUser).toString());
                theAccount.setTxtEmail(theModel.getEmail(currentUser));
                theAccount.setTxtBalance(Integer.toString(theModel.getBalance(currentUser)));

            }
        });
        theTicket.addSignOutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theTicket.dispose();
                AccountController myAccountController = new AccountController(new AccountModel(),
                new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
            }
        });
        
    }
    public void buildChooseSeatActionListener(){
        theChooseSeat.addContinueListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theChooseSeat.setVisible(false);
           } 
        });
        theChooseSeat.addCancelActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               theChooseSeat.dispose();
           } 
        });
    }
    
    public void buildMoviesActionListener(){
        theMovie.addViewTrailerListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    int index = theMovie.getSelectedMovieIndex();
                    theMovie.setPoster(id_list.get(index));
                    
                    rs = theModel.getMoviesData(id_list.get(index));
                    rs.next();
                    
                    String url = rs.getString("Trailer_URL");
                    openWebpage(new URL(url));
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theMovie.addMovieTableClickListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    System.out.println("movie table clicked");
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    int index = theMovie.getSelectedMovieIndex();
                    theMovie.setPoster(id_list.get(index));
                    
                    rs = theModel.getMoviesData(id_list.get(index));
                    rs.next();
                    String movieName = rs.getString("Name");
                    String director = rs.getString("Director");
                    String genres = rs.getString("G1")  +", " +  rs.getString("G2") +", " + rs.getString("G3");
                    double rating = rs.getDouble("Rating");
                    Time duration = rs.getTime("Duration");
                    
                    theMovie.setlblMovieNameText(movieName);
                    theMovie.setlblDirectorNameText("Director: "+director);
                    theMovie.setlblGenreNameText(genres);
                    theMovie.setlblRatingNameText("Rating: "+rating + "/10");
                    theMovie.setlblDurationNameText("Duration: " + duration.getHours() + " hour "+ duration.getMinutes() + " minutes");
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        theMovie.addCinemasListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theCinema = new CustomerView_Cinemas();
                theCinema.setCinemaTableModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                buildCinemasActionListeners();
                theCinema.setVisible(true);
                theMovie.dispose();
            }
        });
        theMovie.addTicketsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                theTicket = new CustomerView_BookTicket();
                buildTicketsActionListeners();
                try {
                    ResultSet rs = theModel.getCurrentShowingLocationIDResultSet();
                    ArrayList<Integer> location_id = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Location_ID");
                        if (!location_id.contains(id))
                            location_id.add(id);
                    }

                    ArrayList<String> location_name = new ArrayList<String>();
                    for (int x=0;x<location_id.size();x++){
                        location_name.add(theModel.getLocationNames(location_id.get(x)));
                    }
                    theTicket.setLocationComboBoxModel(new DefaultComboBoxModel<>(location_name.toArray(new String[0])));
                    theTicket.setCurrentLocationID(location_id);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                theTicket.setBalanceLabel(theModel.getBalance(currentUser));
                theTicket.setVisible(true);
                
                theMovie.dispose();
            }

            
        });
        theMovie.addAccountListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAccount = new CustomerView_Accounts();
                theAccount.setHistoryTableModel(theModel.buildTableModel(theModel.getHistoryOfPurchase(currentUser)));

                theAccount.setVisible(true);
                theAccount.setTxtName(theModel.getName(currentUser));
                theAccount.setTxtBirthDate(theModel.getDOB(currentUser).toString());
                theAccount.setTxtEmail(theModel.getEmail(currentUser));
                theAccount.setTxtBalance(Integer.toString(theModel.getBalance(currentUser)));
                buildAccountActionListener();
                theMovie.dispose();
            }
        });
        theMovie.addSignOutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theMovie.dispose();
                AccountController myAccountController = new AccountController(new AccountModel(),
                new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
            }
        });
    }
    
    public void buildCinemasActionListeners(){
        theCinema.addMoviesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theMovie = new CustomerView_Movies();
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    System.out.println(id_list.toString());
                    Vector<Vector<String>> data = new Vector<Vector<String>>();
                    
                    for (int x=0;x<id_list.size();x++){
                        Vector<String> entry = new Vector<String>();
                        entry.add(theModel.getMovieNames(id_list.get(x)));
                        entry.add(theModel.getMovieDuration(id_list.get(x)));
                        data.add(entry);
                    }
                    
                    Vector<String> columnNames = new Vector<String>();
                    columnNames.add("Name");
                    columnNames.add("Duration");
                    theMovie.setMovieTableModel(new DefaultTableModel(data, columnNames));
                    
                    buildMoviesActionListener();
                    theMovie.setVisible(true);
                    
                    theCinema.dispose();
                    } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theCinema.addTicketsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theCinema.dispose();
                theTicket = new CustomerView_BookTicket();
                buildTicketsActionListeners();
                try {
                    ResultSet rs = theModel.getCurrentShowingLocationIDResultSet();
                    ArrayList<Integer> location_id = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Location_ID");
                        if (!location_id.contains(id))
                            location_id.add(id);
                    }

                    ArrayList<String> location_name = new ArrayList<String>();
                    for (int x=0;x<location_id.size();x++){
                        location_name.add(theModel.getLocationNames(location_id.get(x)));
                    }
                    theTicket.setLocationComboBoxModel(new DefaultComboBoxModel<>(location_name.toArray(new String[0])));
                    theTicket.setCurrentLocationID(location_id);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                theTicket.setBalanceLabel(theModel.getBalance(currentUser));
                theTicket.setVisible(true);
            }

            
        });
        theCinema.addAccountListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                theAccount = new CustomerView_Accounts();
                                theAccount.setHistoryTableModel(theModel.buildTableModel(theModel.getHistoryOfPurchase(currentUser)));

                buildAccountActionListener();
                theAccount.setVisible(true);
                theAccount.setTxtName(theModel.getName(currentUser));
                theAccount.setTxtBirthDate(theModel.getDOB(currentUser).toString());
                theAccount.setTxtEmail(theModel.getEmail(currentUser));
                theAccount.setTxtBalance(Integer.toString(theModel.getBalance(currentUser)));
                theCinema.dispose();

            }
        });
    } 
    
    public void buildAccountActionListener(){
        theAccount.addCinemasListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theCinema = new CustomerView_Cinemas();
                theCinema.setCinemaTableModel(theModel.buildTableModel(theModel.getLocationsResultSet()));
                buildCinemasActionListeners();
                theCinema.setVisible(true);
                theAccount.dispose();
            }
        });
        theAccount.addMoviesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theMovie = new CustomerView_Movies();
                    ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
                    ArrayList<Integer> id_list = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Movie_ID");
                        if (!id_list.contains(id))
                            id_list.add(id);
                    }
                    System.out.println(id_list.toString());
                    Vector<Vector<String>> data = new Vector<Vector<String>>();
                    
                    for (int x=0;x<id_list.size();x++){
                        Vector<String> entry = new Vector<String>();
                        entry.add(theModel.getMovieNames(id_list.get(x)));
                        entry.add(theModel.getMovieDuration(id_list.get(x)));
                        data.add(entry);
                    }
                    
                    Vector<String> columnNames = new Vector<String>();
                    columnNames.add("Name");
                    columnNames.add("Duration");
                    theMovie.setMovieTableModel(new DefaultTableModel(data, columnNames));
                    
                    buildMoviesActionListener();
                    theMovie.setVisible(true);
                    
                    theAccount.dispose();
                    } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        theAccount.addTicketsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                theTicket = new CustomerView_BookTicket();
                buildTicketsActionListeners();
                try {
                    ResultSet rs = theModel.getCurrentShowingLocationIDResultSet();
                    ArrayList<Integer> location_id = new ArrayList<Integer>();
                    while(rs.next()){
                        int id = rs.getInt("Location_ID");
                        if (!location_id.contains(id))
                            location_id.add(id);
                    }

                    ArrayList<String> location_name = new ArrayList<String>();
                    for (int x=0;x<location_id.size();x++){
                        location_name.add(theModel.getLocationNames(location_id.get(x)));
                    }
                    theTicket.setLocationComboBoxModel(new DefaultComboBoxModel<>(location_name.toArray(new String[0])));
                    theTicket.setCurrentLocationID(location_id);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                theTicket.setBalanceLabel(theModel.getBalance(currentUser));
                theTicket.setVisible(true);
                
                theAccount.dispose();
            }

            
        });
        
        theAccount.addChangePasswordListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theEditPassword =  new CustomerView_ChangePassword();
                buildChangePasswordActionListener();
                theEditPassword.setVisible(true);
            }
        });
        theAccount.addChangeEmailListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theEditEmail = new CustomerView_ChangeEmail();
                buildChangeEmailActionListener();
                theEditEmail.setVisible(true);
            }
        });
        theAccount.addTopUpListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAddBalance = new CustomerView_AddBalance();
                buildAddBalanceActionListener();
                theAddBalance.setVisible(true);
            }
        });
        theAccount.addSignOutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theAccount.dispose();
                AccountController myAccountController = new AccountController(new AccountModel(),
                new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
            }
        });
    }
    public void buildAddBalanceActionListener(){
        theAddBalance.addContinueListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String code = theAddBalance.getCode();
                int nominal = theModel.redeemVoucher(code);
                if (nominal != 0){
                    theModel.setBalance(currentUser, theModel.getBalance(currentUser)  + nominal );
                    JOptionPane.showMessageDialog(null, "Top up succesful");
                    
                    theAccount.setHistoryTableModel(theModel.buildTableModel(theModel.getHistoryOfPurchase(currentUser)));
                    theAccount.setTxtBalance(Integer.toString(theModel.getBalance(currentUser)));
                buildAccountActionListener();
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid redeem code");
                }
                    
                theAddBalance.dispose();
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
                String newEmail = theEditEmail.getNewEmail();
                if (!newEmail.equals("")){
                    if (theModel.changeEmail(currentUser, newEmail)){
                        theEditEmail.dispose();
                        JOptionPane.showMessageDialog(null, "Email successfully changed !");
                    }else{
                        JOptionPane.showMessageDialog(null, "Email entered exists");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                }
            }
        });
        
        theEditEmail.addBackListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditEmail.dispose();
            }
        });
    }
    public void buildChangePasswordActionListener(){
        theEditPassword.addChangeListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String currentPassword = theEditPassword.getOldPassword();
                String newPassword = theEditPassword.getNewPassword();
                if (!(currentPassword.equals("") || newPassword.equals(""))){
                    if (theModel.changePassword(currentPassword, newPassword, currentUser)){
                        System.out.println("password change success");
                        JOptionPane.showMessageDialog(null, "Password succesfully changed !");
                        theEditPassword.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Incorrect entry for old password");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                }
            }
        });
        theEditPassword.addBackListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                theEditPassword.dispose();
            }
        });
    }
    
    public String[] toArray (ArrayList<String> a){
        String[] b = new String[a.size()];
        for (int x=0;x<a.size();x++){
            b[x] = a.get(x);
        }
        return b;
    }
    public void makePosters(){
        try {
            ResultSet rs = theModel.getCurrentShowingMovieIDResultSet();
            ArrayList<Integer> id_list = new ArrayList<Integer>();
            while(rs.next()){
                int id = rs.getInt("Movie_ID");
                if (!id_list.contains(id))
                    id_list.add(id);
            }
            for (int x=0;x<id_list.size();x++){
                theModel.makeMoviePoster(id_list.get(x));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}