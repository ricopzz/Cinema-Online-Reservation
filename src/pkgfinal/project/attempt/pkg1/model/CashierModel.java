/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.model;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class CashierModel {
    private final String DB_URL = "jdbc:mysql://localhost/";
    private final String USER = "root";
    private final String PASS = "";

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
    // gets data from database
    public String getEmailFromUsername(String username){
        ResultSet rs = null;
        
        try {
            rs = stmt.executeQuery("SELECT Email FROM Accounts WHERE Username = '" + username +"'");
           
            rs.next();
            return rs.getString("Email");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot access database for email");
            return null;
        }
        
    }
    public String getFullnameFromUsername(String username) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT Name FROM Accounts WHERE Username = '" + username +"'");
            rs.next();
            System.out.println("NAMEEE " + rs.getString("Name"));
            return rs.getString("Name");
        } catch (SQLException ex) {
            //Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot access database for fullname");
            return null;
        }
    }
    public void setClaimedFromUsername(String username){
        try {
            stmt.executeUpdate("UPDATE `Purchase_History` SET `Claimed` = '1' WHERE  Username ='" + username +"'");
        } catch (SQLException ex) {
            Logger.getLogger(CashierModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Gets the table model for the purchase table
    public DefaultTableModel getTableModelPurchase(String Username){
        ResultSet rs = null;
        DefaultTableModel d = null;
        try {
           
            rs = stmt.executeQuery("SELECT * FROM Purchase_History WHERE Username = '" + Username +"' AND Claimed = false");
            d = buildTableModel(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot access database ");
            
        }
        return d;
    }
    // An all purpose function to build table models based on a resultset
    public DefaultTableModel buildTableModel(ResultSet rs) {
        try {
            DefaultTableModel dtm = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // get column names
            Vector<String> columnNames = new Vector<String>();
            for (int x = 1; x <= columnCount; x++) {
                System.out.println(metaData.getColumnName(x));
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
            //Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
