/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sdams.model.Payment;
import sdams.util.FileUtil;
import sdams.util.LoggedInChecker;

/**
 *
 * @author Mei Shi
 */
public class ManagePayment extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagePayment.class.getName());
    
    @Override
    public void setVisible(boolean b){
        if(b && !LoggedInChecker.loginChecker(this)){
            return;
        }
        
        super.setVisible(b);
    }
    
    private ArrayList<Payment> paymentList = new ArrayList<>();
    private DefaultTableModel tableModel;

    /**
     * Creates new form ManagePayment
     */
    public ManagePayment() {
        initComponents();
        loadPayment();
        
        datePayment.addDateChangeListener(e -> filterPayment());
        txtSearchPayment.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterPayment();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterPayment();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterPayment();
            }
        });
    }
    
    public void loadPayment(){
        try{
            // clear existing table rows
            DefaultTableModel model = (DefaultTableModel) tblPayment.getModel();
            model.setRowCount(0);
            
            // get current student id
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            // read class information
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            // display payment records
            for(Payment p : paymentList){
                if(!p.getStudentId().equals(studentId)){
                    continue;
                }
                
                model.addRow(new Object[]{
                    p.getPaymentId(), p.getBookingId(), p.getAmount(), p.getPaymentDate(), p.getPaymentMethod(), p.getStatus()
                });
            }
            
            // show message
            lblTotal.setText("Total Payment Records: " + model.getRowCount());
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void filterPayment(){
        // clear existing table rows
        DefaultTableModel model = (DefaultTableModel) tblPayment.getModel();
        model.setRowCount(0);
        
        // get current student id
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
            return;
        }
        
        // get filter values
        String search = txtSearchPayment.getText().trim().toLowerCase();
        String status = String.valueOf(cmbStatus.getSelectedItem());
        LocalDate date = datePayment.getDate();
        
        // check each class
        for(Payment p : paymentList){
            // only current student's payment
            if(!p.getStudentId().equals(studentId)){
                continue;
            }
            
            String paymentId = p.getPaymentId() == null ? "" : p.getPaymentId().toLowerCase();
            String bookingId = p.getBookingId() == null ? "" : p.getBookingId().toLowerCase();
            String paymentMethod = p.getPaymentMethod() == null ? "" : p.getPaymentMethod().toLowerCase();
            
            boolean matchSearch = search.isEmpty() || paymentId.contains(search) || bookingId.contains(search) || paymentMethod.contains(search);
            boolean matchStatus = status.equals("All Status") || (p.getStatus() != null && p.getStatus().toString().equalsIgnoreCase(status));
            boolean matchDate = date == null || p.getPaymentDate().equals(date);
            
            if(matchSearch && matchStatus && matchDate){
                model.addRow(new Object[]{
                    p.getPaymentId(), p.getBookingId(), p.getAmount(), p.getPaymentDate(), p.getPaymentMethod(), p.getStatus()
                });
            }
        }
        
        lblTotal.setText("Total Payment Records: " + model.getRowCount());
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
        lblTotal = new javax.swing.JLabel();
        txtSearchPayment = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        datePayment = new com.github.lgooddatepicker.components.DatePicker();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPayment = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnMakePayment = new javax.swing.JButton();
        btnDetails = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Payment Records");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        lblTotal.setText("Total Payment Records: xx");

        txtSearchPayment.addActionListener(this::txtSearchPaymentActionPerformed);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Status", "Paid", "Refunded", "Unpaid" }));
        cmbStatus.addActionListener(this::cmbStatusActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        tblPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Payment ID", "Booking ID", "Amount (RM)", "Date", "Method", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPayment);

        btnBack.setText("Back to Student Main Menu");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnMakePayment.setText("Make Payment");
        btnMakePayment.addActionListener(this::btnMakePaymentActionPerformed);

        btnDetails.setText("View Details");
        btnDetails.addActionListener(this::btnDetailsActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(331, 331, 331))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearchPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(datePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnMakePayment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetails)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMakePayment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPaymentActionPerformed
        filterPayment();
    }//GEN-LAST:event_txtSearchPaymentActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        filterPayment();
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearchPayment.setText("");
        cmbStatus.setSelectedIndex(0);
        datePayment.clear();
        
        filterPayment();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        StudentHome studentHome = new StudentHome();
        studentHome.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnMakePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakePaymentActionPerformed
        try{
            int selectedRow = tblPayment.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a payment first.");
                return;
            }
            
            int modelRow = tblPayment.convertRowIndexToModel(selectedRow);
            String paymentId = tblPayment.getModel().getValueAt(modelRow, 0).toString();
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            Payment selectedPayment = null;
            for(Payment p : paymentList){
                if(p.getPaymentId().equals(paymentId) && studentId.equals(p.getStudentId())){
                    selectedPayment = p;
                    break;
                }
            }
            
            // check payment record
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment record not found.");
                return;
            }
            
            // only Unpaid can make payment
            if(selectedPayment.getStatus() != Payment.PaymentStatus.Unpaid){
                JOptionPane.showMessageDialog(null, "Only Unpaid payment can be made. Please select an Unpaid payment.");
                return;
            }
            
            MakePayment makePayment = new MakePayment(selectedPayment.getBookingId(), true);
            if(makePayment.isPaymentInfoLoaded()){
                makePayment.setVisible(true);
                this.dispose();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing to make payment: " + e.getMessage());
        }
    }//GEN-LAST:event_btnMakePaymentActionPerformed

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed
        try{
            int selectedRow = tblPayment.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a payment first.");
                return;
            }
            
            int modelRow = tblPayment.convertRowIndexToModel(selectedRow);
            String paymentId = tblPayment.getModel().getValueAt(modelRow, 0).toString();
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            // find selected payment
            Payment selectedPayment = null;
            for(Payment p : paymentList){
                if(p.getPaymentId().equals(paymentId) && studentId.equals(p.getStudentId())){
                    selectedPayment = p;
                    break;
                }
            }
            
            // check payment record
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment record not found.");
                loadPayment();
                return;
            }
            
            PaymentDetails paymentDetails = new PaymentDetails(paymentId);
            if(paymentDetails.isDetailsLoaded()){
                paymentDetails.setVisible(true);
                this.dispose();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing payment details: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDetailsActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ManagePayment().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDetails;
    private javax.swing.JButton btnMakePayment;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.github.lgooddatepicker.components.DatePicker datePayment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblPayment;
    private javax.swing.JTextField txtSearchPayment;
    // End of variables declaration//GEN-END:variables
}
