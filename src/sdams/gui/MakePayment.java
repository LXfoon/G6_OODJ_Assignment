/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import sdams.model.Booking;
import sdams.model.ClassSession;
import sdams.model.Payment;
import sdams.util.FileUtil;
import sdams.util.Generator;

/**
 *
 * @author Mei Shi
 */
public class MakePayment extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MakePayment.class.getName());
    
    private String bookingId;
    private boolean paymentInfoLoaded = false;
    private boolean fromManagePayment = false;
    
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Payment> paymentList = new ArrayList<>();

    /**
     * Creates new form MakePayment
     */
    public MakePayment(String bookingId) {
        initComponents();
        this.bookingId = bookingId;
        this.fromManagePayment = false;
        loadPayment();
    }
    
    public MakePayment(String bookingId, boolean fromManagePayment) {
        initComponents();
        this.bookingId = bookingId;
        this.fromManagePayment = fromManagePayment;
        loadPayment();
    }
    
    private void loadPayment(){
        try{
            paymentInfoLoaded = false;
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            // find selected booking id
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId) && b.getStudentId().equals(studentId)){
                    selectedBooking = b;
                    break;
                }
            }
            
            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking record not found.");
                return;
            }
            
            // find corresponding class
            ClassSession selectedClass = null;
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
            
            // find corresponding payment
            Payment selectedPayment = null;
            for(Payment p : paymentList){
                if(p.getBookingId().equals(bookingId) && p.getStudentId().equals(studentId)){
                    selectedPayment = p;
                    break;
                }
            }
            
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment record not found. Please contact the Administrator.");
                return;
            }
            
            if(selectedPayment.getStatus() == Payment.PaymentStatus.Paid){
                JOptionPane.showMessageDialog(null, "This payment is already paid!");
                return;
            }
            
            if(selectedPayment.getStatus() == Payment.PaymentStatus.Refunded){
                JOptionPane.showMessageDialog(null, "This payment has been refunded. Please contact the Administrator.");
                return;
            }
            
            if(selectedPayment.getStatus() != Payment.PaymentStatus.Unpaid){
                JOptionPane.showMessageDialog(null, "Invalid payment status. Please contact the Administrator.");
                return;
            }
            
            // display booking information
            lblUnpaidInfo.setText(selectedBooking.getBookingId() + ": " + selectedClass.getClassName());
            
            // display amount
            lblAmountInfo.setText(String.format("RM %.2f", selectedPayment.getAmount()));
            
            // all information loaded successfully
            paymentInfoLoaded = true;
        } catch(Exception e){
            paymentInfoLoaded = false;
            JOptionPane.showMessageDialog(null, "Error loading payment details: " + e.getMessage());
        }
    }
    
    public boolean isPaymentInfoLoaded(){
        return paymentInfoLoaded;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngMethod = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        radBank = new javax.swing.JRadioButton();
        radEWallet = new javax.swing.JRadioButton();
        btnBack = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        lblUnpaidInfo = new javax.swing.JLabel();
        lblAmountInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Make Payment");

        jLabel3.setText("Unpaid Booking");

        jLabel4.setText("Amount (RM)");

        jLabel5.setText("Payment Method");

        btngMethod.add(radBank);
        radBank.setText("Bank");

        btngMethod.add(radEWallet);
        radEWallet.setText("E-Wallet");

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnPay.setText("Pay");
        btnPay.addActionListener(this::btnPayActionPerformed);

        lblUnpaidInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblAmountInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBack)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPay))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(radBank, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(radEWallet, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblUnpaidInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblAmountInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel1)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblUnpaidInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblAmountInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radBank)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radEWallet)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if(fromManagePayment){
            ManagePayment managePayment = new ManagePayment();
            managePayment.setVisible(true);
        } else {
            MyBookings myBookings = new MyBookings();
            myBookings.setVisible(true); 
        }
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        btngMethod.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        try{
            // check payment method
            String paymentMethod = "";
            if(radBank.isSelected()){
                paymentMethod = "Bank";
            } else if(radEWallet.isSelected()){
                paymentMethod = "E-Wallet";
            } else {
                JOptionPane.showMessageDialog(null, "Please select a payment method.");
                return;
            }
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            // reload latest payment reocrds
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            // find booking
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId) && b.getStudentId().equals(studentId)){
                    selectedBooking = b;
                    break;
                }
            }
            
            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking record not found.");
                return;
            }
            
            // check booking status
            if(selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "Only Booked bookings can make payment. Please choose Booked bookings.");
                return;
            }
            
            // find corresponding class
            ClassSession selectedClass = null;
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
            
            // check whether payment already exists in the records
            Payment selectedPayment = null;
            int paymentIndex = -1;
            
            for(int i = 0; i < paymentList.size(); i++){
                Payment p = paymentList.get(i);
                if(p.getBookingId().equals(bookingId) && p.getStudentId().equals(studentId)){
                    selectedPayment = p;
                    paymentIndex = i;
                    break;
                }
            }
            
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment record not found. Please contact the Administrator.");
                return;
            }
            
            if(selectedPayment.getStatus() == Payment.PaymentStatus.Paid){
                JOptionPane.showMessageDialog(null, "This payment is already paid!");
                return;
            }
                
            if(selectedPayment.getStatus() == Payment.PaymentStatus.Refunded){
                JOptionPane.showMessageDialog(null, "This payment has been refunded. Please contact the Administratos.");
                return;
            }
            
            if(selectedPayment.getStatus() != Payment.PaymentStatus.Unpaid){
                JOptionPane.showMessageDialog(null, "Invalid payment status. Please contact the Administrator.");
                return;
            }
            
            // confirm payment
            int confirm = JOptionPane.showConfirmDialog(null, "Confirm your payment of " + String.format("RM %.2f", selectedPayment.getAmount()) + " using " + paymentMethod + "?", "Confirm Payment", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION){
                return;
            }
            
            // create new payment record
            Payment updatedPayment = new Payment(selectedPayment.getPaymentId(), selectedPayment.getBookingId(), selectedPayment.getStudentId(), paymentMethod, selectedPayment.getAmount(), java.time.LocalDate.now(), Payment.PaymentStatus.Paid);
            paymentList.set(paymentIndex, updatedPayment);
            
            // save updated payment record
            FileUtil.write("payment.txt", paymentList, Payment::toLine);
            
            JOptionPane.showMessageDialog(null, "Payment successful! Thank you!");
            
            // close this form and return to previous form
            if(fromManagePayment){
                ManagePayment managePayment = new ManagePayment();
                managePayment.setVisible(true);
            } else {
                MyBookings myBookings = new MyBookings();
                myBookings.setVisible(true);
            }
            this.dispose();
            
            // prevent another payment on the same page
            paymentInfoLoaded = false;
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing payment: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPayActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MakePayment("").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnPay;
    private javax.swing.ButtonGroup btngMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblAmountInfo;
    private javax.swing.JLabel lblUnpaidInfo;
    private javax.swing.JRadioButton radBank;
    private javax.swing.JRadioButton radEWallet;
    // End of variables declaration//GEN-END:variables
}
