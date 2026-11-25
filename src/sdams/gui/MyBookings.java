/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sdams.model.Booking;
import sdams.model.ClassSession;
import sdams.model.Instructor;
import sdams.model.Payment;
import sdams.model.Staff;
import sdams.util.FileUtil;
import sdams.util.LoggedInChecker;

/**
 *
 * @author Mei Shi
 */
public class MyBookings extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MyBookings.class.getName());

    @Override
    public void setVisible(boolean b){
        if(b && !LoggedInChecker.loginChecker(this)){
            return;
        }
        
        super.setVisible(b);
    }
    
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    private ArrayList<Payment> paymentList = new ArrayList<>();
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form MyBookings
     */
    public MyBookings() {
        initComponents();
        loadBooking();
        
        dateSearch.addDateChangeListener(e -> filterBooking());
        txtSearchBooking.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterBooking();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterBooking();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterBooking();
            }
        });
    }
    
    /*
    private String getCurrentStudentId(){
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
        }
        return studentId;
    }
    */
    
    private void loadBooking(){
        try{
            DefaultTableModel model = (DefaultTableModel) tblBooking.getModel();
            model.setRowCount(0);
            
            // get current student id
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
        
            // get information from booking records and other records
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
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
            
            // display current student's booking information only
            for(Booking b: bookingList){
                // check student id
                if(!b.getStudentId().equals(studentId)){
                    continue;
                }
                
                // find matching class
                ClassSession cs = null;
                for(ClassSession c : classList){
                    if(c.getClassId().equals(b.getClassId())){
                        cs = c;
                        break;
                    }
                }
                
                // skip if cannot found the class record
                if(cs == null){
                    continue;
                }
                
                // find instructor name
                String instructorName = "Unknown";
                for(Instructor i : instructorList){
                    if(i.getId().equals(cs.getInstructorId())){
                        instructorName = i.getName();
                        break;
                    }
                }
                
                // find payment status for the booking
                String paymentStatus = "Unpaid";
                for(Payment p : paymentList){
                    if(p.getBookingId().equals(b.getBookingId())){
                        paymentStatus = p.getStatus().toString();
                        break;
                    }
                }
                
                model.addRow(new Object[]{
                    b.getBookingId(), cs.getClassName(), instructorName, cs.getDate(), cs.getStartTime(), cs.getEndTime(), b.getStatus(), paymentStatus
                });
            }
            
            // show message
            lblTotal.setText("Total Bookings: " + model.getRowCount());
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void filterBooking(){
        // clear existing table rows
        DefaultTableModel model = (DefaultTableModel) tblBooking.getModel();
        model.setRowCount(0);
        
        // get current student id
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
            return;
        }
        
        // get filter values
        String search = txtSearchBooking.getText().trim().toLowerCase();
        String status = String.valueOf(cmbStatus.getSelectedItem());
        LocalDate date = dateSearch.getDate();
        
        // check each booking
        for(Booking b: bookingList){
            // display current student's bookings only
            if(!b.getStudentId().equals(studentId)){
                continue;
            }
            
            ClassSession cs = null;
            for(ClassSession c : classList){
                if(c.getClassId().equals(b.getClassId())){
                    cs = c;
                    break;
                }
            }
            if(cs == null){
                continue;
            }
                
            String instructorName = "";
            for(Instructor i : instructorList){
                if(i.getId().equals(cs.getInstructorId())){
                    instructorName = i.getName();
                    break;
                }
            }
               
            String paymentStatus = "Unpaid";
            for(Payment p : paymentList){
                if(p.getBookingId().equals(b.getBookingId())){
                    paymentStatus = p.getStatus().toString();
                    break;
                }
            }
            
            boolean matchSearch = search.isEmpty() || b.getBookingId().toLowerCase().contains(search) || cs.getClassName().toLowerCase().contains(search) || instructorName.toLowerCase().contains(search);
            boolean matchStatus = status.equals("All Status") || b.getStatus().toString().equalsIgnoreCase(status);
            boolean matchDate = date == null || cs.getDate().equals(date);
            
            if(matchSearch && matchStatus && matchDate){
                model.addRow(new Object[]{
                    b.getBookingId(), cs.getClassName(), instructorName, cs.getDate(), cs.getStartTime(), cs.getEndTime(), b.getStatus(), paymentStatus
                });
            }
        }
        
        // update booking records count
        lblTotal.setText("Total Bookings: " + model.getRowCount());
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
        txtSearchBooking = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBooking = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        btnReschedule = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        dateSearch = new com.github.lgooddatepicker.components.DatePicker();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("My Bookings");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        lblTotal.setText("Total Bookings: xx");

        txtSearchBooking.addActionListener(this::txtSearchBookingActionPerformed);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Status", "Booked", "Cancelled", "Completed" }));
        cmbStatus.addActionListener(this::cmbStatusActionPerformed);

        tblBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Booking ID", "Class", "Instructor", "Date", "Start Time", "End Time", "Status", "Payment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblBooking.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(tblBooking);
        if (tblBooking.getColumnModel().getColumnCount() > 0) {
            tblBooking.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblBooking.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblBooking.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblBooking.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblBooking.getColumnModel().getColumn(4).setPreferredWidth(65);
            tblBooking.getColumnModel().getColumn(5).setPreferredWidth(65);
            tblBooking.getColumnModel().getColumn(6).setPreferredWidth(70);
            tblBooking.getColumnModel().getColumn(7).setPreferredWidth(60);
        }

        btnBack.setText("Back to Browse Classes");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnPay.setText("Pay Now");
        btnPay.addActionListener(this::btnPayActionPerformed);

        btnReschedule.setText("Reschedule");
        btnReschedule.addActionListener(this::btnRescheduleActionPerformed);

        btnCancel.setText("Cancel Booking");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(352, 352, 352))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnBack)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnPay)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnReschedule)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCancel))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReschedule, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchBookingActionPerformed
        filterBooking();
    }//GEN-LAST:event_txtSearchBookingActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        filterBooking();
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearchBooking.setText("");
        cmbStatus.setSelectedIndex(0);
        dateSearch.clear();
        
        filterBooking();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        BrowseClasses browseClasses = new BrowseClasses();
        browseClasses.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        try{
            // must select a row
            int selectedRow = tblBooking.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a booking first.");
                return;
            }

            // get selected Booking ID
            int modelRow = tblBooking.convertRowIndexToModel(selectedRow);
            String bookingId = tblBooking.getModel().getValueAt(modelRow, 0).toString();
            
            // get current student id
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }

            // reload latest records
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);

            // find booking record
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId)){
                    selectedBooking = b;
                    break;
                }
            }

            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking records not found.");
                return;
            }

            // logic:
            // Booked + Unpaid = Must make payment then only can cancel
            // Booked -> Cancelled + Paid = Cancel but no refund
            // Completed + Paid = Cannot cancel
            // Instructor cancel class + Paid = Refund

            // check booking ownership
            if(!selectedBooking.getStudentId().equals(studentId)){
                JOptionPane.showMessageDialog(null, "You are not allowed to cancel another student's booking!");
            }
            
            // check booking status
            if(selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "Only Booked class can be cancelled. Please choose Booked class.");
                return;
            }

            // check payment status
            Payment selectedPayment = null;
            for(Payment p : paymentList){
                if(p.getBookingId().equals(bookingId) && p.getStatus() == Payment.PaymentStatus.Paid){
                    selectedPayment = p;
                    break;
                }
            }

            if(selectedPayment == null || selectedPayment.getStatus() != Payment.PaymentStatus.Paid){
                JOptionPane.showMessageDialog(null, "Please make payment before cancelling this booking!");
                return;
            }

            // confirm cancellation
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this booking?\n" + "Please note that payment is non-refundable!", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION){
                return;
            }

            // find class record
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

            // check class status
            if(selectedClass.getStatus() == ClassSession.ClassStatus.Completed || selectedClass.getStatus() == ClassSession.ClassStatus.Cancelled){
                JOptionPane.showMessageDialog(null, "This booking cannot be cancel because the class is already completed or cancelled.");
                return;
            }
            
            // validate available slots
            if(selectedClass.getAvailableSlots() < 0 || selectedClass.getAvailableSlots() > selectedClass.getMaxSlots()){
                JOptionPane.showMessageDialog(null, "Invalid class slot information. Please contact the Administrator.");
                return;
            }

            // updated booking status
            selectedBooking.setStatus(Booking.BookingStatus.Cancelled);

            // add available slots after cancel
            selectedClass.setAvailableSlots(selectedClass.getAvailableSlots() + 1);

            if(selectedClass.getStatus() == ClassSession.ClassStatus.Full){
                selectedClass.setStatus(ClassSession.ClassStatus.Available);
            }

            // save update
            FileUtil.write("booking.txt", bookingList, Booking::toLine);
            FileUtil.write("class.txt", classList, ClassSession::toLine);

            // refresh table
            loadBooking();
            JOptionPane.showMessageDialog(null, "Booking successfully cancelled. No refund will be provided!");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error cancelling booking: " + e.getMessage());
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        try{
            int selectedRow = tblBooking.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a booking first.");
                return;
            }
            
            int modelRow = tblBooking.convertRowIndexToModel(selectedRow);
            String bookingId = tblBooking.getModel().getValueAt(modelRow, 0).toString();
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("payment.txt", paymentList, Payment::fromLine);
            
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId)){
                    selectedBooking = b;
                    break;
                }
            }

            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking records not found.");
                return;
            }
            
            if(!selectedBooking.getStudentId().equals(studentId)){
                JOptionPane.showMessageDialog(null, "You are not allowed to make payment another for student's booking!");
            }
            
            // check booking status
            if(selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "Only Booked bookings can make payment. Please choose Booked bookings.");
                return;
            }
            
            // check payment status
            Payment selectedPayment = null;
            for(Payment p : paymentList){
                if(p.getBookingId().equals(bookingId)){
                    selectedPayment = p;
                    break;
                }
            }
            
            if(selectedPayment == null){
                JOptionPane.showMessageDialog(null, "Payment records not found.");
                return;
            }
            
            if(selectedPayment.getStatus() != Payment.PaymentStatus.Unpaid){
                JOptionPane.showMessageDialog(null, "Only Unpaid bookings can make payment. Please choose Unpaid bookings.");
                return;
            }
            
            // open MakePayment form
            MakePayment makePayment = new MakePayment(bookingId);
            if(makePayment.isPaymentInfoLoaded()){
                makePayment.setVisible(true);
                this.dispose();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing to make payment: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnRescheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRescheduleActionPerformed
        // check selected row
        try{
            int selectedRow = tblBooking.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a booking first.");
                return;
            }
            
            int modelRow = tblBooking.convertRowIndexToModel(selectedRow);
            String bookingId = tblBooking.getModel().getValueAt(modelRow, 0).toString();
            
            String studentId = LoggedIn.getId();
            if(studentId == null || studentId.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
                return;
            }
            
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId)){
                    selectedBooking = b;
                    break;
                }
            }

            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking records not found.");
                return;
            }
            
            if(!selectedBooking.getStudentId().equals(studentId)){
                JOptionPane.showMessageDialog(null, "You are not allowed to reschedule another for student's booking!");
            }
            
            // check booking status
            if(selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "Only Booked bookings can reschedule. Please choose Booked bookings.");
                return;
            }
            
            // open Reshedule form
            Reschedule reschedule = new Reschedule(bookingId);
            if(reschedule.isRescheduleLoaded()){
                reschedule.setVisible(true);
                this.dispose();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing reschedule: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRescheduleActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MyBookings().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReschedule;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.github.lgooddatepicker.components.DatePicker dateSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblBooking;
    private javax.swing.JTextField txtSearchBooking;
    // End of variables declaration//GEN-END:variables
}
