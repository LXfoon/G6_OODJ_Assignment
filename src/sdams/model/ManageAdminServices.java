/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import sdams.util.FileUtil;
import static sdams.util.Generator.idGenerator;
import static sdams.util.Generator.passwordGenerator;
import sdams.util.UserManager;
import sdams.util.Validator;

/**
 *
 * @author Foon
 */
public class ManageAdminServices implements UserManager{
    ArrayList<Admin> allAdmin = new ArrayList<>();
    
    @Override
    public void loadUser() {
        allAdmin.clear();
        FileUtil.read("user.txt", allAdmin, Admin::fromLine);
    }

    @Override
    public void addUser(String name, String email, String phoneNum) {        
        String id = idGenerator("", "user.txt");
        String password = passwordGenerator();
        String role = "Admin";

        Validator.userValidator(name, email, password, phoneNum);

        loadUser();
        
        //replace with actual admin class later
        Admin newAdmin = new Admin(id, name, email, password, phoneNum, role);
        allAdmin.add(newAdmin);

        FileUtil.write("user.txt", allAdmin, User::toLine);
        JOptionPane.showMessageDialog(null, "Admin created successfully!");
    }

    @Override
    public void updateUser(String id, String name, String email, String password, String phoneNum) {
        loadUser();
        Validator.userValidator(name, email, password, phoneNum);

        for(Admin a : allAdmin){
            if(a.getId().equals(id)){
                a.setName(name);
                a.setEmail(email);
                a.setPassword(password);
                a.setPhoneNum(phoneNum);

            }
        }

        FileUtil.write("user.txt", allAdmin, Admin::toLine);

        JOptionPane.showMessageDialog(null, "Admin edited successfully!");
    }

    @Override
    public void deleteUser(String id) {
        loadUser();
        for(Admin a : allAdmin){
            if(a.getId().equals(id)){
                allAdmin.remove(a);
                break;
            }
        }
            
        FileUtil.write("user.txt", allAdmin, Admin::toLine);
        JOptionPane.showMessageDialog(null, "Admin deleted successfully!");
    }
}
