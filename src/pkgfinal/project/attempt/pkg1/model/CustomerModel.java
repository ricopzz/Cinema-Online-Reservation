/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pkgfinal.project.attempt.pkg1.controller.AccountController;

/**
 *
 * @author Enrico
 */
public class CustomerModel {
    
    
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USER = "root";
    private final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;
    
    private String randomCode = null;

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
    

    public void establishConnection() {
        try {
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = (Statement) conn.createStatement();
            stmt.executeQuery("USE final_project_programming_languages_db");
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error establishing connection to database");
        }
    }
    
    public CustomerModel(){
        
    }
    
    public void addPurchase(int schedule_id, String username, Date date_of_purchase, Time time_of_purchase, String seat){
        try {
            String query = "INSERT INTO Purchase_History (`Schedule_ID`, `Username`, `Date_Of_Purchase`, `Time_Of_Purchase`, `Seat_No`, `Claimed`) "
                    + "VALUES (" + schedule_id + ",'" + username + "','" + date_of_purchase.toString() + "','" + time_of_purchase.toString() +"','" + seat+"',"+"0)";
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ResultSet getCurrentShowingTime(int movie_id,int location_id){
        ArrayList<String> time = new ArrayList<String>();
        try{
            String query = "SELECT Time FROM Schedule WHERE Movie_ID = '" + movie_id + "' AND Location_ID ='" + location_id+"'";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    public ResultSet getCurrentShowingMovieIDResultSet(int location_id){
        try{
            String query = "SELECT Movie_ID FROM Schedule WHERE Location_ID = '" + location_id + "'";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public ResultSet getCurrentShowingLocationIDResultSet(){
        try{
            String query = "SELECT Location_ID FROM Schedule";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public ResultSet getCurrentShowingTheater(int location_id, int movie_id, String time) {
        ArrayList<String> theater = new ArrayList<String>();
        try{
            String query = "SELECT Theater_Number FROM Schedule WHERE Movie_ID = '" + movie_id + "' AND Location_ID ='" + location_id+"' AND Time ='"+time+"'";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int getScheduleID(int location_id, int movie_id, String time, int theater_number){
        try {
            String query = "SELECT ID FROM Schedule WHERE Movie_ID = '" + movie_id + "' AND Location_ID ='" + location_id+"' AND Time ='"+time+"' AND Theater_Number='" + theater_number+"'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getInt("ID");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String getMovieNames(Integer index) {
        try {
            String query = "SELECT * FROM Movies WHERE ID = " + index;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getString("Name");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getLocationNames(Integer index) {
        try {
            String query = "SELECT * FROM Location WHERE ID = " + index;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getString("Name");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getName(String username){
        try{
            String query = "SELECT * FROM Accounts WHERE Username = '" + username +"'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getString("Name");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public Date getDOB(String username){
        try{
            String query = "SELECT * FROM Accounts WHERE Username = '" + username+ "'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            
            return rs.getDate("DOB");
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    public String getEmail(String username){
        try{
            String query = "SELECT * FROM Accounts WHERE Username = '" + username +"'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getString("Email");
        } catch (SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }
    public int getBalance(String username){
        try{
            String query = "SELECT * FROM Accounts WHERE Username = '" + username +"'";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getInt("Balance");
        } catch (SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
            return -1;
        }
    }
    
    public void changeEmail(String oldEmail, String newEmail){
        try{
            String query = "UPDATE Accounts SET Email='"+oldEmail+"' WHERE Email='"+newEmail+"'";
            stmt.executeUpdate(query);
        } catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void changePassword(String oldPassword, String newPassword){
        try{
            String query = "UPDATE Accounts SET Email='"+oldPassword+"' WHERE Email='"+newPassword+"'";
            stmt.executeUpdate(query);
        } catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void fillHistoryTable(String username){
        try{
            String query = "SELECT *  FROM Purchase_History WHERE Username = '" + username +"'";
            ResultSet rs = stmt.executeQuery(query);
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public ArrayList<String> getTakenSeats(int schedule_id) {
        try {
            ArrayList<String> seats = new ArrayList<String>();
            String query = "SELECT Seat_No FROM Purchase_History WHERE Schedule_ID = " + schedule_id;
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
               seats.add(rs.getString("Seat_No"));
            }
            return seats;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

}
 
