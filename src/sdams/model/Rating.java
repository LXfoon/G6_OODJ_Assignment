package sdams.model;

import java.time.LocalDate;
import static sdams.model.ClassSession.formatter;

public class Rating {
    private String ratingId, studentId, classId, instructorId, comment;
    private int score;
    private LocalDate ratingDate;
    private RatingStatus status;
    
    public Rating(String ratingId, String studentId, String classId, String instructorId, String comment, int score, LocalDate ratingDate, RatingStatus status){
        this.ratingId = ratingId;
        this.studentId = studentId;
        this.classId = classId;
        this.instructorId = instructorId;
        this.comment = comment;
        this.score = score;
        this.ratingDate = ratingDate;
        this.status = status;
    }

    public enum RatingStatus{
        Rated,
        Unrated;
        
        public static RatingStatus fromString(String s){
            if(s == null) return null;
            
            for(RatingStatus ratingStatus : values()){
                if(ratingStatus.name().equalsIgnoreCase(s.trim())) return ratingStatus;
            }
            
            return null;
        }
    }
    
    public String getRatingId(){
        return this.ratingId;
    }
    
    public String getStudentId(){
        return this.studentId;
    }
    
    public String getClassId(){
        return this.classId;
    }
    
    public String getInstructorId(){
        return this.instructorId;
    }
    
    public String getComment(){
        return this.comment;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public LocalDate getRatingDate(){
        return this.ratingDate;
    }
    
    public RatingStatus getStatus(){
        return this.status;
    }
    
    public void setRatingId(String ratingId){
        this.ratingId = ratingId;
    }
    
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    
    public void setClassId(String classId){
        this.classId = classId;
    }
    
    public void setInstructorId(String instructorId){
        this.instructorId = instructorId;
    }
    
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void setRatingDate(LocalDate ratingDate){
        this.ratingDate = ratingDate;
    }
    
    public void setStatus(RatingStatus status){
        this.status = status;
    }
    
    public static Rating fromLine(String line) {
        LocalDate ratingDate = null;
        int score = 0;
        String parts[] = line.split("\\|", -1);
        if(parts.length < 8) return null;
        
        String ratingId = parts[0].trim();
        String studentId = parts[1].trim();
        String classId = parts[2].trim();
        String instructorId = parts[3].trim();
        if(!parts[4].trim().equals("-") && !parts[4].trim().isEmpty()){
            ratingDate = LocalDate.parse(parts[4].trim(), formatter);
        }
        if(!parts[5].trim().equals("-") && !parts[5].trim().isEmpty()){
            score = Integer.parseInt(parts[5].trim());
        }
        String comment = parts[6].trim();
        RatingStatus status = RatingStatus.fromString(parts[7].trim());
        
        return new Rating(ratingId, studentId, classId, instructorId, comment, score, ratingDate, status);
    }
    
    public String toLine(){
        String Date = (this.ratingDate == null) ? "-" : this.ratingDate.toString();
        String Score = (this.score == 0) ? "-" : String.valueOf(this.score);
        String Comment = (this.comment == null || this.comment.trim().isEmpty()) ? "-" : this.comment;
        return this.ratingId + "|" + this.studentId + "|" + this.classId + "|" + this.instructorId + "|" + Date + "|" + Score + "|" + Comment + "|" + this.status;
    }
}
