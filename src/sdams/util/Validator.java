/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import java.util.regex.Pattern;
import sdams.exception.ValidationException;

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
}
