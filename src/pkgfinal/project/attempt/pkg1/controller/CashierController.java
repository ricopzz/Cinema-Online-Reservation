/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import pkgfinal.project.attempt.pkg1.SendEmail;
import pkgfinal.project.attempt.pkg1.model.CashierModel;
import pkgfinal.project.attempt.pkg1.views.cashier.CashierView_Interface;
import pkgfinal.project.attempt.pkg1.views.cashier.CashierView_VerifyPrint;


/**
 *
 * @author enrico
 */
public class CashierController {
    
    private CashierView_Interface theInterface;
    private CashierView_VerifyPrint theVerify;
    
    private CashierModel theModel;
    private SendEmail emailSender = new SendEmail();
   
    public static void main(String[] args){
        try{
         UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        CashierController myCashier = new CashierController(new CashierView_Interface(),new CashierView_VerifyPrint(), new CashierModel());                                
     
    }
    
    public CashierController(CashierView_Interface theInterface,CashierView_VerifyPrint theVerify,CashierModel theModel ){
        this.theInterface = theInterface;
        this.theModel = theModel;
        this.theVerify = theVerify;
        start();
    }
    
    public void start(){
        buildCashierInterfaceListener();
        buildVerifyPrintListener();
        theModel.establishConnection();
        theInterface.setVisible(true);
    }
    
    public void buildCashierInterfaceListener(){
        theInterface.addPrintListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                String username = theInterface.getSelectedUsername();
                if (!theInterface.isPrintQueueEmpty()){
                    JOptionPane.showMessageDialog(theInterface, "Sending Email....");
                    String code = emailSender.sendEmailBooking(theModel.getEmailFromUsername(username), theModel.getFullnameFromUsername(username), theInterface.getSeatNumbers());
                    if (!code.equals(null)){ 
                        System.out.println(code);
                        theVerify = new CashierView_VerifyPrint();
                        buildVerifyPrintListener();
                        theVerify.setVerificationCode(code);
                        theVerify.setUsername(username);
                        theVerify.setVisible(true);
                    }else{
                        JOptionPane.showMessageDialog(null, "Failed to send email");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Print queue empty");
                }              
           }
        });
        theInterface.addPrintQueueListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               theInterface.addPrintQueue();
            }
        });
        theInterface.addChangeUserListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theInterface.resetPrintQueue();
                theInterface.setPurchaseTabelModel(theModel.getTableModelPurchase(theInterface.getSelectedUsername()));
                theInterface.resetPrintQueue();
            }
            
        });
        theInterface.addSignOutListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        });
        theInterface.addKeyReleasedListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                
            }
        });
    }
    public void buildVerifyPrintListener(){
        theVerify.addContinueListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (theVerify.getVerificationCode().equals(theVerify.getText())){
                    String username = theVerify.getUsername();
                    JOptionPane.showMessageDialog(theInterface, "Verification succesful");
                    ArrayList<String> seats = theInterface.getSeatNumbers();
                    for (int x=0;x<seats.size();x++){
                        theModel.setClaimedFromUsername(username,seats.get(x));
                    }
                    
                    theVerify.setVisible(false);
                    theVerify.dispose();
                }else{
                    JOptionPane.showMessageDialog(theInterface, "Invalid verification code");
                    theVerify.setText("");
                }
            }
        });
        theVerify.addResendEmailListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = theVerify.getUsername();
                //JOptionPane.showMessageDialog(theInterface, "Sending Email....");
                String code = emailSender.sendEmailBooking(theModel.getEmailFromUsername(username), theModel.getFullnameFromUsername(username), theInterface.getSeatNumbers());
                theVerify.setVerificationCode(code);
                theVerify.setText("");
            }
        });
    }
}
