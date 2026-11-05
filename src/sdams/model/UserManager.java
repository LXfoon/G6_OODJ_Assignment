/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;

/**
 *
 * @author Foon
 */
public interface UserManager {
    void addUser(String name, String email, String phoneNum);
    void updateUser(String name, String email, String password, String phoneNum, String id, Admin selectedAdmin, ArrayList<Admin> adminList);
    void deleteUser(ArrayList<Admin> adminList, Admin selectedAdmin);
}
