/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sdams.model.Booking;
import sdams.model.ClassSession;
import sdams.model.Instructor;
import sdams.model.Staff;
import sdams.util.FileUtil;

/**
 *
 * @author Mei Shi
 */
public class Reschedule extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Reschedule.class.getName());

    private String bookingId;
    private boolean rescheduleLoaded = false;
    
    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    private ArrayList<Booking> bookingList = new ArrayList<>();
    private ArrayList<Staff> staffList = new ArrayList<>();
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form Reschedule
     */
    public Reschedule(){
        initComponents();
    }
    
    public Reschedule(String bookingId) {
        initComponents();
        this.bookingId = bookingId;
        loadData();
    }
    
    private void loadData(){
        try{
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            FileUtil.read("user.txt", staffList, Staff::fromUserLine);
            
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId) && b.getStudentId().equals(LoggedIn.getId())){
                    selectedBooking = b;
                    break;
                }
            }
            
            // check wheter booking record exist
            if(selectedBooking == null){
                JOptionPane.showMessageDialog(null, "Booking record not found.");
                btnConfirm.setEnabled(false);
                return;
            }
            
            // Only Booked booking can reschedule
            if(selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "Only Booked bookings can be reschedulled.");
                btnConfirm.setEnabled(false);
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
                btnConfirm.setEnabled(false);
                return;
            }
            
            //rescheduleLoaded = true;
            
            // display selected booking information
            lblBookingClassInfo.setText(selectedBooking.getBookingId() + "|" + selectedClass.getClassName());
            lblDateTimeInfo.setText(selectedClass.getDate() + "|" + selectedClass.getStartTime() + "-" + selectedClass.getEndTime());
            
            // display other available class in table
            loadAvailableClasses();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error loading booking details: " + e.getMessage());
        }
    }
    
    private String findInstructorName(String instructorId){
        for(Staff s : staffList){
            if(s instanceof Instructor && s.getId().equals(instructorId)){
                return s.getName();
            }
        }
        
        return "Unknown";
    }
    
    private void loadAvailableClasses(){
        try{
            // clear existing table rows
            DefaultTableModel model = (DefaultTableModel) tblClass.getModel();
            model.setRowCount(0);
            
            ClassSession selectedClass = null;
            
            for(ClassSession cs : classList){
                // same class name
                boolean sameName = cs.getClassName().equals(selectedClass.getClassName());
                
                // same discipline
                boolean sameDiscipline = cs.getDiscipline().equals(selectedClass.getDiscipline());
                
                // same instructor
                boolean sameInstructor = cs.getInstructorId().equals(selectedClass.getInstructorId());
                
                // same fee
                boolean sameFee = Double.compare(cs.getFee(), selectedClass.getFee()) == 0;
                
                // different date and time
                boolean diffDateTime = !cs.getDate().equals(selectedClass.getDate()) || !cs.getStartTime().equals(selectedClass.getStartTime()) || !cs.getEndTime().equals(selectedClass.getEndTime());
                
                // has available slots
                boolean hasSlots = cs.getAvailableSlots() > 0;
                boolean available = cs.getStatus() == ClassSession.ClassStatus.Available;
                
                // check all conditions
                if(sameName && sameDiscipline && sameInstructor && sameFee && diffDateTime && hasSlots && available){
                    String instructorName = findInstructorName(cs.getInstructorId());
                    
                    model.addRow(new Object[]{
                        cs.getClassId(), cs.getClassName(), cs.getDiscipline(), instructorName, cs.getDate(), cs.getStartTime(), cs.getEndTime(), cs.getFee(), cs.getAvailableSlots()
                    });
                }
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public boolean isRescheduleLoaded(){
        return rescheduleLoaded;
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnConfirm = new javax.swing.JButton();
        lblBookingClassInfo = new javax.swing.JLabel();
        lblDateTimeInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Reschedule Class");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Current Booking");

        jLabel3.setText("Booking / Class");

        jLabel4.setText("Date / Time");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Please choose a new class  *same discipline, free slots, no clash");

        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Class Name", "Deciplines", "Instructor", "Date", "Start Time", "End Time", "Fee (RM)", "Available Slot"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClass);
        if (tblClass.getColumnModel().getColumnCount() > 0) {
            tblClass.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblClass.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblClass.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblClass.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblClass.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblClass.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblClass.getColumnModel().getColumn(6).setPreferredWidth(75);
            tblClass.getColumnModel().getColumn(7).setPreferredWidth(70);
            tblClass.getColumnModel().getColumn(8).setPreferredWidth(90);
        }

        btnBack.setText("Back to My Booings");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnConfirm.setText("Confirm Reschedule");
        btnConfirm.addActionListener(this::btnConfirmActionPerformed);

        lblBookingClassInfo.setBackground(java.awt.Color.white);
        lblBookingClassInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblDateTimeInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(305, 305, 305)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(37, 37, 37))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBookingClassInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDateTimeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnConfirm)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblBookingClassInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDateTimeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        MyBookings myBookings = new MyBookings();
        myBookings.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        try{
            // must select a row
            int selectedRow = tblClass.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Please select a class first.");
                return;
            }
            
            // get selected Booking ID
            int modelRow = tblClass.convertRowIndexToModel(selectedRow);
            String classId = tblClass.getValueAt(modelRow, 0).toString();

            // reload latest records
            FileUtil.read("booking.txt", bookingList, Booking::fromLine);
            FileUtil.read("class.txt", classList, ClassSession::fromLine);
            
            // find booking again
            Booking selectedBooking = null;
            for(Booking b : bookingList){
                if(b.getBookingId().equals(bookingId)){
                    selectedBooking = b;
                    break;
                }
            }

            // check booking status again
            if(selectedBooking == null || selectedBooking.getStatus() != Booking.BookingStatus.Booked){
                JOptionPane.showMessageDialog(null, "This booking cannot be rescheduled.");
                return;
            }
            
            // find selectedClass again
            ClassSession selectedClass = null;
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(selectedBooking.getClassId())){
                    selectedClass = cs;
                    break;
                }
            }
            
            if(selectedClass == null){
                JOptionPane.showMessageDialog(null, "Class record not found.");
                btnConfirm.setEnabled(false);
                return;
            }
            
            // find new class
            ClassSession newClass = null;
            for(ClassSession cs : classList){
                if(cs.getClassId().equals(newClass)){
                    newClass = cs;
                    break;
                }
            }
            
            if(newClass == null){
                JOptionPane.showMessageDialog(null, "Selected class not found.");
                btnConfirm.setEnabled(false);
                return;
            }
            
            // validate new class again
            boolean sameName = newClass.getClassName().equals(selectedClass.getClassName());
            boolean sameDiscipline = newClass.getDiscipline().equals(selectedClass.getDiscipline());
            boolean sameInstructor = newClass.getInstructorId().equals(selectedClass.getInstructorId());
            boolean sameFee = Double.compare(newClass.getFee(), selectedClass.getFee()) == 0;
            boolean diffDateTime = !newClass.getDate().equals(selectedClass.getDate()) || !newClass.getStartTime().equals(selectedClass.getStartTime()) || !newClass.getEndTime().equals(selectedClass.getEndTime());
            boolean hasSlots = newClass.getAvailableSlots() > 0;
            boolean available = newClass.getStatus() == ClassSession.ClassStatus.Available;
                
            if(!sameName || !sameDiscipline || !sameInstructor || !sameFee || !diffDateTime || !hasSlots || !available){
                JOptionPane.showMessageDialog(null, "This class is no longer available. Please select another class.");
                loadAvailableClasses();
                return;
            }
            
            // confirm reschedule
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reschedule this booking?", "Confirm Reschedule", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION){
                return;
            }
            
            // add available slots
            selectedClass.setAvailableSlots(selectedClass.getAvailableSlots() + 1);
            
            if(selectedClass.getStatus() == ClassSession.ClassStatus.Full){
                selectedClass.setStatus(ClassSession.ClassStatus.Available);
            }
            
            // minus available slots after reschedule
            newClass.setAvailableSlots(newClass.getAvailableSlots() - 1);
            
            if(newClass.getAvailableSlots() == 0){
                newClass.setStatus(ClassSession.ClassStatus.Full);
            }
            
            // update booking class id and booking status
            selectedBooking.setClassId(newClass.getClassId());
            selectedBooking.setStatus(Booking.BookingStatus.Booked);
            
            // save
            FileUtil.write("booking.txt", bookingList, Booking::toLine);
            FileUtil.write("class.txt", classList, ClassSession::toLine);
            
            JOptionPane.showMessageDialog(null, "Booking has been successfully rescheduled.");
            
            // return to MyBookings.java
            MyBookings myBookings = new MyBookings();
            myBookings.setVisible(true);
            this.dispose();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error processing reschedule: " + e.getMessage());
        } 
    }//GEN-LAST:event_btnConfirmActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Reschedule().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBookingClassInfo;
    private javax.swing.JLabel lblDateTimeInfo;
    private javax.swing.JTable tblClass;
    // End of variables declaration//GEN-END:variables
}
