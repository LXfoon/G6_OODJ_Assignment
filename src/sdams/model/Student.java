/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public class Student extends User {

    public static final String ROLE = "Student";

    public Student() {
        super();
    }

    public Student(String id, String name, String email, String password, String phoneNum, String status) {
        super(id, name, email, password, phoneNum, ROLE, status);
    }

    public static Student fromLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 7) return null;
        String role = parts[5].trim();
        if (!ROLE.equals(role)) return null;
        return new Student(parts[0].trim(), parts[1], parts[2], parts[3], parts[4], parts[6]);
    }
}
