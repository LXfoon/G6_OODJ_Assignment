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
import sdams.util.Validator;

/**
 *
 * @author Foon
 */
public class ManageAdminServices implements UserManager{

    @Override
    public void addUser(String name, String email, String phoneNum) {
            ArrayList<User> allUser = new ArrayList<>();
        
            String id = idGenerator("");
            String password = passwordGenerator();
            String role = "Admin";

            Validator.userValidator(name, email, password, phoneNum);

            FileUtil.read("user.txt", allUser, Manager::fromLine);
            //replace with actual admin class later
            Admin newAdmin = new Admin(id, name, email, password, phoneNum, role);
            allUser.add(newAdmin);

            FileUtil.write("user.txt", allUser, User::toLine);
            JOptionPane.showMessageDialog(null, "Admin created successfully!");
    }

    @Override
    public void updateUser(String name, String email, String password, String phoneNum, String id, Admin selectedAdmin, ArrayList<Admin> adminList) {
            Validator.userValidator(name, email, password, phoneNum);
            
            for(int i=0; i<adminList.size(); i++){
                if(adminList.get(i).getId().equals(id)){
                    adminList.set(i, selectedAdmin);
                }
            }

            FileUtil.write("user.txt", adminList, Admin::toLine);
            
            JOptionPane.showMessageDialog(null, "Admin edited successfully!");
    }

    @Override
    public void deleteUser(ArrayList<Admin> adminList, Admin selectedAdmin) {
        adminList.remove(selectedAdmin);
            
        FileUtil.write("user.txt", adminList, Admin::toLine);
        JOptionPane.showMessageDialog(null, "Admin deleted successfully!");
    }
    
}
