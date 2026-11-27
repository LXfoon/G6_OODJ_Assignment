/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sdams.model.Attendance;
import sdams.model.ClassSession;
import sdams.model.Instructor;
import sdams.model.Rating;
import sdams.model.Staff;
import sdams.util.FileUtil;
import sdams.util.LoggedInChecker;

/**
 *
 * @author Mei Shi
 */
public class InstructorRating extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(InstructorRating.class.getName());

    @Override
    public void setVisible(boolean b){
        if(b && !LoggedInChecker.loginChecker(this)){
            return;
        }
        
        super.setVisible(b);
    }
    
    private ArrayList<Attendance> attendanceList = new ArrayList<>();
    private ArrayList<Rating> ratingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form InstructorRating
     */
    public InstructorRating() {
        initComponents();
        loadRating();
        
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterRating();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterRating();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterRating();
            }
        });
    }
    
    private void loadRating(){
        try{
            // clear existing table rows
            DefaultTableModel model = (DefaultTableModel) tblRate.getModel();
            model.setRowCount(0);
            
            FileUtil.read("attendance.txt", attendanceList, Attendance::fromLine);
            FileUtil.read("rating.txt", ratingList, Rating::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            
            // read user records and get staff information
            ArrayList<Staff> staffList = new ArrayList<>();
            FileUtil.read("user.txt", staffList, Staff::fromUserLine);
            
            // get instructor name only
            instructorList.clear();

            for(Staff s : staffList){
                if(s instanceof Instructor i){
                    instructorList.add(i);
                }
            }
            
            // Display current student's attendance and rating
            filterRating();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void filterRating(){
        // clear existing table rows
        DefaultTableModel model = (DefaultTableModel) tblRate.getModel();
        model.setRowCount(0);
        
        // get current student id
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
            return;
        }
        
        // get filter values
        String search = txtSearch.getText().trim().toLowerCase();
        String score = String.valueOf(cmbScore.getSelectedItem());
        String status = String.valueOf(cmbStatus.getSelectedItem());
        
        // Check each attendance record
        for(Attendance a : attendanceList){

            // Only display current student's attendance
            if(!a.getStudentId().equals(studentId)){
                continue;
            }

            // Only display Present or Late attendance
            if(a.getStatus() == Attendance.AttStatus.Absent){
                continue;
            }

            // Find class information
            ClassSession selectedClass = null;
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(a.getClassId())){
                    selectedClass = cs;
                    break;
                }
            }

            if(selectedClass == null){
                continue;
            }

            // Find instructor name
            String instructorName = "Unknown";
            for(Instructor i : instructorList){
                if(i.getId().equals(selectedClass.getInstructorId())){
                    instructorName = i.getName();
                    break;
                }
            }

            // Find rating information
            Rating selectedRating = null;
            for(Rating r : ratingList){
                if(r.getStudentId().equals(studentId) && r.getClassId().equals(a.getClassId())){
                    selectedRating = r;
                    break;
                }
            }

            // Set rating status and score
            String ratingStatus = "Unrated";
            String ratingScore = "-";

            if(selectedRating != null && selectedRating.getStatus() != null){
                ratingStatus = selectedRating.getStatus().toString();
                if(selectedRating.getStatus() == Rating.RatingStatus.Rated){
                    ratingScore = String.valueOf(selectedRating.getScore());
                }
            }

            // check search condition
            boolean matchSearch = search.isEmpty() || selectedClass.getClassId().toLowerCase().contains(search) || selectedClass.getClassName().toLowerCase().contains(search) 
                    || instructorName.toLowerCase().contains(search);
            boolean matchScore = score.equals("All Score") || ratingScore.equals(score);
            boolean matchStatus = status.equals("All Status") || ratingStatus.equals(status);
            
            if(matchSearch && matchScore && matchStatus){
                model.addRow(new Object[]{
                    selectedClass.getClassId(), selectedClass.getClassName(), instructorName, selectedClass.getDate(), a.getStatus(), ratingStatus, ratingScore
                });
            }
        }
        
        // update available class count
        lblAttended.setText("Attended Classes: " + model.getRowCount());
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
        lblAttended = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        cmbScore = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnRate = new javax.swing.JButton();
        btnMyRating = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRate = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Instructor Rating");

        lblAttended.setText("Attended Classes: xx");

        txtSearch.addActionListener(this::txtSearchActionPerformed);

        cmbScore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Score", "1", "2", "3", "4", "5" }));
        cmbScore.addActionListener(this::cmbScoreActionPerformed);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Status", "Rated", "Unrated" }));
        cmbStatus.addActionListener(this::cmbStatusActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnBack.setText("Back to Student Main Menu");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnRate.setText("Rate Selected Instructor");
        btnRate.addActionListener(this::btnRateActionPerformed);

        btnMyRating.setText("My Ratings");
        btnMyRating.addActionListener(this::btnMyRatingActionPerformed);

        tblRate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Instructor", "Date", "Attendance", "Rating Status", "Score"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblRate);
        if (tblRate.getColumnModel().getColumnCount() > 0) {
            tblRate.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblRate.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblRate.getColumnModel().getColumn(3).setPreferredWidth(80);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAttended)
                .addGap(347, 347, 347))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnReset)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnBack)
                                        .addGap(312, 312, 312)
                                        .addComponent(btnMyRating)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(btnRate)))))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAttended)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbScore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMyRating, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbScoreActionPerformed
        filterRating();
    }//GEN-LAST:event_cmbScoreActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        filterRating();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        filterRating();
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        cmbScore.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        
        filterRating();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        StudentHome studentHome = new StudentHome();
        studentHome.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnMyRatingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyRatingActionPerformed
        MyRatings myRatings = new MyRatings();
        myRatings.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMyRatingActionPerformed

    private void btnRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRateActionPerformed
        try{
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            int selectedRow = tblRate.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a class first.");
                return;
            }
            
            int modelRow = tblRate.convertRowIndexToModel(selectedRow);
            String classId = tblRate.getModel().getValueAt(modelRow, 0).toString();
            String status = tblRate.getModel().getValueAt(modelRow, 5).toString();
            
            if(!status.equalsIgnoreCase("Unrated")){
                JOptionPane.showMessageDialog(null, "You can only rate an Unrated class. Please select an Unrated class.");
                return;
            }
            
            RateInstructor rateInstructor = new RateInstructor(classId);
            if(rateInstructor.isRatingInfoLoaded()){
                rateInstructor.setVisible(true);
                this.dispose();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing rating: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRateActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new InstructorRating().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnMyRating;
    private javax.swing.JButton btnRate;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbScore;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAttended;
    private javax.swing.JTable tblRate;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
