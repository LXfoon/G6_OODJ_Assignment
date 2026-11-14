/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public class AttOfficer extends Staff {
    
    public AttOfficer() { 
        super(); 
    }
    
    public AttOfficer(String id, String name, String email, String password,String phoneNum, double salary) {
        super(id, name, email, password, phoneNum, "Attendance", salary);
    }
}