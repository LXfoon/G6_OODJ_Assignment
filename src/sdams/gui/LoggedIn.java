/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.gui;

/**
 *
 * @author Foon
 */
public class LoggedIn {
    private static String username;
    public static void setUsername(String username){
        LoggedIn.username = username;
    }
    
    public static String getUsername(){
        return username;
    }
}
