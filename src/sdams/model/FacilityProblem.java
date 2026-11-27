/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sdams.model;

/**
 *
 * @author User
 */
public class FacilityProblem {
    private String problemId, equipmentId, description, reportedBy, dateReported;
    private String status, resolutionDetails;
    
    public FacilityProblem(String problemId, String equipmentId, String description,
                            String reportedBy, String dateReported, String status,
                            String resolutionDetails){
        this.problemId=problemId;
        this.equipmentId=equipmentId;
        this.description=description;
        this.reportedBy=reportedBy;
        this.dateReported=dateReported;
        this.status=status;
        this.resolutionDetails=resolutionDetails;
    }
    
    public String getProblemId(){
        return problemId;
    }
    public String getEquipmentId(){
        return equipmentId;
    }
    public String getDescription(){
        return description;
    }
    public String getReportedBy(){
        return reportedBy;
    }
    public String getDateReported(){
        return dateReported;
    }
    public String getStatus(){
        return status;
    }
    public String getResolutionDetails(){
        return resolutionDetails;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setResolutionDetails(String resolutionDetails){
        this.resolutionDetails=resolutionDetails;
    }
    
    public String toLine(){
        return problemId + "|" + equipmentId + "|" + description + "|" + reportedBy +
                "|" + dateReported + "|" + status + "|" + resolutionDetails;
    }
    
    public static FacilityProblem fromLine(String line){
        String[] p = line.split("\\|", -1);
        if (p.length < 7)
            return null;
        return new FacilityProblem(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(),
                                    p[4].trim(), p[5].trim(), p[6].trim());
    }
}
