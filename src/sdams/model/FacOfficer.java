/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public class FacOfficer extends Staff {
    
    public FacOfficer(String id, String name, String email, String password,String phoneNum, double salary) {
        super(id, name, email, password, phoneNum, "Facilities", salary);
    }
    
    public static FacOfficer fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 7) return null;
        
    try{    
        String id = parts[0].trim();
        String name = parts[1].trim();
        String email = parts[2].trim();
        String password = parts[3].trim();
        String phoneNum = parts[4].trim();
        double salary = Double.parseDouble(parts[6].trim());
        
        return new FacOfficer(id, name, email, password, phoneNum, salary); 
    } catch (NumberFormatException e) {
        return null;
    }
    }
}
    