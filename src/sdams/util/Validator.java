/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import java.util.List;
import java.util.regex.Pattern;
import sdams.exception.ValidationException;
import sdams.model.User;

/**
 *
 * @author Foon
 */
public class Validator {
    private static final Pattern PHONE = Pattern.compile("^(\\+?60|0)?1[0-9]{8,9}$");
    
    public static void userValidator(String name, String email, String password, String phoneNum){
        if(name.equals("")){
            throw new ValidationException(
                "Name is required."
            );
        }
        
        if(password.equals("")){
            throw new ValidationException(
                "Password is required."
            );
        }
        
        if(!email.contains("@") || email.equals("")){
            throw new ValidationException(
                "Invaild email."
            );
        }
        
        if(!PHONE.matcher(phoneNum.trim()).matches() || phoneNum.equals("")){
            throw new ValidationException(
                "Invaild phone number."
            );
        }
    }
    
    public static void dupEmailChecker(String email, List<? extends User> allUser){        
        dupEmailChecker(email, null, allUser);
    }
    
    public static void dupEmailChecker(String email, String currentId, List<? extends User> allUser){        
        for(User u : allUser){
            if(u.getId() == null || u.getEmail() == null) continue;
            if(currentId != null && u.getId().equalsIgnoreCase(currentId)) continue;
            String existingEmail = u.getEmail();
            
            if(existingEmail.equalsIgnoreCase(email)){
                throw new ValidationException(
                    "Same email cannot be used."
                );
            }
        }
    }
    
    public static void inputFieldChecker(List<String> inputs){
        for(String input : inputs){
            if(input.equals("")){
                throw new ValidationException(
                    "Input field cannot be empty."
                );
            }
            
            if(input.contains("|")){
                throw new ValidationException(
                    "Invalid character is found (|)."
                );
            }
        }
    }
}
