/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import sdams.model.Booking;
import sdams.model.ClassSession;
import sdams.model.Instructor;
import sdams.model.Payment;
import sdams.model.Staff;
import sdams.util.FileUtil;

/**
 *
 * @author Mei Shi
 */
public class PaymentDetails extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PaymentDetails.class.getName());

    private String paymentId;
    private boolean detailsLoaded = false;
    
    private Payment selectedPayment;
    private Booking selectedBooking;
    private ClassSession selectedClass;
    
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Payment> paymentList = new ArrayList<>();
    private ArrayList<Staff> staffList = new ArrayList<>();
    
    /**
     * Creates new form PaymentDetails
     */
    public PaymentDetails(String paymentId) {
        initComponents();
        this.paymentId = paymentId;
        
        txtaDetails.setEditable(false);
        txtaDetails.setLineWrap(true);
        txtaDetails.setWrapStyleWord(true);
        
        loadDetails();
    }
    
    private void loadDetails(){
        try{
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            FileUtil.read("user.txt", staffList, Staff::fromUserLine);
            
            // find payment
            for(Payment p : paymentList){
                if(p.getPaymentId().equals(paymentId)){
                    selectedPayment = p;
                    break;
                }
            }
            
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment record not found.");
                return;
            }
            
            // find booking
            for(Booking b : bookingList){
                if(b.getBookingId().equals(selectedPayment.getBookingId())){
                    selectedBooking = b;
                    break;
                }
            }
            
            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking record not found.");
                return;
            }
            
            // find class
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(selectedBooking.getClassId())){
                    selectedClass = cs;
                    break;
                }
            }
            
            if(selectedClass == null){
                JOptionPane.showMessageDialog(null, "Class record not found.");
                return;
            }
            
            // display information to the text area
            showPaymentInfo();
            detailsLoaded = true;
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error loading payment details: " + e.getMessage());
        }
    }
    
    private void showPaymentInfo(){
        String details = "Payment ID: " + selectedPayment.getPaymentId() + "\nBooking ID: " + selectedPayment.getBookingId() + "\nAmount: RM" + String.format("%.2f", selectedPayment.getAmount())
                + "\nPayment Date: " + (selectedPayment.getPaymentDate() == null ? "Unpaid" : selectedPayment.getPaymentDate()) 
                + "\nPayment Method: " + (selectedPayment.getPaymentMethod() == null || selectedPayment.getPaymentMethod().isEmpty() ? "Unpaid" : selectedPayment.getPaymentMethod()) 
                + "\nPayment Status: " + selectedPayment.getStatus() + "\nBooking Status: " + selectedBooking.getStatus();
        
        txtaDetails.setText(details);
    }
    
    private String findInstructorName(String instructorId){
        for(Staff s : staffList){
            if(s instanceof Instructor && s.getId().equals(instructorId)){
                return s.getName();
            }
        }
        
        return "Unknown Instructor";
    }
    
    private void showClassInfo(){
        String instructorName = findInstructorName(selectedClass.getInstructorId());
        String details = "Class ID: " + selectedClass.getClassId() + "\nClass Name: " + selectedClass.getClassName() + "\nDiscipline: " + selectedClass.getDiscipline() + "\nInstructor: " + instructorName 
                + "\nClass Date: " + selectedClass.getDate() + "\nClass Start Time: " + selectedClass.getStartTime() + "\nClass End Time: " + selectedClass.getEndTime()
                + "\nFee: RM" + String.format("%.2f", selectedClass.getFee());
        
        txtaDetails.setText(details);
    }
    
    public boolean isDetailsLoaded(){
        return detailsLoaded;
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
        btnPaymentInfo = new javax.swing.JButton();
        btnClassInfo = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaDetails = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Payment Details");

        btnPaymentInfo.setText("Payment Information");
        btnPaymentInfo.addActionListener(this::btnPaymentInfoActionPerformed);

        btnClassInfo.setText("Class Information");
        btnClassInfo.addActionListener(this::btnClassInfoActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        txtaDetails.setColumns(20);
        txtaDetails.setRows(5);
        jScrollPane1.setViewportView(txtaDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(159, 159, 159))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPaymentInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClassInfo))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPaymentInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClassInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        ManagePayment managePayment = new ManagePayment();
        managePayment.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnPaymentInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentInfoActionPerformed
        showPaymentInfo();
    }//GEN-LAST:event_btnPaymentInfoActionPerformed

    private void btnClassInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClassInfoActionPerformed
        showClassInfo();
    }//GEN-LAST:event_btnClassInfoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PaymentDetails("").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClassInfo;
    private javax.swing.JButton btnPaymentInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtaDetails;
    // End of variables declaration//GEN-END:variables
}
