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
    public String[] getCurrentShowingTime(String movie, String location){
        ArrayList<String> time = new ArrayList<String>();
        try{
            
            String query = "SELECT ID FROM Movies WHERE Name = '" + movie  + "'";
            ResultSet rs = stmt.executeQuery(query);
            int movie_id = -1;
            if(rs.next())  movie_id = rs.getInt("ID");
            
             query = "SELECT ID FROM Location WHERE Name = '" + location  + "'";
             rs = stmt.executeQuery(query);
            int location_id = -1;
            if(rs.next())  location_id = rs.getInt("ID");
            
            query = "SELECT Time FROM Schedule WHERE Movie_ID = '" + movie_id + "' AND Location_ID ='" + location_id+"'";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                Time t = rs.getTime("Time");
                time.add(t.toString());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return time.toArray(new String[0]);
    }
    public String[] getCurrentShowingMovies(String location){
        ArrayList<String> movies = new ArrayList<String>();
        try{
            
            String query = "SELECT ID FROM Location WHERE Name = '" + location + "'";
            ResultSet rs = stmt.executeQuery(query);
            int location_id = -1;
            if(rs.next())  location_id = rs.getInt("ID");
            
            ArrayList<Integer> movie_id = new ArrayList<Integer>();
            query = "SELECT Movie_ID FROM Schedule WHERE Location_ID = '" + location_id + "'";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("Movie_ID");
                if (!movie_id.contains(id)) movie_id.add(id);
            }
            
            for (int x=0;x<movie_id.size();x++){
                query = "SELECT * FROM Movies WHERE ID = " + movie_id.get(x);
                rs = stmt.executeQuery(query);
                if (rs.next())
                    movies.add(rs.getString("Name"));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return movies.toArray(new String[0]);
    }
    public String[] getCurrentShowingLocation(){
        ArrayList<String> movies = new ArrayList<String>();
        try{
            ArrayList<Integer> movie_id = new ArrayList<Integer>();
            String query = "SELECT Location_ID FROM Schedule";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("Location_ID");
                if (!movie_id.contains(id)) movie_id.add(id);
            }
            
            for (int x=0;x<movie_id.size();x++){
                query = "SELECT * FROM Location WHERE ID = " + movie_id.get(x);
                rs = stmt.executeQuery(query);
                if (rs.next())
                    movies.add(rs.getString("Name"));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return movies.toArray(new String[0]);
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
    
}
