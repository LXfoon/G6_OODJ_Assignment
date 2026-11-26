/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author User
 */
public class Equipment {
    private String equipmentId, name, category, condition, location;
    private int quantity;
    
    public Equipment(String equipmentId, String name, String category,
                    int quantity, String condition, String location){
        this.equipmentId=equipmentId;
        this.name=name;
        this.category=category;
        this.quantity=quantity;
        this.condition=condition;
        this.location=location;
    }
    
    public String getEquipmentId(){
        return equipmentId;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getCondition(){
        return condition;
    }
    public String getLocation(){
        return location;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public void setCondition(String condition){
        this.condition=condition;
    }
    public void setLocation(String location){
        this.location=location;
    }
    
    public String toLine(){
        return equipmentId + "|" + name + "|" + category + "|" + quantity
                + "|" + condition + "|" + location;
    }
    
    public static Equipment fromLine(String line){
        String[] p = line.split("\\|");
        if (p.length < 6) return null;
        try{
            return new Equipment(p[0].trim(), p[1].trim(), p[2].trim(),
                                Integer.parseInt(p[3].trim()), p[4].trim(), p[5].trim());
        }catch (NumberFormatException e) {
            return null;
        }
     
    }
}
