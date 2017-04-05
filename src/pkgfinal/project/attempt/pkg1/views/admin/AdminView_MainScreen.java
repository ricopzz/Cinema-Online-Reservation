/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.views.admin;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yosua
 */
public class AdminView_MainScreen extends javax.swing.JFrame {

    /**
     * Creates new form AdminMainScreen
     */
    public AdminView_MainScreen() {
        initComponents();
        poster1.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/pkgfinal/project/attempt/pkg1/resources/buffer.jpg")).getImage().getScaledInstance(270, 400, Image.SCALE_DEFAULT)));
    
    }
    
    // movies
    
    public void addAddMoviesActionListeners(ActionListener a){
        btnAddMovies.addActionListener(a);
    }
    
    public void addEditMovieActionListeners(ActionListener a){
        btnEditMovie.addActionListener(a);
    }
    
    public void addDeleteMovieActionListeners(ActionListener a){
        btnDeleteMovie.addActionListener(a);
    }
    
    // locations
    
    public void addAddLocationActionListeners(ActionListener a){
        btnAddLocation.addActionListener(a);
    }
    
  
    
    public void addDeleteLocationActionListeners(ActionListener a){
        btnDeleteLocation.addActionListener(a);
    }
    
    // schedule
    
    public void addAddScheduleActionListeners(ActionListener a){
        btnAddSchedule.addActionListener(a);
    }
    
    public void addDeleteScheduleActionListeners(ActionListener a){
        btnDeleteSchedule.addActionListener(a);
    }
    
    // account
    
    public void addAddAccountActionListeners(ActionListener a){
        btnAddAccount.addActionListener(a);
    }
    
    public void addDeleteAccountActionListeners(ActionListener a){
        btnDeleteAccount.addActionListener(a);
    }
    
    // vouchers
    
    public void addAddVouchersActionListeners(ActionListener a){
        btnAddVouchers.addActionListener(a);
    }
    
    public void addDeleteVouchersActionListeners(ActionListener a){
        btnDeleteVouchers.addActionListener(a);
    }
    
    
    
    
    
    public void setMovieTabelModel(DefaultTableModel dataModel){
        tblMovies.setModel(dataModel);
    }
    
    
    public void addMovieTableClickListener(MouseAdapter a){
        tblMovies.addMouseListener(a);
    }
    public void addLocationTableClickListener(MouseAdapter a){
        tblLocation.addMouseListener(a);
    }
    public void addAccountTableClickListener(MouseAdapter a){
        tblAccounts.addMouseListener(a);
    }
    public void addScheduleTableClickListener(MouseAdapter a){
        tblSchedule.addMouseListener(a);
    }
    public void addVoucherTableClickListener(MouseAdapter a){
        tblVoucher.addMouseListener(a);
    }
    
    public String getSelectedIDMovies(){
        int row =  tblMovies.getSelectedRow();
        if (row >= 0){
            return tblMovies.getValueAt(row, 0).toString();
        }else{
            return null;
        } 
    }
    
    public String getSelectedIDLocations(){
        int row = tblLocation.getSelectedRow();
        if (row >= 0){
            return tblLocation.getValueAt(row, 0).toString();
        }else{
            return null;
        }
    }
    
    public String getSelectedIDSchedules(){
        int row = tblSchedule.getSelectedRow();
        if (row >= 0){
            return tblSchedule.getValueAt(row, 0).toString();
        }else{
            return null;
        }
    }

    public String getSelectedIDAccounts(){
        int row = tblAccounts.getSelectedRow();
        if (row >= 0){
            return tblAccounts.getValueAt(row, 0).toString();
        }else{
            return null;
        }
    }
    
    public String getSelectedCodeVoucher(){
        int row = tblVoucher.getSelectedRow();
        if (row >= 0){
            return tblVoucher.getValueAt(row, 0).toString();
        }else{
            return null;
        }
    }
    
    public void addMovieSearchKeyListener(KeyAdapter a){
        txtMovieSearch.addKeyListener(a);
    }
    public void addLocationSearchKeyListener(KeyAdapter a){
        txtLocationSearch.addKeyListener(a);
    }
    public void addAccountSearchKeyListener(KeyAdapter a){
        txtAccountSearch.addKeyListener(a);
    }
    public void addVoucherSearchKeyListener(KeyAdapter a){
        txtVoucherSearch.addKeyListener(a);
    }
    public void addScheduleSearchKeyListener(KeyAdapter a){
        txtScheduleSearch.addKeyListener(a);
    }
    
    
    
    public String getMovieSearchKey(){
        return txtMovieSearch.getText();
    }
    public String getLocationSearchKey(){
        return txtLocationSearch.getText();
    }
    public String getAccountSearchKey(){
        return txtAccountSearch.getText();
    }
    public String getVoucherSearchKey(){
        return txtVoucherSearch.getText();
    }
    public String getScheduleSearchKey(){
        return txtScheduleSearch.getText();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovies = new javax.swing.JTable();
        btnAddMovies = new javax.swing.JButton();
        btnDeleteMovie = new javax.swing.JButton();
        btnEditMovie = new javax.swing.JButton();
        txtMovieSearch = new javax.swing.JTextField();
        poster1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSchedule = new javax.swing.JTable();
        btnAddSchedule = new javax.swing.JButton();
        txtScheduleSearch = new javax.swing.JTextField();
        btnDeleteSchedule = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAccounts = new javax.swing.JTable();
        btnAddAccount = new javax.swing.JButton();
        txtAccountSearch = new javax.swing.JTextField();
        btnDeleteAccount = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLocation = new javax.swing.JTable();
        btnAddLocation = new javax.swing.JButton();
        txtLocationSearch = new javax.swing.JTextField();
        btnDeleteLocation = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVoucher = new javax.swing.JTable();
        btnAddVouchers = new javax.swing.JButton();
        txtVoucherSearch = new javax.swing.JTextField();
        btnDeleteVouchers = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnSignOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setLocationByPlatform(true);

        tblMovies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMovies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMoviesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMovies);

        btnAddMovies.setText("Add Movies");

        btnDeleteMovie.setText("Delete Movie");

        btnEditMovie.setText("Edit Movie");

        txtMovieSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMovieSearchActionPerformed(evt);
            }
        });
        txtMovieSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMovieSearchKeyReleased(evt);
            }
        });

        poster1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddMovies)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditMovie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMovieSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteMovie))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(poster1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(poster1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteMovie)
                    .addComponent(txtMovieSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditMovie)
                    .addComponent(btnAddMovies)))
        );

        jTabbedPane1.addTab("Manage Movies", jPanel1);

        tblSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblSchedule);

        btnAddSchedule.setText("Add Schedule");

        btnDeleteSchedule.setText("Delete Schedule");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSchedule)
                .addGap(95, 95, 95)
                .addComponent(txtScheduleSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteSchedule))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddSchedule)
                    .addComponent(btnDeleteSchedule)
                    .addComponent(txtScheduleSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Manage Schedules", jPanel3);

        tblAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblAccounts);

        btnAddAccount.setText("Add Account");

        btnDeleteAccount.setText("Delete Account");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddAccount)
                .addGap(95, 95, 95)
                .addComponent(txtAccountSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteAccount)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAccount)
                    .addComponent(btnDeleteAccount)
                    .addComponent(txtAccountSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Manage Accounts", jPanel4);

        tblLocation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblLocation);

        btnAddLocation.setText("Add Locations");

        btnDeleteLocation.setText("Delete Location");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddLocation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocationSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteLocation)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddLocation)
                    .addComponent(btnDeleteLocation)
                    .addComponent(txtLocationSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Manage Locations", jPanel2);

        tblVoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblVoucher);

        btnAddVouchers.setText("Add Vouchers");

        btnDeleteVouchers.setText("Delete Vouchers");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnDeleteVouchers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVoucherSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddVouchers)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddVouchers)
                    .addComponent(txtVoucherSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteVouchers)))
        );

        jTabbedPane1.addTab("Manage Vouchers", jPanel5);

        jLabel2.setText("Welcome, Yosua");

        btnSignOut.setText("Sign out");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSignOut)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnSignOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Manage Movies");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblMoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMoviesMouseClicked
        
    }//GEN-LAST:event_tblMoviesMouseClicked

    private void txtMovieSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMovieSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovieSearchKeyReleased

    private void txtMovieSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovieSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovieSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminView_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminView_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminView_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminView_MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminView_MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAccount;
    private javax.swing.JButton btnAddLocation;
    private javax.swing.JButton btnAddMovies;
    private javax.swing.JButton btnAddSchedule;
    private javax.swing.JButton btnAddVouchers;
    private javax.swing.JButton btnDeleteAccount;
    private javax.swing.JButton btnDeleteLocation;
    private javax.swing.JButton btnDeleteMovie;
    private javax.swing.JButton btnDeleteSchedule;
    private javax.swing.JButton btnDeleteVouchers;
    private javax.swing.JButton btnEditMovie;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel poster1;
    public javax.swing.JTable tblAccounts;
    public javax.swing.JTable tblLocation;
    public javax.swing.JTable tblMovies;
    public javax.swing.JTable tblSchedule;
    public javax.swing.JTable tblVoucher;
    private javax.swing.JTextField txtAccountSearch;
    private javax.swing.JTextField txtLocationSearch;
    private javax.swing.JTextField txtMovieSearch;
    private javax.swing.JTextField txtScheduleSearch;
    private javax.swing.JTextField txtVoucherSearch;
    // End of variables declaration//GEN-END:variables
    
    public void setPoster(int index) {
        String path = "/pkgfinal/project/attempt/pkg1/resources/buffer" + index+".jpg";
        File someFile = new File("/Users/User/NetBeansProjects/Final Project - Attempt 1/src/pkgfinal/project/attempt/pkg1/resources/buffer"+index+ ".jpg");
        if ((someFile.exists() && !someFile.isDirectory())){
            System.out.println("file found");
            poster1.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(270, 400, Image.SCALE_DEFAULT)));
        }else{
            System.out.println("File not found");
        }
    }

    public void addSignOutListener(ActionListener actionListener) {
        btnSignOut.addActionListener(actionListener);
    }
    
    public void setLabelName(String text){
        jLabel2.setText(text);
    }

}
