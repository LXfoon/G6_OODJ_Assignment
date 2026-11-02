/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Foon
 */
public class ClassSession {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private String classId, className, discipline, instructorId, status;
    private LocalDate date;
    private LocalTime startTime, endTime;
    private double fee;

    private ClassSession(String classId, String className, String discipline, String instructorId, LocalDate date, LocalTime startTime, LocalTime endTime, double fee, String status) {
        this.classId = classId;
        this.className = className;
        this.discipline = discipline;
        this.instructorId = instructorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fee = fee;
        this.status = status;
    }
    
    enum Status{
        Available,
        Full,
        Cancelled,
        Completed
    }
    
    public String getClassId(){
        return this.classId;
    }
    
    public String getClassName(){
        return this.className;
    }
    
    public String getDiscipline(){
        return this.discipline;
    }
    
    public String getInstructorId(){
        return this.instructorId;
    }
    
    public LocalDate getDate(){
        return this.date;
    }
    
    public LocalTime getStartTime(){
        return this.startTime;
    }
    
    public LocalTime getEndTime(){
        return this.endTime;
    }
    
    public double getFee(){
        return this.fee;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setClassId(String classId){
        this.classId = classId;
    }
    
    public void setClassName(String className){
        this.className = className;
    }
    
    public void setDiscipline(String discipline){
        this.discipline = discipline;
    }
    
    public void setInstructorId(String instructorId){
        this.instructorId = instructorId;
    }
    
    public void setDate(LocalDate date){
        this.date = date;
    }
    
    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }
    
    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }
    
    public void setFee(double fee){
        this.fee = fee;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public static ClassSession fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 9) return null;
        
        String classId = parts[0].trim();
        String className = parts[1].trim();
        String discipline = parts[2].trim();
        String instructorId = parts[3].trim();
        LocalDate date = LocalDate.parse(parts[4].trim(), formatter);
        LocalTime startTime = LocalTime.parse(parts[5].trim());
        LocalTime endTime = LocalTime.parse(parts[6].trim());
        double fee = Double.parseDouble(parts[7].trim());
        String status = parts[8].trim();
        
        return new ClassSession(classId, className, discipline, instructorId, date, startTime, endTime, fee, status);    
    }
}
