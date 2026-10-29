/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;

/**
 *
 * @author Foon
 */
public class Generator {
    public static String idGenerator(){
        String userId="";
        int maxId = 0;
        
        try{
           FileReader fr = new FileReader("user.txt");
            BufferedReader br = new BufferedReader(fr); 
            String line;
            
            while((line = br.readLine()) != null){
                String parts[] = line.split("\\|");
                if (parts.length == 0 || parts[0].trim().isEmpty()) continue;
                
                int current = Integer.parseInt(parts[0]);
                if(current > maxId) maxId = current;
            }
            
            maxId += 1;
            userId = Integer.toString(maxId);
            System.out.println(userId);
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        return userId;
    }
    
    public static String passwordGenerator(){
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(pool.length());
            password.append(pool.charAt(idx));
        }

        return password.toString();
    }
}
