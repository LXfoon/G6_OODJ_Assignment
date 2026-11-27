/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.gui;

/**
 *
 * @author Foon
 */
public class SelectedAdmin {
    private static String id;
    public static void setId(String id){
        SelectedAdmin.id = id;
    }
    
    public static String getId(){
        return id;
    }
}
