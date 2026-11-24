/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import sdams.gui.LoggedIn;
import sdams.gui.Login;

/**
 *
 * @author Foon
 */
public class LoggedInChecker {
    public static boolean loginChecker(java.awt.Window caller){
        if(LoggedIn.getId() == null){
            JOptionPane.showMessageDialog(null, "Please log in!");
            Login login = new Login();
            login.setVisible(true);
            if(caller != null){
                caller.dispose();
            }
            return false;
        }
        return true;
    }

}
