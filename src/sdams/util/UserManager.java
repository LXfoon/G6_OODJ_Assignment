/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sdams.util;

/**
 *
 * @author Foon
 */
public interface UserManager {
    void loadUser();
    void addUser(String name, String email, String phoneNum);
    void updateUser(String id, String name, String email, String password, String phoneNum);
    void deleteUser(String id);
}
