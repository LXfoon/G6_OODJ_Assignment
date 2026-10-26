/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author Foon
 */
public class FileUtil {
    public static <Ent> void write(String fileName, ArrayList<Ent> dataList, Function<Ent, String>toLine){
        try{
            PrintWriter pw = new PrintWriter(fileName);
            for(int i=0; i<dataList.size(); i++){
                Ent et = dataList.get(i);
                pw.println(toLine.apply(et));
            }
            
            pw.close();
        }catch(IOException e){
            System.out.println("Error in write: "+e.getMessage());
        }
    }
    
    public static <Ent> void read(String fileName, ArrayList<Ent> dataList, Function<String, Ent>fromLine){
        try{
            dataList.clear();
            Scanner s = new Scanner(new File(fileName));
            while(s.hasNext()){
                String line = s.nextLine().trim();
                if(line.isEmpty()) continue;
                
                Ent et = fromLine.apply(line);
                if(et != null) dataList.add(et);
            }
        }catch (IOException e){
            System.out.println("Error in read: "+e.getMessage());
        }
    }
}
