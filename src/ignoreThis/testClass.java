/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ignoreThis;

import javax.swing.UIManager;
import pkgfinal.project.attempt.pkg1.controller.AccountController;
import pkgfinal.project.attempt.pkg1.model.AccountModel;
import pkgfinal.project.attempt.pkg1.views.AccountView_ForgetPassword;
import pkgfinal.project.attempt.pkg1.views.AccountView_Login;
import pkgfinal.project.attempt.pkg1.views.AccountView_SignUp;

/**
 *
 * @author Yosua
 */
public class testClass {
    public static void main(String[] args){
        try{
         UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        AccountController myAccountController = new AccountController(new AccountModel(),
               new AccountView_Login(), new AccountView_SignUp(), new AccountView_ForgetPassword());
    }
}
