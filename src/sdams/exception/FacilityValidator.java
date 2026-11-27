/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.exception;

/**
 *
 * @author User
 */
public class FacilityValidator {
    public static void equipmentValidator(String name, String quantity, String location){
        if (name.isEmpty()){
            throw new ValidationException("Equipment name is required.");
        }
        if (location.isEmpty()){
            throw new ValidationException("Location is required.");
        }
        if (name.contains("|") || location.contains("|")){
            throw new ValidationException("The | character is not allowed.");
        }
        int q;
        try{
            q = Integer.parseInt(quantity.trim());
        }catch (NumberFormatException e){
            throw new ValidationException("Quantity must be a whole number.");
        }
        if (q < 0) {
            throw new ValidationException("Quantity cannot be negative.");
        }
    }
    
    public static void problemValidator(String status, String resolution){
        if (status.isEmpty()){
            throw new ValidationException("Resolution detail is required when the status is Resolved.");
        }
        if (resolution.contains("|")){
            throw new ValidationException("The | character is not allowed.");
        }
    }
}
