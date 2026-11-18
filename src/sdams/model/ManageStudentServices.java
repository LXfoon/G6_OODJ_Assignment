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
            if (parts.length < 6) continue;
            
            if (stu != null) {
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
        Student student = new Student(id, name, email, password, phoneNum);
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

        allUserLines.removeIf(line -> {
            String[] parts = line.split("\\|", -1);
            return parts.length >= 1 && parts[0].trim().equals(id);
        });
        allStudents.removeIf(stu -> stu.getId().equals(id));

        FileUtil.write(USER_FILE, allUserLines, stu -> stu);
        JOptionPane.showMessageDialog(null, "Student deleted successfully!");
    }
    
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(allStudents);
    }

    public Student findById(String id) {
        for (Student stu : allStudents) if (stu.getId().equals(id)) return stu;
        return null;
    }

    public int count() { return allStudents.size(); }
    
    private boolean replaceUserLine(String id, String newLine) {
        for (int i = 0; i < allUserLines.size(); i++) {
            String[] parts = allUserLines.get(i).split("\\|", -1);
            if (parts.length >= 6 && parts[0].trim().equals(id)) {
                allUserLines.set(i, newLine);
                return true;
            }
        }
        return false;
    }
}
