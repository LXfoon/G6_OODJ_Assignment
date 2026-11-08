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

    public static AttOfficer fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) {
            return null;
        }
        
        if (!"Attendance".equalsIgnoreCase(parts[5].trim())) {
            return null;
        }
        
        return new AttOfficer(parts[0], parts[1], parts[2], parts[3], parts[4], 0.00);
    }
}