/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import javax.swing.JOptionPane;
import sdams.gui.LoggedIn;

/**
 *
 * @author Foon
 */
public class LoggedInChecker {
    public static void LoginChecker(){
        if(LoggedIn.getId() == null){
            JOptionPane.showMessageDialog(null, "Please log in!");
            System.exit(0);
        }
    }

}
