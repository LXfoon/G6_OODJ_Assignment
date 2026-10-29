/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author Foon
 */
public class Admin extends User{
    public Admin(){};
    public Admin(String id, String name, String email, String password, String phoneNum, String role) {
        super(id, name, email, password, phoneNum, role);
    }
    
    public static Admin fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 6) return null;
        
        String id = parts[0].trim();
        String name = parts[1].trim();
        String email = parts[2].trim();
        String password = parts[3].trim();
        String phoneNum = parts[4].trim();
        String role = parts[5].trim();
        
        return new Admin(id, name, email, password, phoneNum, role);    
    }
}
