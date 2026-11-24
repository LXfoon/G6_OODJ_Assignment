/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import sdams.util.Generator;
import sdams.util.FileUtil;
import sdams.util.UserManager;
import sdams.util.Validator;

public class ManageStaffServices implements UserManager {

    private static final String USER_FILE  = "user.txt";
    private static final String STAFF_FILE = "staff.txt";
    private static final String CLASS_FILE = "class.txt";

    private final ArrayList<Staff> allStaff = new ArrayList<>();
    private final ArrayList<User> allUser = new ArrayList<>();
    private final ArrayList<String> allUserLines = new ArrayList<>();
    
    private String pendingId;
    private String pendingPassword;

    @Override
    public void loadUser() {
        allStaff.clear();
        allUser.clear();
        
        ArrayList<String> salaryLines = new ArrayList<>();
        FileUtil.read(STAFF_FILE, salaryLines, s -> s);
        
        FileUtil.read(USER_FILE, allUserLines, s -> s);
        
        for (String line : allUserLines) {
            Staff s = Staff.fromLines(line, salaryLines);
            
            if (s != null) {
                if (!s.isActive()) continue; // exclude deactivated users
                allStaff.add(s);  
                allUser.add(s);
            } else {
                Manager m = Manager.fromLine(line);
                if (m != null) allUser.add(m); // <- XW CHANGED
            }
        }
        
    }
    
    @Override
    public void addUser(String name, String email, String phoneNum) {
        loadUser();
        String password = Generator.passwordGenerator();
        Validator.userValidator(name, email, password, phoneNum);
        Validator.dupEmailChecker(email, null, allUser); 
        Validator.inputFieldChecker(new ArrayList<>(List.of(name, email, password)));
        String id = Generator.idGenerator("", USER_FILE);

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
            case "Instructor" ->  new Instructor(id, name, email, password, phone, "Active", salary);
            case "Facilities" -> new FacOfficer(id, name, email, password, phone, "Active", salary);
            case "Attendance" -> new AttOfficer(id, name, email, password, phone, "Active", salary);
            case "Admin" -> new Admin(id, name, email, password, phone, "Active", salary); //CHANGED
            default -> new Instructor(id, name, email, password, phone, "Active", salary);
        };

        allStaff.add(staff);
        allUserLines.add(staff.toLine());

        FileUtil.write(USER_FILE, allUserLines, s -> s);
        writeSalaryFile();

        JOptionPane.showMessageDialog(null, "Staff created successfully!\n\nID: " + id + "\nPassword: " + password);
        return id;
    }
    
    @Override
    public void updateUser(String id, String name, String email, String password, String phoneNum) {
        // role cannot be edited
        Validator.userValidator(name, email, password, phoneNum);
        Validator.dupEmailChecker(email, id, allUser); //CHANGED
        Validator.inputFieldChecker(new ArrayList<>(List.of(name, email, password))); //CHANGED

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
    
    public void updateStaff(String id, String name, String email, String password, String phoneNum, double salary) {
        loadUser();
        Staff staff = findById(id);
        if (staff == null) {
            JOptionPane.showMessageDialog(null, "Staff not found: " + id);
            return;
        }
        
        if (salary < 0) {
            JOptionPane.showMessageDialog(null, "Salary must be 0 or greater.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        updateUser(id, name, email, password, phoneNum);
        
        staff.setSalary(salary); 
        writeSalaryFile();
        
        JOptionPane.showMessageDialog(null, "Staff edited successfully!");
    }

    @Override
    public void deleteUser(String id) {
        loadUser();
        Staff staff = findById(id);
        if (staff == null) {
            JOptionPane.showMessageDialog(null, "Staff not found: " + id);
            return;
        }

        if (!staff.isActive()) {
            JOptionPane.showMessageDialog(null, "Staff is already inactive: " + id);
            return;
        }

        // cannot deactivate if instructor has upcoming classes
        if (staff instanceof Instructor && hasActiveClasses(id)) {
            JOptionPane.showMessageDialog(null,
                    "Cannot deactivate this instructor.\n\n"
                    + "They still have upcoming classes scheduled.\n"
                    + "Complete or cancel those classes first.",
                    "Active Classes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Deactivate staff " + id + "?\n\n"
                + "The account will be marked Inactive and cannot log in.",
                "Confirm Deactivate", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        staff.setStatus("Inactive");
        replaceUserLine(id, staff.toLine());
        FileUtil.write(USER_FILE, allUserLines, s -> s);

        JOptionPane.showMessageDialog(null, "Staff account deactivated: " + id);
    }

    private boolean hasActiveClasses(String instructorId) {
        ArrayList<String> lines = new ArrayList<>();
        FileUtil.read(CLASS_FILE, lines, s -> s);

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 9) continue;
            if (!p[3].trim().equals(instructorId)) continue;

            String status = p[8].trim();
            if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Cancelled")) continue;
            return true;
        }
        return false;
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
        if (role == null) return "Instructor";
        String r = role.trim().toLowerCase();
        
        return switch (r) {
            case "instructor" -> "Instructor";
            case "facilities" -> "Facilities";
            case "attendance" -> "Attendance";
            case "admin" -> "Admin";
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
            if (parts.length >= 7 && parts[0].trim().equals(id)) {
                allUserLines.set(i, newLine);
                return;
            }
        }
    }

    private void writeSalaryFile() {
        ArrayList<String> lines = new ArrayList<>();
        for (Staff s : allStaff) {
            lines.add(s.toSalaryLine());
        }
        FileUtil.write(STAFF_FILE, lines, s -> s);
    }
}