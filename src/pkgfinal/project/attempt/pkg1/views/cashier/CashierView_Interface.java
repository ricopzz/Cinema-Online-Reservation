/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.views.cashier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author enrico
 */
public class CashierView_Interface extends javax.swing.JFrame {

    /**
     * Creates new form CashierView_Interface
     */
    ActionListener updateClockAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            lblClock.setText(new Date().toLocaleString()); 
        }
    };
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
            java.util.logging.Logger.getLogger(CashierView_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CashierView_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CashierView_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CashierView_Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashierView_Interface().setVisible(true);
            }
        });
    }
   
    public CashierView_Interface() {
        
        initComponents();
        Timer t = new Timer(1000, updateClockAction);
        t.start();
    }
    
    public void addPrintListener(ActionListener a){
        btnPrint.addActionListener(a);
    }
    public void addPrintQueueListener(ActionListener a){
        btnAddToPrintQueue.addActionListener(a);
    }
    public void addSignOutListener(ActionListener a){
        btnSignOut.addActionListener(a);
    }
    public void addKeyReleasedListener(KeyAdapter a){
        txtUsername.addKeyListener(a);
    }
    public void addChangeUserListener(ActionListener a){
        btnChangeUser.addActionListener(a);
    }
    
    public void setPurchaseTabelModel(DefaultTableModel dataModel){
        tblPurchase.setModel(dataModel);
    }
    public void addPrintQueue() {
        System.out.println("hahaha");
        DefaultTableModel model = (DefaultTableModel) tblPrintQueue.getModel();
        Object []columns = new Object[7];
        // checks if already in print queue
        boolean exist = false;
        for (int x=0;x<model.getRowCount();x++){
            if (model.getValueAt(x, 0) == tblPurchase.getValueAt(tblPurchase.getSelectedRow(),0 )){
                exist = true;
            };
        }
        if (exist){
            JOptionPane.showMessageDialog(null, "Entry already in print queue");
        }else{
            for (int x=0;x<7;x++){
                columns[x] = tblPurchase.getValueAt(tblPurchase.getSelectedRow(), x);

            }
            model.addRow(columns);
            tblPrintQueue.setModel(model);
        }
    }
    public void resetPrintQueue() {
        tblPrintQueue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Schedule_ID", "Username", "Date_Of_Purchase", "Time_Of_Purchase", "Seat_No", "Claimed"
            }
        ));
    }
    
    public ArrayList<String> getSeatNumbers(){
        ArrayList<String> alist = new ArrayList<String>();
        for (int x=0;x<tblPrintQueue.getRowCount();x++){
            alist.add((String)tblPrintQueue.getValueAt(x, 5));
        }
        return alist;
    }
    public String getSelectedUsername(){
        return txtUsername.getText();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txtAdminUsername = new javax.swing.JTextField();
        lblClock = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnSignOut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPurchase = new javax.swing.JTable();
        btnAddToPrintQueue = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPrintQueue = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        btnChangeUser = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtAdminUsername.setText("Admin username");
        txtAdminUsername.setEnabled(false);

        btnSignOut.setText("Sign Out");

        jLabel1.setText("Username : ");

        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsernameKeyReleased(evt);
            }
        });

        tblPurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Schedule_ID", "Username", "Date_Of_Purchase", "Time_Of_Purchase", "Seat_No", "Claimed"
            }
        ));
        jScrollPane1.setViewportView(tblPurchase);

        btnAddToPrintQueue.setText("Add to print queue");
        btnAddToPrintQueue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToPrintQueueActionPerformed(evt);
            }
        });

        tblPrintQueue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Schedule_ID", "Username", "Date_Of_Purchase", "Time_Of_Purchase", "Seat_No", "Claimed"
            }
        ));
        jScrollPane3.setViewportView(tblPrintQueue);

        btnPrint.setText("Print ");

        btnChangeUser.setText("Change User");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtAdminUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(lblClock, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSignOut))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChangeUser))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddToPrintQueue, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPrint, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClock, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSignOut))
                    .addComponent(txtAdminUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddToPrintQueue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrint))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddToPrintQueueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToPrintQueueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddToPrintQueueActionPerformed

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameKeyReleased

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToPrintQueue;
    private javax.swing.JButton btnChangeUser;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSignOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblClock;
    private javax.swing.JTable tblPrintQueue;
    private javax.swing.JTable tblPurchase;
    private javax.swing.JTextField txtAdminUsername;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    public boolean isPrintQueueEmpty() {
        if (tblPrintQueue.getValueAt(0, 0).equals(null)){
            return true;
        }else{
            return false;
        }
    }

   
}
