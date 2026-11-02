/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.time.LocalDate;
import static sdams.model.ClassSession.formatter;

/**
 *
 * @author Foon
 */
public class Booking {
    private String bookingId, studentId, classId, status;
    private LocalDate bookingDate;
    
    public Booking(String bookingId, String studentId, String classId, String status, LocalDate bookingDate){
        this.bookingId = bookingId;
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
        this.bookingDate = bookingDate;
    }
    
    public String getBookingId(){
        return this.bookingId;
    }
    
    public String getStudentId(){
        return this.studentId;
    }
    
    public String getClassId(){
        return this.classId;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public LocalDate getBookingDate(){
        return this.bookingDate;
    }
    
    public void setBookingId(String bookingId){
        this.bookingId = bookingId;
    }
    
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    
    public void setClassId(String classId){
        this.classId = classId;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public void setBookingDate(LocalDate bookingDate){
        this.bookingDate = bookingDate;
    }
    
    enum bookingStatus{
        Booked,
        Rescheduled,
        Cancelled,
        Completed
    }
    
    public static Booking fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 5) return null;
        
        String bookingId = parts[0].trim();
        String studentId = parts[1].trim();
        String classId = parts[2].trim();
        String status = parts[3].trim();
        LocalDate bookingDate = LocalDate.parse(parts[4].trim(), formatter);
        
        return new Booking(bookingId, studentId, classId, status, bookingDate);    
    }
}
