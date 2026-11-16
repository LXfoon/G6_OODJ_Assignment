/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;
import java.util.Map;
import sdams.util.FileUtil;

/**
 *
 * @author xiaow
 */

// can be deleted if not used
public class StaffRepo {

    private static final String USER_FILE  = "user.txt";
    private static final String STAFF_FILE = "staff.txt";

    // all staff
    public ArrayList<Staff> loadAllStaff() {
        ArrayList<String> salaryLines = new ArrayList<>();
        FileUtil.read(STAFF_FILE, salaryLines, s -> s);

        ArrayList<String> userLines = new ArrayList<>();
        FileUtil.read(USER_FILE, userLines, s -> s);

        ArrayList<Staff> staff = new ArrayList<>();
        for (String line : userLines) {
            Staff s = Staff.fromLines(line, salaryLines);
            if (s != null) staff.add(s);
        }
        return staff;
    }

    public Staff findStaffById(String id) {
        if (id == null || id.isBlank()) return null;
        id = id.trim();

        ArrayList<String> userLines = new ArrayList<>();
        FileUtil.read(USER_FILE, userLines, s -> s);

        String userLine = null;
        for (String line : userLines) {
            if (line.split("\\|", -1)[0].trim().equals(id)) { 
                userLine = line; 
                break; 
            }
        }
        if (userLine == null) return null;

        ArrayList<String> salaryLines = new ArrayList<>();
        FileUtil.read(STAFF_FILE, salaryLines, s -> s);

        return Staff.fromLines(userLine, salaryLines);
    }
    
    public double getSalaryById(String id) {
        if (id == null || id.isBlank()) return 0.0;
        id = id.trim();

        ArrayList<String> salaryLines = new ArrayList<>();
        FileUtil.read(STAFF_FILE, salaryLines, s -> s);

        for (String salaryLine : salaryLines) {
            Map.Entry<String, Double> e = Staff.fromSalaryLine(salaryLine);
            if (e != null && e.getKey().equals(id)) return e.getValue();
        }
        return 0.0;
    }
}