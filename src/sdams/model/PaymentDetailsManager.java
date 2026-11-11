/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

import java.util.ArrayList;
import sdams.util.FileUtil;

/**
 *
 * @author xiaow
 */
public class PaymentDetailsManager {
        
    private static final String USER_FILE = "user.txt";
    private static final String PAYMENT_FILE = "payment.txt";
    private static final String BOOKING_FILE = "booking.txt";
    private static final String CLASS_FILE = "class.txt";
    
    public record PaymentDetails(
        String paymentId, double amount, String paymentDate, String paymentStatus,
        String studentId, String studentName,
        String bookingId, String bookingStatus, String bookingDate,
        String classId, String className,
        String instructorId, String instructorName,
        String classDate, String classStartTime, String classEndTime, double classPrice
    ) {}

    public PaymentDetails getPaymentDetails(String paymentId) {
        if (paymentId == null || paymentId.isBlank()) return null;

        String[] pay = findLine(PAYMENT_FILE, paymentId);
        if (pay == null || pay.length < 6) return null;

        String bookingId = pay[1].trim();
        String studentId = pay[2].trim();
        double amount = parseDouble(pay[3]);
        String paymentDate = pay[4].trim();
        String paymentStatus = pay[5].trim();

        String studentName = findUserName(studentId);

        String[] booking = findLine(BOOKING_FILE, bookingId);
        String classId = booking != null && booking.length > 2 ? booking[2].trim() : "";
        String bookingStatus = booking != null && booking.length > 3 ? booking[3].trim() : "";
        String bookingDate = booking != null && booking.length > 4 ? booking[4].trim() : "";

        String[] cls = findLine(CLASS_FILE, classId);
        String className = cls != null && cls.length > 1 ? cls[1].trim() : "";
        String instructorId = cls != null && cls.length > 3 ? cls[3].trim() : "";
        String classDate = cls != null && cls.length > 4 ? cls[4].trim() : "";
        String startTime = cls != null && cls.length > 5 ? cls[5].trim() : "";
        String endTime = cls != null && cls.length > 6 ? cls[6].trim() : "";
        double classPrice = cls != null && cls.length > 7 ? parseDouble(cls[7]) : 0.0;

        String instructorName = findUserName(instructorId);

        return new PaymentDetails(
            paymentId, amount, paymentDate, paymentStatus,
            studentId, studentName,
            bookingId, bookingStatus, bookingDate,
            classId, className,
            instructorId, instructorName,
            classDate, startTime, endTime, classPrice
        );
    }

    private String[] findLine(String file, String id) {
        if (id == null || id.isBlank()) return null;
        ArrayList<String> lines = new ArrayList<>();
        FileUtil.read(file, lines, s -> s);
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length >= 1 && p[0].trim().equals(id)) return p;
        }
        return null;
    }

    private String findUserName(String userId) {
        String[] line = findLine(USER_FILE, userId);
        return (line != null && line.length > 1) ? line[1].trim() : "(unknown)";
    }

    private double parseDouble(String s) {
        try { 
            return Double.parseDouble(s.trim()); 
        } catch (Exception e) { 
            return 0.0;
        }
    }
}
