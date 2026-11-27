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
public class AttendanceRecord {
    private String attId, studentId, classId, recordedBy;
    private AttStatus status;
    private LocalDate recordedDate;

    public AttendanceRecord(String attId, String studentId, String classId, String recordedBy, AttStatus status, LocalDate recordedDate) {
        this.attId = attId;
        this.studentId = studentId;
        this.classId = classId;
        this.recordedBy = recordedBy;
        this.status = status;
        this.recordedDate = recordedDate;
    }
    
    public enum AttStatus{
        Present,
        Absent,
        Late;
        
        public static AttStatus fromString(String s){
            if(s == null) return null;
            
            for(AttStatus attStatus : values()){
                if(attStatus.name().equalsIgnoreCase(s.trim())) return attStatus;
            }
            
            return null;
        } 
    }
    
    public String getAttId(){
        return this.attId;
    }
    
    public String getStudentId(){
        return this.studentId;
    }
    
    public String getClassId(){
        return this.classId;
    }
    
    public String getRecordedBy(){
        return this.recordedBy;
    }
    
    public AttStatus getStatus(){
        return this.status;
    }
    
    public LocalDate getRecordedDate(){
        return this.recordedDate;
    }
    
    public void setAttId(String attId){
        this.attId = attId;
    }
    
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    
    public void setClassId(String classId){
        this.classId = classId;
    }
    
    public void setRecordedBy(String recordedBy){
        this.recordedBy = recordedBy;
    }
    
    public void setStatus(AttStatus status){
        this.status = status;
    }
    
    public void setRecordedDate(LocalDate recordedDate){
        this.recordedDate = recordedDate;
    }
    
    public static AttendanceRecord fromLine(String line) {
        String parts[] = line.split("\\|");
        if(parts.length < 6) return null;
        
        String attId = parts[0].trim();
        String studentId = parts[1].trim();
        String classId = parts[2].trim();
        String recordedBy = parts[3].trim();
        AttStatus status = AttStatus.fromString(parts[4].trim());
        LocalDate recordedDate = LocalDate.parse(parts[5].trim(), formatter);
        
        return new AttendanceRecord(attId, studentId, classId, recordedBy, status, recordedDate);    
    }
    
    public String toLine(){
        return this.attId + "|" + this.studentId + "|" + this.classId + "|" + this.recordedBy + "|" + this.status+ "|" + this.recordedDate;
    }
}
