/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;

import sdams.util.Generator;
import sdams.util.FileUtil;
import sdams.util.UserManager;
import sdams.util.Validator;

public class ManageStaffServices implements UserManager {

    private static final String USER_FILE  = "user.txt";
    private static final String STAFF_FILE = "staff.txt";

    private final ArrayList<Staff> allStaff = new ArrayList<>();
    private final ArrayList<String> allUserLines = new ArrayList<>();
    private final LinkedHashMap<String, Double> salaryMap = new LinkedHashMap<>();
    private final DecimalFormat moneyFmt = new DecimalFormat("0.00");
    
    private String pendingId;
    private String pendingPassword;

    @Override
    public void loadUser() {
        allStaff.clear();
        allUserLines.clear();
        salaryMap.clear();
        ArrayList<String> salaryLines = new ArrayList<>();
        FileUtil.read(STAFF_FILE, salaryLines, s -> s);
        for (String line : salaryLines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 2) continue;
            String id = parts[0].trim();
            String salaryStr = parts[1].trim();
            if (id.isEmpty()) continue;
            try {
                double salary = Double.parseDouble(salaryStr);
                salaryMap.put(id, salary);
            } catch (NumberFormatException e) {
                salaryMap.putIfAbsent(id, 0.00);
            }
        }

        FileUtil.read(USER_FILE, allUserLines, s -> s);
        for (String line : allUserLines) {
            Staff s = Staff.fromLine(line);
            if (s == null) continue; 
            s.setSalary(salaryMap.getOrDefault(s.getId(), 0.0));
            allStaff.add(s);
        }
    }

    @Override
    public void addUser(String name, String email, String phoneNum) {
        String password = Generator.passwordGenerator();
        Validator.userValidator(name, email, password, phoneNum);
        String id = Generator.idGenerator("");

        this.pendingId = id;
        this.pendingPassword = password;
    }
    
    public String addStaff(String name, String email, String phone, String role, double salary) {
        try {
            addUser(name, email, phone);
        } catch (RuntimeException ex) {
            clearPending();
            JOptionPane.showMessageDialog(null,
                    ex.getMessage() == null ? "Validation failed." : ex.getMessage(),
                    "Validation", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (salary < 0) {
            clearPending();
            JOptionPane.showMessageDialog(null,
                    "Salary must be 0 or greater.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String id = pendingId;
        String password = pendingPassword;
        clearPending();

        loadUser();

        String shortRole = normalizeRole(role);
        Staff staff = switch (shortRole) {
            case "Facilities" -> new FacOfficer(id, name, email, password, phone, salary);
            case "Attendance" -> new AttOfficer(id, name, email, password, phone, salary);
            default -> new Instructor(id, name, email, password, phone, salary);
        };

        allStaff.add(staff);
        allUserLines.add(staff.toLine());
        salaryMap.put(id, salary);

        FileUtil.write(USER_FILE, allUserLines, s -> s);
        writeSalaryFile();

        JOptionPane.showMessageDialog(null, "Staff created successfully!\n\nID: " + id + "\nPassword: " + password);
        return id;
    }
    
    @Override
    public void updateUser(String id, String name, String email, String password, String phoneNum) {
        // role cannot be edited
        loadUser();
        Validator.userValidator(name, email, password, phoneNum);

        Staff staff = findById(id);
        if (staff == null) {
            JOptionPane.showMessageDialog(null, "Staff not found: " + id);
            return;
        }
        staff.setName(name);
        staff.setEmail(email);
        staff.setPassword(password);
        staff.setPhoneNum(phoneNum);

        replaceUserLine(id, staff.toLine());
        FileUtil.write(USER_FILE, allUserLines, s -> s);
    }
    
    public void updateStaff(String id, String name, String email, String phoneNum, double salary) {
        loadUser();
        Staff staff = findById(id);
        if (staff == null) {
            JOptionPane.showMessageDialog(null, "Staff not found: " + id);
            return;
        }
        String existingPassword = staff.getPassword();
        
        updateUser(id, name, email, existingPassword, phoneNum);
        
        salaryMap.put(id, salary);
        writeSalaryFile();
        
        JOptionPane.showMessageDialog(null, "Staff edited successfully!");
    }

    @Override
    public void deleteUser(String id) {
        loadUser();

        allUserLines.removeIf(line -> {
            String[] parts = line.split("\\|", -1);
            return parts.length >= 1 && parts[0].trim().equals(id);
        });
        allStaff.removeIf(s -> s.getId().equals(id));
        salaryMap.remove(id);

        FileUtil.write(USER_FILE, allUserLines, s -> s);
        writeSalaryFile();
        JOptionPane.showMessageDialog(null, "Staff deleted successfully!");
    }

    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(allStaff);
    }

    public Staff findById(String id) {
        for (Staff s : allStaff) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public int countByType(Class<? extends Staff> type) {
        int n = 0;
        for (Staff s : allStaff) {
            if (type.isInstance(s)) {
                n++;
            }
        }
        return n;
    }

    private String normalizeRole(String role) {
        String r = role.trim().toLowerCase();
        
        return switch (r) {
            case "instructor" -> "Instructor";
            case "facilities" -> "Facilities";
            case "attendance" -> "Attendance";
            default -> "Instructor";
        };
        
    }
    
    
    private void clearPending() {
        pendingId = null;
        pendingPassword = null;
    }

    private void replaceUserLine(String id, String newLine) {
        for (int i = 0; i < allUserLines.size(); i++) {
            String[] parts = allUserLines.get(i).split("\\|", -1);
            if (parts.length >= 6 && parts[0].trim().equals(id)) {
                allUserLines.set(i, newLine);
                return;
            }
        }
    }

    private void writeSalaryFile() {
        ArrayList<String> lines = new ArrayList<>();
        for (Map.Entry<String, Double> e : salaryMap.entrySet()) {
            lines.add(e.getKey() + "|" + moneyFmt.format(e.getValue()));
        }
        FileUtil.write(STAFF_FILE, lines, s -> s);
    }
}