/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public class Instructor extends Staff {
    
    public Instructor() { 
        super(); 
    }
    
    public Instructor(String id, String name, String email, String password,String phoneNum, double salary) {
        super(id, name, email, password, phoneNum, "Instructor", salary);
    }

    public static Instructor fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) {
            return null;
        }
        
        if (!"Instructor".equalsIgnoreCase(parts[5].trim())) {
            return null;
        }
        
        return new Instructor(parts[0], parts[1], parts[2], parts[3], parts[4], 0.00);
    }
}