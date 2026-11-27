/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import sdams.util.FileUtil;
import sdams.util.Generator;
import sdams.util.UserManager;
import sdams.util.Validator;

/**
 *
 * @author xiaow
 */
public class ManageStudentServices implements UserManager {
    
    private static final String USER_FILE = "user.txt";
    private static final String BOOKING_FILE = "booking.txt";
    
    private final ArrayList<User> allUser = new ArrayList<>();  
    private final ArrayList<Student> allStudents = new ArrayList<>();
    private final ArrayList<String> allUserLines = new ArrayList<>();
    
    @Override
    public void loadUser() {
        allStudents.clear();
        allUserLines.clear();
        allUser.clear();
        FileUtil.read(USER_FILE, allUserLines, stu -> stu);
        for (String line : allUserLines) {
            Student stu = Student.fromLine(line);
            
            String[] parts = line.split("\\|", -1);
            if (parts.length < 7) continue;
            
            if (stu != null) {
                if (!stu.isActive()) continue; // exclude deactivated accounts
                allStudents.add(stu);
            } else {
                Manager m = Manager.fromLine(line);
                if (m != null) allUser.add(m);
            }
            
        }
    }
    
    @Override
    public void addUser(String name, String email, String phoneNum) {
        String password = Generator.passwordGenerator();
        
        Validator.userValidator(name, email, password, phoneNum);
        Validator.inputFieldChecker(List.of(name, email, password, phoneNum));
        loadUser();
        Validator.dupEmailChecker(email, null, allUser);
        
        String id = Generator.idGenerator("", USER_FILE);
        Student student = new Student(id, name, email, password, phoneNum, "Active");
        allUserLines.add(student.toLine());
        allStudents.add(student);

        FileUtil.write(USER_FILE, allUserLines, s -> s);

        JOptionPane.showMessageDialog(null, "Student created successfully!\n\nID: " + id + "\nPassword: " + password);
    }
    
    @Override
    public void updateUser(String id, String name, String email, String password, String phoneNum) {
        loadUser();
        Validator.userValidator(name, email, password, phoneNum);
        
        Validator.dupEmailChecker(email, id, allUser);
        Validator.inputFieldChecker(new ArrayList<>(List.of(name, email, password)));

        Student student = findById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found: " + id);
            return;
        }

        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setPhoneNum(phoneNum);

        replaceUserLine(id, student.toLine());
        FileUtil.write(USER_FILE, allUserLines, stu -> stu);
        JOptionPane.showMessageDialog(null, "Student edited successfully!");
    }
    
    @Override
    public void deleteUser(String id) {
        loadUser();
        Student student = findById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(null, "Student not found: " + id);
            return;
        }

        if (!student.isActive()) {
            JOptionPane.showMessageDialog(null, "Student is already inactive: " + id);
            return;
        }

        // cannot deactivate if student has active bookings
        if (hasActiveBookings(id)) {
            JOptionPane.showMessageDialog(null,
                    "Cannot deactivate this student.\n\n"
                    + "They still have active class bookings.\n"
                    + "Cancel or complete those bookings first.",
                    "Active Bookings", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Deactivate student " + id + "?\n\n"
                + "The account will be marked Inactive and cannot log in.\n",
                "Confirm Deactivate", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        student.setStatus("Inactive");
        replaceUserLine(id, student.toLine());
        FileUtil.write(USER_FILE, allUserLines, s -> s);

        JOptionPane.showMessageDialog(null, "Student account deactivated: " + id);
    }
    
    private boolean hasActiveBookings(String studentId) {
        ArrayList<String> lines = new ArrayList<>();
        FileUtil.read(BOOKING_FILE, lines, s -> s);

        for (String l : lines) {
            String[] p = l.split("\\|", -1);
            if (p.length < 4) continue;
            if (!p[1].trim().equals(studentId)) continue;

            String status = p[3].trim();
            if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Cancelled")) continue;
            return true;
        }
        return false;
    }
    
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(allStudents);
    }

    public Student findById(String id) {
        for (Student stu : allStudents) {
            if (stu.getId().equals(id)) {
                return stu;
            }
        }
        return null;
    }

    public int count() { 
        return allStudents.size(); 
    }
    
    private boolean replaceUserLine(String id, String newLine) {
        for (int i = 0; i < allUserLines.size(); i++) {
            String[] parts = allUserLines.get(i).split("\\|", -1);
            if (parts.length >= 7 && parts[0].trim().equals(id)) {
                allUserLines.set(i, newLine);
                return true;
            }
        }
        return false;
    }
}
