/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1;

import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.controller.Customer.CustomerController;
import pkgfinal.project.attempt.pkg1.controller.CashierController;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_ForgetPassword;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.AccountView_SignUp;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_HistoryOfPurchase;
import pkgfinal.project.attempt.pkg1.views.cashier.CashierView_Interface;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_AddBalance;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_ChangeInformation;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_ChangePassword;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_ChooseSeat;
import pkgfinal.project.attempt.pkg1.views.m.CustomerView_Interface;

/**
 *
 * @author Yosua
 */
public class FinalProjectAttempt1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //AccountController myAccountController = new AccountController(new AccountModel(),
       //         new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
        //CashierController myCashier = new CashierController(new CashierView_Interface());                                
      CustomerController m = new CustomerController(new CustomerView_Interface() ,new CustomerView_ChangeInformation() , new CustomerView_ChangePassword() , new CustomerView_AddBalance() ,new CustomerView_ChooseSeat() ,new CustomerView_HistoryOfPurchase() );
    }
    
}
