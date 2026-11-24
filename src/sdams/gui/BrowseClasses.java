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
import sdams.model.Staff;
import sdams.util.FileUtil;
import sdams.util.Generator;

/**
 *
 * @author Mei Shi
 */
public class BrowseClasses extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BrowseClasses.class.getName());

    private ArrayList<ClassSession> classList = new ArrayList<>();
    private ArrayList<Instructor> instructorList = new ArrayList<>();
    private DefaultTableModel tableModel;
    
    /**
     * Creates new form BrowseClasses
     */
    public BrowseClasses() {
        initComponents();
        loadClass();
        
        datePicker.addDateChangeListener(e -> filterClass());
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterClass();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterClass();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterClass();
            }
        });
    }
    
    private void loadClass(){
        try{
            // clear existing table rows
            DefaultTableModel model = (DefaultTableModel) tblClass.getModel();
            model.setRowCount(0);
            
            // read class information
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
            
            // display available class and full class
            for(ClassSession cs : classList){
                // skip completed and cancelled class
                if(cs.getStatus() != ClassSession.ClassStatus.Available && cs.getStatus() != ClassSession.ClassStatus.Full){
                    continue;
                }
                
                // get instructor name
                String instructorName = "Unknown";
                for(Instructor i : instructorList){
                    if(i.getId().equals(cs.getInstructorId())){
                        instructorName = i.getName();
                        break;
                    }
                }
                
                model.addRow(new Object[]{
                    cs.getClassId(), cs.getClassName(), cs.getDiscipline(), instructorName, cs.getDate(), cs.getStartTime(), cs.getEndTime(), cs.getFee(), cs.getAvailableSlots()
                });
            }
            
            // show message
            lblAvailable.setText("Classes: " + model.getRowCount());
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void filterClass(){
        // clear existing table rows
        DefaultTableModel model = (DefaultTableModel) tblClass.getModel();
        model.setRowCount(0);
        
        // get filter values
        String search = txtSearch.getText().trim().toLowerCase();
        String discipline = String.valueOf(cmbDisciplines.getSelectedItem());
        LocalDate date = datePicker.getDate();
        
        // check each class
        for(ClassSession cs : classList){
            // display available and full class only
            if(cs.getStatus() != ClassSession.ClassStatus.Available && cs.getStatus() != ClassSession.ClassStatus.Full){
                continue;
            }
            
            // for instructor name
            String instructorName = "Unknown";
            for(Instructor i : instructorList){
                if(i.getId().equals(cs.getInstructorId())){
                    instructorName = i.getName();
                    break;
                }
            }
            
            boolean matchSearch = search.isEmpty() || cs.getClassId().toLowerCase().contains(search) || cs.getClassName().toLowerCase().contains(search) || instructorName.toLowerCase().contains(search);
            boolean matchDisciplines = discipline.equals("All Disciplines") || cs.getDiscipline().equalsIgnoreCase(discipline);
            boolean matchDate = date == null || cs.getDate().equals(date);
            
            if(matchSearch && matchDisciplines && matchDate){
                model.addRow(new Object[]{
                    cs.getClassId(), cs.getClassName(), cs.getDiscipline(), instructorName, cs.getDate(), cs.getStartTime(), cs.getEndTime(), cs.getFee(), cs.getAvailableSlots()
                });
            }
        }
        
        // update available class count
        lblAvailable.setText("Classes: " + model.getRowCount());
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
        lblAvailable = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        cmbDisciplines = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnMyBookings = new javax.swing.JButton();
        btnBook = new javax.swing.JButton();
        datePicker = new com.github.lgooddatepicker.components.DatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Browse Classes");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        lblAvailable.setText("Classes: xx");

        txtSearch.setToolTipText("");
        txtSearch.addActionListener(this::txtSearchActionPerformed);

        cmbDisciplines.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Disciplines", "Karate", "Judo", "Taekwondo", "MMA" }));
        cmbDisciplines.addActionListener(this::cmbDisciplinesActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Class Name", "Disciplines", "Instructor", "Date", "Start Time", "End Time", "Fee (RM)", "Available Slot"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblClass.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
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

        btnBack.setText("Back to Student Main Menu");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnMyBookings.setText("My Bookings");
        btnMyBookings.addActionListener(this::btnMyBookingsActionPerformed);

        btnBook.setText("Book Selected Class");
        btnBook.addActionListener(this::btnBookActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnBack)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMyBookings)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBook))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(372, 372, 372)
                        .addComponent(lblAvailable)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAvailable)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMyBookings, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        StudentHome studentHome = new StudentHome();
        studentHome.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        filterClass();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cmbDisciplinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDisciplinesActionPerformed
        filterClass();
    }//GEN-LAST:event_cmbDisciplinesActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        cmbDisciplines.setSelectedIndex(0);
        datePicker.clear();
        
        filterClass();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnMyBookingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyBookingsActionPerformed
        MyBookings myBookings = new MyBookings();
        myBookings.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMyBookingsActionPerformed

    private void btnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookActionPerformed
        // must select a row
        int selectedRow = tblClass.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(null, "Please select a class first.");
            return;
        }
        
        // get selected class id
        int modelRow = tblClass.convertRowIndexToModel(selectedRow);
        String classId = tblClass.getModel().getValueAt(modelRow, 0).toString();
        
        // find selected class
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
        
        // check class status
        if(selectedClass.getStatus() == ClassSession.ClassStatus.Full || selectedClass.getAvailableSlots() <= 0){
            JOptionPane.showMessageDialog(null, "Sorry, this class is full! Please select another class.");
            return;
        }
        
        if(selectedClass.getStatus() != ClassSession.ClassStatus.Available){
            JOptionPane.showMessageDialog(null, "Sorry, this class is currently unavailable!");
            return;
        }
        
        // validate available class
        if(selectedClass.getAvailableSlots() > selectedClass.getMaxSlots()){
            JOptionPane.showMessageDialog(null, "Invalid class slot information. Please contact the Administrator.");
            return;
        }
        
        // get current student id
        String studentId = LoggedIn.getId();
        if(studentId == null || studentId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to identify the logged-in student. Please log in again.");
            return;
        }
        
        // read existing booking records
        ArrayList<Booking> bookingList = new ArrayList<>();
        FileUtil.read("booking.txt", bookingList, Booking::fromLine);
        
        // create new booking
        String bookingId = Generator.idGenerator("B", "booking.txt");
        if(bookingId == null || bookingId.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unable to generate booking ID. Please try again.");
            return;
        }
        
        Booking newBooking = new Booking(bookingId, studentId, classId, Booking.BookingStatus.Booked, java.time.LocalDate.now());
        bookingList.add(newBooking);
        
        // minus 1 available slots
        selectedClass.setAvailableSlots(selectedClass.getAvailableSlots() - 1);
        
        // update class status if no available slots
        if(selectedClass.getAvailableSlots() == 0){
            selectedClass.setStatus(ClassSession.ClassStatus.Full);
        }
        
        // save booking and class records
        FileUtil.write("booking.txt", bookingList, Booking::toLine);
        FileUtil.write("class.txt", classList, ClassSession::toLine);
        
        // refresh table
        loadClass();
        JOptionPane.showMessageDialog(null, "Class successfully booked");
    }//GEN-LAST:event_btnBookActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new BrowseClasses().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBook;
    private javax.swing.JButton btnMyBookings;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbDisciplines;
    private com.github.lgooddatepicker.components.DatePicker datePicker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvailable;
    private javax.swing.JTable tblClass;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
