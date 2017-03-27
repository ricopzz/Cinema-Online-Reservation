/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pkgfinal.project.attempt.pkg1.controller.AccountController;

/**
 *
 * @author Yosua
 */
public class AdminModel {
    
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USER = "root";
    private final String PASS = "";
    
    private Location myLocation = new Location();
    private AccountModel myAccount = new AccountModel();

    private Connection conn = null;
    private Statement stmt = null;
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
    
    public DefaultTableModel buildTableModel(ResultSet rs) {
        try {
            DefaultTableModel dtm = new DefaultTableModel();
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
            return new DefaultTableModel(data, columnNames);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    //Movies
    
    public ResultSet getMoviesResultSet(){
        try{
            establishConnection();
            String query = "SELECT ID, Name, G1, G2, G3, Duration, Director, Trailer_URL, Rating  FROM Movies";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
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
   
    public ResultSet getMoviesSearchResultSet(String searchKey){
        try {
            ResultSet rs = null;
            rs =  stmt.executeQuery("SELECT ID, Name, G1, G2, G3, Duration, Director, Trailer_URL, Rating FROM Movies WHERE ID LIKE '%" + searchKey + "%' OR Name LIKE '" + searchKey +"%'");
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void addMovie(Movie m){
        
        try{
    
            establishConnection();
            String sql = "INSERT INTO Movies (Name, G1, G2, G3, Duration, Director, Trailer_URL, Poster, Rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getGenre1());
            stmt.setString(3, m.getGenre2());
            stmt.setString(4, m.getGenre3());
            stmt.setObject(5, m.getDuration());
            stmt.setString(6, m.getDirector());
            stmt.setString(7, m.getTrailerurl());
            

            File image = m.getPoster();
            FileInputStream   fis = new FileInputStream(image);
            stmt.setBinaryStream(8, fis, (int) image.length());
            
            
            stmt.setDouble(9, m.getRating());
            stmt.execute();
    
        }catch(SQLException ex){
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteMovies(String ID){
        try{
            establishConnection();
            String query = "DELETE FROM Movies where ID = "+ ID;
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    public void editMovies(int ID, Movie m){
        try {
           
            establishConnection();
            String sql = "UPDATE Movies SET Name = ?, G1 = ?, G2 = ?, G3 = ? , Duration = ?, Director = ?, Trailer_URL = ?, Poster = ?, Rating = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getGenre1());
            stmt.setString(3, m.getGenre2());
            stmt.setString(4, m.getGenre3());
            stmt.setObject(5, m.getDuration());
            stmt.setString(6, m.getDirector());
            stmt.setString(7, m.getTrailerurl());
            File image = m.getPoster();
            FileInputStream   fis = new FileInputStream(image);
            stmt.setBinaryStream(8, fis, (int) image.length());
            
            
            stmt.setDouble(9, m.getRating());
            stmt.setInt(10, ID);
            stmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Locations
    
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
    
    public void addLocation(String name, String address, int theater_no){
        try{
            establishConnection();
            String query = "INSERT INTO Location (Name, Address, theater_amount) VALUES ('"+name+"','"+address+"',"+theater_no+")";
            System.out.println(query);
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void deleteLocations(String ID){
        try{
            establishConnection();
            String query = "DELETE FROM Location where ID = "+ ID;
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    // Schedules
    
    public ResultSet getSchedulesResultSet(){
        try{
            establishConnection();
            String query = "SELECT * FROM Schedule";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public void deleteSchedules(String ID){
        try{
            establishConnection();
            String query = "DELETE FROM Schedule where ID = "+ ID;
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    // Accounts
    
    public ResultSet getAccountsResultSet(){
        try{
            establishConnection();
            String query = "SELECT * FROM Accounts";
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.out.println(ex);
            return null;
        }
    }
    
    public void deleteAccount(String ID){
        try{
            establishConnection();
            String query = "DELETE FROM Accounts where ID = "+ ID;
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
   
    public boolean addAccount(String name,String email,String dateOfBirth,String username,String password,int balance,String type){
        try {
            String query = "INSERT INTO Accounts (`Username`,`Password`,`Name`,`Email`,`DOB`,`Balance`,Type) VALUES (" +
                "'"+ username +"', "+"'"+ password +"', "+"'"+ name+"', "+"'"+email +"', "+"'"+ dateOfBirth +"', "+
                balance+",'"+type+"')";
            System.out.println(query);
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occurred (Please check that all fields have been filled)");
            return false;
        }
    }
    
    
    
    

  
}