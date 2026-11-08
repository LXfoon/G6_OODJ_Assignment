/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public abstract class Staff extends User {
    private double salary;

    public Staff() { 
        super(); 
    }

    public Staff(String id, String name, String email, String password,
                 String phoneNum, String role, double salary) {
        super(id, name, email, password, phoneNum, role);
        this.salary = salary;
    }

    public double getSalary() { 
        return salary; 
    }
    
    public void setSalary(double salary) {
        this.salary = salary; 
    }

    public String toSalaryLine() { 
        // add in staff.txt (format: userID|salary)
        return getId() + "|" + salary;
    }
    
    public static Staff fromLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) return null;

        String id = parts[0].trim();
        String name = parts[1];
        String email = parts[2];
        String pass = parts[3];
        String phone = parts[4];
        String role = parts[5].trim().toLowerCase();
        
        switch (role) {
            case "instructor": 
                return new Instructor(id, name, email, pass, phone, 0.0);
            case "facilities": 
                return new FacOfficer(id, name, email, pass, phone, 0.0);
            case "attendance": 
                return new AttOfficer(id, name, email, pass, phone, 0.0);
            
            default: return null; // not staff
        }
    }
}
