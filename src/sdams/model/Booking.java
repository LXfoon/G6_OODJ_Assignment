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
    private String bookingId, studentId, classId;
    private BookingStatus status;
    private LocalDate bookingDate;
    
    public enum BookingStatus{
        Booked,
        Rescheduled,
        Cancelled,
        Completed;
        
        public static BookingStatus fromString(String s){
            if(s == null) return null;
            for(BookingStatus booking : values()){
                if(booking.name().equalsIgnoreCase(s.trim())) return booking;
            }
            return null;
        }
    }
    
    public Booking(String bookingId, String studentId, String classId, BookingStatus status, LocalDate bookingDate){
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
    
    public BookingStatus getStatus(){
        return status;
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
    
    public void setStatus(BookingStatus status){
        this.status = status;
    }
    
    public void setBookingDate(LocalDate bookingDate){
        this.bookingDate = bookingDate;
    }
    
    public static Booking fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 5) return null;
        
        String bookingId = parts[0].trim();
        String studentId = parts[1].trim();
        String classId = parts[2].trim();
        BookingStatus status = BookingStatus.fromString(parts[3].trim());
        LocalDate bookingDate = LocalDate.parse(parts[4].trim(), formatter);
        
        return new Booking(bookingId, studentId, classId, status, bookingDate);    
    }
    
    public String toLine(){
        return this.bookingId + "|" + this.studentId + "|" + this.classId + "|" + this.status + "|" + this.bookingDate;
    }
}
