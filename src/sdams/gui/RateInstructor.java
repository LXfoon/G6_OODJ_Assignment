/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sdams.model.Attendance;
import sdams.model.ClassSession;
import sdams.model.Instructor;
import sdams.model.Rating;
import sdams.model.Staff;
import sdams.util.FileUtil;

/**
 *
 * @author Mei Shi
 */
public class RateInstructor extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RateInstructor.class.getName());

    private String classId;
    private String studentId;
    private boolean ratingInfoLoaded = false;
    
    private Rating selectedRating = null;
    private Instructor selectedInstructor = null;
    
    private ArrayList<Attendance> attendanceList = new ArrayList<>();
    private ArrayList<Rating> ratingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Staff> staffList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    
    /**
     * Creates new form RateClass
     */
    public RateInstructor(String classId) {
        initComponents();
        this.classId = classId;
        
        txtaComment.setLineWrap(true);
        txtaComment.setWrapStyleWord(true);
        
        loadRatingInfo();
    }
    
    private void loadRatingInfo(){
        try{
            ratingInfoLoaded = false;
            selectedRating = null;
            selectedInstructor = null;
            
            this.studentId = LoggedIn.getId();
            if(this.studentId == null || this.studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            if(classId == null || classId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Invalid Class ID.");
                return;
            }
            
            FileUtil.read("attendance.txt", attendanceList, Attendance::fromLine);
            FileUtil.read("rating.txt", ratingList, Rating::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("user.txt", staffList, Staff::fromUserLine);
            
            // get all instructors
            instructorList.clear();

            for(Staff s : staffList){
                if(s instanceof Instructor i){
                    instructorList.add(i);
                }
            }
            
            // check student's attendance
            boolean hasAttended = false;
            for(Attendance a : attendanceList){
                if(a.getStudentId().equals(studentId) && a.getClassId().equals(classId) && (a.getStatus() == Attendance.AttStatus.Present || a.getStatus() == Attendance.AttStatus.Late)){
                    hasAttended = true;
                    break;
                }
            }
            
            if(!hasAttended){
                JOptionPane.showMessageDialog(null, "You are not eligible to rate this class because you have not attended it.");
                return;
            }
            
            // find rating records
            for(Rating r : ratingList){
                if(r.getClassId().equals(classId) && r.getStudentId().equals(studentId)){
                    selectedRating = r;
                    break;
                }
            }
            
            if(selectedRating == null){
                JOptionPane.showMessageDialog(null, "Rating record not found.");
                return;
            }
            
            // check rating status
            if(selectedRating.getStatus() != Rating.RatingStatus.Unrated){
                JOptionPane.showMessageDialog(null, "This class has already been rated.");
                return;
            }
            
            // find class information
            ClassSession selectedClass = null;
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(classId)){
                    selectedClass = cs;
                    break;
                }
            }
            
            if(selectedClass == null){
                JOptionPane.showMessageDialog(null, "Class record not found.");
                return;
            }
            
            // find instructor information
            for(Instructor i : instructorList){
                if(i.getId().equals(selectedClass.getInstructorId())){
                    selectedInstructor = i;
                    break;
                }
            }
            
            if(selectedInstructor == null){
                JOptionPane.showMessageDialog(null, "Instrutor record not found.");
                return;
            }
            
            // display information
            lblClassInfo.setText(selectedClass.getClassId() + ": " + selectedClass.getClassName());
            lblInstructorInfo.setText(selectedInstructor.getName());
            
            ratingInfoLoaded = true;
        } catch(Exception e){
            ratingInfoLoaded = false;
            JOptionPane.showMessageDialog(null, "Error loading details: " + e.getMessage());
        }
    }
    
    public boolean isRatingInfoLoaded(){
        return ratingInfoLoaded;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        spinnerScore = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaComment = new javax.swing.JTextArea();
        lblClassInfo = new javax.swing.JLabel();
        lblInstructorInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Rate Instructor");

        jLabel2.setText("Class");

        jLabel3.setText("Instructor");

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(this::btnSubmitActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        jLabel4.setText("Score");

        spinnerScore.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jLabel5.setText("Comment");

        txtaComment.setColumns(20);
        txtaComment.setRows(5);
        jScrollPane1.setViewportView(txtaComment);

        lblClassInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblInstructorInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(spinnerScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                                    .addComponent(lblClassInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblInstructorInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSubmit)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblClassInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblInstructorInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(spinnerScore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        InstructorRating instructorRating = new InstructorRating();
        instructorRating.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        spinnerScore.setValue(1);
        txtaComment.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        try{
            // check rating information
            if(!ratingInfoLoaded || selectedRating == null || selectedInstructor == null){
                JOptionPane.showMessageDialog(null, "Rating information is not available.");
                return;
            }
            
            // get score input and comment input
            int score = Integer.parseInt(spinnerScore.getValue().toString());
            String comment = txtaComment.getText().trim();
            
            // validate score
            if(score < 1 || score > 5){
                JOptionPane.showMessageDialog(null, "Please select a score between 1 and 5.");
            return;
            }
            
            // validate comment
            if(comment.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter your comment.");
                txtaComment.requestFocus();
                return;
            }
            
            // reload rating records before updating
            FileUtil.read("rating.txt", ratingList, Rating::fromLine);
            
            Rating latestRating = null;
            int ratingIndex = -1;
            
            for(int i = 0; i < ratingList.size(); i++){
                Rating r = ratingList.get(i);
                if(r.getRatingId().equals(selectedRating.getRatingId()) && r.getStudentId().equals(studentId) && r.getClassId().equals(classId)){
                    latestRating = r;
                    ratingIndex = i;
                    break;
                }
            }
            
            if(latestRating == null){
                JOptionPane.showMessageDialog(null, "Rating record not found. Please try again.");
                return;
            }
            
            // check rating status again
            if(latestRating.getStatus() != Rating.RatingStatus.Unrated){
                JOptionPane.showMessageDialog(null, "This class has already been rated.");
                return;
            }
            
            // confirm rating
            int confirm = JOptionPane.showConfirmDialog(null, "Confirm rate " + selectedInstructor.getName() + " with " + score + "?", "Confirm Rating", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION){
                return;
            }
            
            // update rating information
            latestRating.setScore(score);
            latestRating.setComment(comment);
            latestRating.setRatingDate(LocalDate.now());
            latestRating.setStatus(Rating.RatingStatus.Rated);
            
            // save updated rating records
            FileUtil.write("rating.txt", ratingList, Rating::toLine);
            
            JOptionPane.showMessageDialog(null, "Rating submitted successfully!");
            
            InstructorRating instructorRating = new InstructorRating();
            instructorRating.setVisible(true);
            this.dispose();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error submitting rating: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new RateInstructor("").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClassInfo;
    private javax.swing.JLabel lblInstructorInfo;
    private javax.swing.JSpinner spinnerScore;
    private javax.swing.JTextArea txtaComment;
    // End of variables declaration//GEN-END:variables
}
