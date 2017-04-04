/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
    
    // random code generation
    public String getRandomCode() {
        return randomCode;
    }
    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
    // for purchasing
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
    // gets the content for comboboxes
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
    public ResultSet getCurrentShowingMovieIDResultSet(){
        try{
            String query = "SELECT Movie_ID FROM Schedule";
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
    // data fetching from database
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
    public String getMovieDuration(Integer index) {
        try {
            String query = "SELECT * FROM Movies WHERE ID = " + index;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getString("Duration");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet getMoviesData(int ID){
        try{
            establishConnection();
            String query = "SELECT  Name, G1, G2, G3, Duration, Director, Trailer_URL, Rating  FROM Movies WHERE ID = " + ID;
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
    public String getLocationNames(int id) {
        try {
            String query = "SELECT * FROM Location WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return rs.getString("Name");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // user data
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
    // change email and password
    public boolean changeEmail(String oldEmail, String newEmail){
        try{
            String query = "UPDATE Accounts SET Email='"+oldEmail+"' WHERE Email='"+newEmail+"'";
            stmt.executeUpdate(query);
            return true;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean changePassword(String oldPassword,String newPassword, String username){
        try{
            String query = "UPDATE Accounts SET Password='"+newPassword+"' WHERE Username ='"+ username +"' AND Password = '" + oldPassword+ "'";
            if (!(stmt.executeUpdate(query) > 0)) return false;
            return true;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    // etc
    public void fillHistoryTable(String username){
        try{
            String query = "SELECT * FROM Purchase_History WHERE Username = '" + username +"'";
            ResultSet rs = stmt.executeQuery(query);
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void makeMoviePoster(int index){
        //  ClassLoader classLoader = getClass().getClassLoader();
        File someFile = new File("/Users/User/NetBeansProjects/Final Project - Attempt 1/src/pkgfinal/project/attempt/pkg1/resources/buffer"+index+ ".jpg");
    
        if(!(someFile.exists() && !someFile.isDirectory())) { 
            try {
                String query =  "SELECT Poster FROM Movies WHERE ID =" +index;
                ResultSet rs = stmt.executeQuery(query);
                Blob blob = null;
                if (rs.next()){
                    blob = rs.getBlob("Poster");
                }

                InputStream in = blob.getBinaryStream();

                OutputStream out = new FileOutputStream(someFile);
                byte[] buff = new byte[100000];  // how much of the blob to read/write at a time
                int len = 0;

                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }       
            } catch (SQLException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("file exist");
        }
        
    }
    public DefaultTableModel buildTableModel(ResultSet rs) {
        try {
            
            DefaultTableModel dtm = new DefaultTableModel();
            if (rs == null) return dtm;
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // get column names
            Vector<String> columnNames = new Vector<String>();
            for (int x = 1; x <= columnCount; x++) {
                columnNames.add(metaData.getColumnName(x));
            }
            // get column data
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
               for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
            return new DefaultTableModel(data,columnNames);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    // new implementations
    public ResultSet getLocationsResultSet(){
        try{
            establishConnection();
            String query = "SELECT * FROM Location";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }

    public ResultSet getLocationsResultSet(Integer ID) {
       try{
            establishConnection();
            String query = "SELECT * FROM Location WHERE ID = " + ID;
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        } 
    }
    
    public ResultSet getHistoryOfPurchase(String username){
        try{
            establishConnection();
            String query = "SELECT * FROM Purchase_History WHERE Username = '" + username+"'";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        } 
    }
   

}
 
