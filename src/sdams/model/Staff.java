/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;

/**
 *
 * @author xiaow
 */
public abstract class Staff extends User {
    private double salary;
    private static final DecimalFormat MONEY_FMT = new DecimalFormat("0.00");

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
        return getId() + "|" + MONEY_FMT.format(getSalary());
    }
    
    public static Staff fromUserLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) return null;

        String id = parts[0].trim();
        String name = parts[1];
        String email = parts[2];
        String pw = parts[3];
        String phone = parts[4];
        String role = parts[5].trim().toLowerCase();
        
        switch (role) {
            case "instructor": 
                return new Instructor(id, name, email, pw, phone, 0.0);
            case "facilities": 
                return new FacOfficer(id, name, email, pw, phone, 0.0);
            case "attendance": 
                return new AttOfficer(id, name, email, pw, phone, 0.0);
            // placeholder for salary, set in fromLines
                
            default: return null; // not staff
        }
    }
    
    // map id(key) and salary(value)
    public static Map.Entry<String, Double> fromSalaryLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 2) return null;
        try {
            return Map.entry(parts[0].trim(), Double.valueOf(parts[1].trim()));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    // find and set salary
    public static Staff fromLines(String userLine, ArrayList<String> salaryLines) {
        Staff s = fromUserLine(userLine);
        if (s == null) return null;

        for (String salaryLine : salaryLines) {
            Map.Entry<String, Double> entry = fromSalaryLine(salaryLine);
            if (entry != null && entry.getKey().equals(s.getId())) {
                s.setSalary(entry.getValue());
                break;
            }
        }
        return s;
    }
}
