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
        String paymentId, double amount, String paymentDate, String paymentStatus, String paymentMethod,
        String studentId, String studentName,
        String bookingId, String bookingStatus, String bookingDate,
        String classId, String className,
        String instructorId, String instructorName,
        String classDate, String classStartTime, String classEndTime, double classPrice
    ) {}

    public PaymentDetails getPaymentDetails(String paymentId) {
        if (paymentId == null || paymentId.isBlank()) return null;

        // Payment
        String payLine = findRawLine(PAYMENT_FILE, paymentId);
        if (payLine == null) return null;
        Payment pay = Payment.fromLine(payLine);
        if (pay == null) return null;

        String bookingId = pay.getBookingId();
        String studentId = pay.getStudentId();
        double amount = pay.getAmount();
        String paymentDate = pay.getPaymentDate() == null ? "-" : pay.getPaymentDate().toString();
        String paymentStatus = pay.getStatus() == null ? "-" : pay.getStatus().name();
        String paymentMethod = pay.getPaymentMethod();
        String studentName = findUserName(studentId);

        // Booking
        String bookingLine = findRawLine(BOOKING_FILE, bookingId);
        Booking booking = (bookingLine == null) ? null : Booking.fromLine(bookingLine);

        String classId = (booking == null) ? "" : booking.getClassId();
        String bookingStatus = (booking == null || booking.getStatus() == null) ? "-" : booking.getStatus().name();
        String bookingDate = (booking == null || booking.getBookingDate() == null) ? "-" : booking.getBookingDate().toString();

        // ClassSession
        String classLine = findRawLine(CLASS_FILE, classId);
        ClassSession cls = (classLine == null) ? null : ClassSession.fromLine(classLine);

        String className = (cls == null) ? ""  : cls.getClassName();
        String instructorId = (cls == null) ? ""  : cls.getInstructorId();
        String classDate = (cls == null || cls.getDate() == null) ? "-" : cls.getDate().toString();
        String startTime = (cls == null || cls.getStartTime() == null) ? "-" : cls.getStartTime().toString();
        String endTime = (cls == null || cls.getEndTime()   == null) ? "-" : cls.getEndTime().toString();
        double classPrice = (cls == null) ? 0.0 : cls.getFee();

        String instructorName = findUserName(instructorId);

        return new PaymentDetails(
            paymentId, amount, paymentDate, paymentStatus, paymentMethod,
            studentId, studentName,
            bookingId, bookingStatus, bookingDate,
            classId, className,
            instructorId, instructorName,
            classDate, startTime, endTime, classPrice
        );
    }

    private String findRawLine(String file, String id) {
        if (id == null || id.isBlank()) return null;
        ArrayList<String> lines = new ArrayList<>();
        FileUtil.read(file, lines, s -> s);
        for (String line : lines) {
            if (line.isBlank()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length >= 1 && p[0].trim().equals(id)) return line;
        }
        return null;
    }

    private String findUserName(String userId) {
        if (userId == null || userId.isBlank()) return "(unknown)";
        String line = findRawLine(USER_FILE, userId);
        if (line == null) return "(unknown)";
        String[] p = line.split("\\|", -1);
        return (p.length > 1) ? p[1].trim() : "(unknown)";
    }
}
