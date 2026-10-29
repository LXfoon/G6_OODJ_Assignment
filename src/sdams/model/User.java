/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author Foon
 */
public abstract class User {    
    private String id, name, email, password, role;
    private String phoneNum;
    
    public User(){};
    
    public User(String id, String name, String email, String password, String phoneNum, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.role = role;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getPhoneNum(){
        return this.phoneNum;
    }
    
    public String getRole(){
        return this.role;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String toLine(){
        return this.id + "|" + this.name + "|" + this.email + "|" + this.password + "|" + this.phoneNum+ "|" + this.role;
    }
}
