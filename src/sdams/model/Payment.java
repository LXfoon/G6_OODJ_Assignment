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
public class Payment {
    private String paymentId, bookingId, studentId;
    private PaymentStatus status;
    private double amount;
    private LocalDate paymentDate;
    
    public Payment(String paymentId, String bookingId, String studentId, double amount, LocalDate paymentDate, PaymentStatus status){
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }
    
    public enum PaymentStatus{
        Paid,
        Refunded;
        
        public static PaymentStatus fromString(String s){
            if(s == null) return null;
            
            for(PaymentStatus payment : values()){
                if(payment.name().equalsIgnoreCase(s.trim())) return payment;
            }
            
            return null;
        }
    }
    
    public String getPaymentId(){
        return this.paymentId;
    }
    
    public String getBookingId(){
        return this.bookingId;
    }
    
    public String getStudentId(){
        return this.studentId;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public LocalDate getPaymentDate(){
        return this.paymentDate;
    }
    
    public PaymentStatus getStatus(){
        return this.status;
    }
    
    public void setPaymentId(String paymentId){
        this.paymentId = paymentId;
    }
    
    public void setBookingId(String bookingId){
        this.bookingId = bookingId;
    }
    
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    
    public void setAmount(double amount){
        this.amount = amount;
    }
    
    public void setPaymentDate(LocalDate paymentDate){
        this.paymentDate = paymentDate;
    }
    
    public void setStatus(PaymentStatus status){
        this.status = status;
    }
    
    public static Payment fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 6) return null;
        
        String paymentId = parts[0].trim();
        String bookingId = parts[1].trim();
        String studentId = parts[2].trim();
        double amount = Double.parseDouble(parts[3].trim());
        LocalDate paymentDate = LocalDate.parse(parts[4].trim(), formatter);
        PaymentStatus status = PaymentStatus.fromString(parts[5].trim());
        
        return new Payment(paymentId, bookingId, studentId, amount, paymentDate, status);
    }
    
    public String toLine(){
        return this.paymentId + "|" + this.bookingId + "|" + this.studentId + "|" + this.amount + "|" + this.paymentDate+ "|" + this.status;
    }
}
