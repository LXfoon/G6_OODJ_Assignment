/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author xiaow
 */
public class Instructor extends Staff {
    
    public Instructor() { 
        super(); 
    }
    
    public Instructor(String id, String name, String email, String password,String phoneNum, double salary) {
        super(id, name, email, password, phoneNum, "Instructor", salary);
    }
}