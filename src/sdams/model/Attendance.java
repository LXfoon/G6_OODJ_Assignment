package sdams.model;
public class Attendance {
    private String attendanceId, bookingId, studentId, classId;
    private AttStatus status;
    
    public Attendance(String attendanceId, String bookingId, String studentId, String classId, AttStatus status){
        this.attendanceId = attendanceId;
        this.bookingId = bookingId;
        this.studentId = studentId;
        this.classId = classId;
        this.status = status;
    }
    
    public enum AttStatus{
        Present,
        Absate,
        Late;
        
        public static AttStatus fromString(String s){
            if(s == null) return null;
            
            for(AttStatus attStatus: values()){
                if(attStatus.name().equalsIgnoreCase(s.trim())) return attStatus;
            }
            
            return null;
        }
    }
    
    public String getAttendanceId(){
        return this.attendanceId;
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
    
    public AttStatus getStatus(){
        return this.status;
    }
    
    public void setAttendanceId(String attendanceId){
        this.attendanceId = attendanceId;
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
    
    public void setStatus(AttStatus status){
        this.status = status;
    }
    
    public static Attendance fromLine(String line){
        String parts[] = line.split("\\|");
        if(parts.length < 5) return null;
        
        String attendanceId = parts[0].trim();
        String bookingId = parts[1].trim();
        String studentId = parts[2].trim();
        String classId = parts[3].trim();
        AttStatus status = AttStatus.fromString(parts[4].trim());
        
        return new Attendance(attendanceId, bookingId, studentId, classId, status);
    }
    
    public String toLine(){
        return this.attendanceId + "|" + this.bookingId + "|" + this.studentId + "|" + this.classId + "|" + this.status;
    }
}
