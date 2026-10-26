/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author Foon
 */
public class Manager extends User{
    public Manager(String id, String name, String email, String password, long phoneNum) {
        super(id, name, email, password, phoneNum);
    }

    public static Manager fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 5) return null;
        
        String id = parts[0].trim();
        String name = parts[1].trim();
        String email = parts[2].trim();
        String password = parts[3].trim();
        long phoneNum = Long.parseLong(parts[4].trim());
        
        return new Manager(id, name, email, password, phoneNum);    
    }
}
