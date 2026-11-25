/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
public class MyRatings extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MyRatings.class.getName());

    @Override
    public void setVisible(boolean b){
        if(b && !LoggedInChecker.loginChecker(this)){
            return;
        }
        
        super.setVisible(b);
    }
    
    private ArrayList<Rating> ratingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form MyRatings
     */
    public MyRatings() {
        initComponents();
        loadMyRating();
        
        txtSearchRatings.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterMyRatings();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterMyRatings();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterMyRatings();
            }
        });
    }
    
    private void loadMyRating(){
        try{
            // clear existing table rows
            DefaultTableModel model = (DefaultTableModel) tblRatings.getModel();
            model.setRowCount(0);
            
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
            
            filterMyRatings();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void filterMyRatings(){
        // clear existing table rows
        DefaultTableModel model = (DefaultTableModel) tblRatings.getModel();
        model.setRowCount(0);
        
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
            return;
        }
        
        // get filter values
        String search = txtSearchRatings.getText().trim().toLowerCase();
        String score = String.valueOf(cmbScore.getSelectedItem());
        
        // variable for calculete average score
        double totalScore = 0;
        int ratedCount = 0;
        
        // check each rating record
        for(Rating r : ratingList){
            // only display current student Rated rating record
            if(!r.getStudentId().equals(studentId)){
                continue;
            }
                
            if(r.getStatus() != Rating.RatingStatus.Rated){
                continue;
            }
                
            // find class information
            ClassSession selectedClass = null;
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(r.getClassId())){
                    selectedClass = cs;
                    break;
                }
            }
                
            if(selectedClass == null){
                continue;
            }
                
            // find instructor name
            String instructorName = "";
            for(Instructor i : instructorList){
                if(i.getId().equals(selectedClass.getInstructorId())){
                    instructorName = i.getName();
                    break;
                }
            }
                
            // check filter conditions
            boolean matchSearch = search.isEmpty() || r.getRatingId().toLowerCase().contains(search) || selectedClass.getClassName().toLowerCase().contains(search) 
                    || instructorName.toLowerCase().contains(search);
            boolean matchScore = score.equals("All Score") || String.valueOf(r.getScore()).equals(score);
            
            if(matchSearch && matchScore){
                model.addRow(new Object[]{
                    r.getRatingId(), selectedClass.getClassName(), instructorName, r.getScore(), r.getComment(), r.getRatingDate()
                });
            }
                
            // calculate average score of displayed records
            totalScore += r.getScore();
            ratedCount++;
        }
        
        // calculate average score
        double averageScore = 0;
        if(ratedCount > 0){
            averageScore = totalScore / ratedCount;
        }
            
        // show message
        lblRatings.setText("Total Ratings: " + model.getRowCount() + " | " + String.format("Average Score Given: %.2f", averageScore));        
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
        lblRatings = new javax.swing.JLabel();
        txtSearchRatings = new javax.swing.JTextField();
        cmbScore = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRatings = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("My Ratings");

        lblRatings.setText("Total Ratings: xx   Average Score Given: xx");

        txtSearchRatings.addActionListener(this::txtSearchRatingsActionPerformed);

        cmbScore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Score", "1", "2", "3", "4", "5" }));
        cmbScore.addActionListener(this::cmbScoreActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        btnBack.setText("Back to Instructor Rating");
        btnBack.addActionListener(this::btnBackActionPerformed);

        tblRatings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Rating ID", "Class", "Instructor", "Score", "Comment", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblRatings);
        if (tblRatings.getColumnModel().getColumnCount() > 0) {
            tblRatings.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblRatings.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblRatings.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblRatings.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblRatings.getColumnModel().getColumn(4).setPreferredWidth(200);
            tblRatings.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(lblRatings))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearchRatings, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRatings)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchRatings, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbScore, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchRatingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRatingsActionPerformed
        filterMyRatings();
    }//GEN-LAST:event_txtSearchRatingsActionPerformed

    private void cmbScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbScoreActionPerformed
        filterMyRatings();
    }//GEN-LAST:event_cmbScoreActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearchRatings.setText("");
        cmbScore.setSelectedIndex(0);
        
        filterMyRatings();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        InstructorRating instructorRating = new InstructorRating();
        instructorRating.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MyRatings().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbScore;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRatings;
    private javax.swing.JTable tblRatings;
    private javax.swing.JTextField txtSearchRatings;
    // End of variables declaration//GEN-END:variables
}
