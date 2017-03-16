/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1;

import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_ForgetPassword;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.AccountView_SignUp;

/**
 *
 * @author Yosua
 */
public class FinalProjectAttempt1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AccountController myAccountController = new AccountController(new AccountModel(),
                new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
                                        
    }
    
}
