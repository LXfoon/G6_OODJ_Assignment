/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.swing.table.DefaultTableModel;
import sdams.model.AttendanceRecord;
import sdams.model.ClassSession;
import sdams.model.Student;
import sdams.util.FileUtil;

/**
 *
 * @author Foon
 */
public class ViewAtt extends javax.swing.JFrame {
ArrayList<AttendanceRecord> allAtt = new ArrayList<>();
ArrayList<Student> allUser = new ArrayList<>();
ArrayList<ClassSession> allClass = new ArrayList<>();
TreeSet<String> disciplineList = new TreeSet<>();
TreeSet<String> instructorList = new TreeSet<>();

    /**
     * Creates new form ViewAtt
     */
    public ViewAtt() {
        initComponents();
        setLocationRelativeTo(null);
        searchTxt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
        });
        
        FileUtil.read("attendance.txt", allAtt, AttendanceRecord::fromLine);
        FileUtil.read("user.txt", allUser, Student::fromLine);
        FileUtil.read("class.txt", allClass, ClassSession::fromLine);
        
        populateFilters();
        
        disciplineDropdown.addActionListener(e ->{
            Object selection = disciplineDropdown.getSelectedItem();
            if (selection != null) applyFilter();
        });
        
        instructorDropdown.addActionListener(e ->{
            Object selection = instructorDropdown.getSelectedItem();
            if (selection != null) applyFilter();
        });
        
        dateSearch.addDateChangeListener(e -> applyFilter());
            
        try{
            DefaultTableModel model = (DefaultTableModel) attTable.getModel();
            model.setRowCount(0);
            attTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            attTable.getColumnModel().getColumn(1).setPreferredWidth(50);
            attTable.getColumnModel().getColumn(3).setPreferredWidth(50);
            
            for(AttendanceRecord a : allAtt){
                addRow(model, a);
            }
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void applyFilter(){
        DefaultTableModel model = (DefaultTableModel) attTable.getModel();
        model.setRowCount(0);
        
        String search = searchTxt.getText().trim().toLowerCase();
        Object disSelection = disciplineDropdown.getSelectedItem();
        String disFilter = disSelection == null ? "All" : disSelection.toString();
        Object insSelction = instructorDropdown.getSelectedItem();
        String insFilter = insSelction == null ? "All" : insSelction.toString();
        LocalDate dateFilter = dateSearch.getDate();
        
        for(AttendanceRecord a : allAtt){
            if(a.getAttId()== null || a.getClassId() == null) continue;
            String studentName = getStudentName(a.getStudentId()).toLowerCase();
            
            ClassSession attClass = getClassInfo(a.getClassId());
            if(attClass == null) continue;
            String className = attClass.getClassName() == null ? "" : attClass.getClassName().toLowerCase();
            String discipline = attClass.getDiscipline() == null ? "" : attClass.getDiscipline();
            LocalDate classDate = attClass.getDate();
            String instructor = getInstructor(attClass.getInstructorId());
            
            boolean matchSearch = search.isEmpty() || studentName.contains(search) || className.contains(search);
            boolean matchDiscipline = disFilter.equals("All") || discipline.equalsIgnoreCase(disFilter);
            boolean matchInstructor = insFilter.equals("All") || instructor.equalsIgnoreCase(insFilter);
            boolean matchDate = dateFilter == null|| dateFilter.equals(classDate);

            if(matchSearch && matchDiscipline && matchInstructor && matchDate){
                addRow(model, a);
            }
        }
    }
    
    private void populateFilters() {
        disciplineList.clear();
        disciplineDropdown.removeAllItems();
        disciplineDropdown.addItem("All");
        
        instructorList.clear();
        instructorDropdown.removeAllItems();
        instructorDropdown.addItem("All");
        
        for(AttendanceRecord a : allAtt){
            if(a.getAttId() == null) continue;

            ClassSession attClass = getClassInfo(a.getClassId());
            if(attClass == null) continue;
            
            String discipline = attClass.getDiscipline();
            if(discipline != null && !discipline.isBlank()) disciplineList.add(discipline);

            String instructor = getInstructor(attClass.getInstructorId());
            if(instructor != null && !instructor.isBlank()) instructorList.add(instructor);
        }
        
        for(String disciplineVal : disciplineList){
            disciplineDropdown.addItem(disciplineVal);
        }
        
        for(String instructorVal : instructorList){
            instructorDropdown.addItem(instructorVal);
        }
    }
    
    private String getStudentName(String studentId){
        String studentName = "";
        
        for(Student s : allUser){
                if(s.getId() == null) continue;
                
                if(s.getId().equalsIgnoreCase(studentId) && "Student".equalsIgnoreCase(s.getRole())){
                    studentName = s.getName() == null ? "" : s.getName();
                    break;
                }
            }
        
        return studentName;
    }

    private ClassSession getClassInfo(String classId){        
        for(ClassSession c : allClass){
                if(c.getClassId() == null) continue;
                
                if(c.getClassId().equalsIgnoreCase(classId)){
                    return c;
                }
            }
        return null;
    }
    
    private String getInstructor(String instructorId){
        String instructor = "";
        
        for(Student s : allUser){
            if(s.getId() == null) continue;

            if(s.getId().equalsIgnoreCase(instructorId) && "Instructor".equalsIgnoreCase(s.getRole())){
                instructor = s.getName();
                break;
            }
        }
        
        return instructor;
    }
    
    private void addRow(DefaultTableModel model, AttendanceRecord a){
        if(a == null || a.getAttId() == null) return;
        
        String studentName = getStudentName(a.getStudentId());
                
        ClassSession attClass = getClassInfo(a.getClassId());
        if(attClass == null) return;
        String className = attClass.getClassName();
        String disciplineName = attClass.getDiscipline();
        String insName = getInstructor(attClass.getInstructorId());
        LocalDate classDate = attClass.getDate();

        model.addRow(new Object[]{
                a.getAttId(),
                studentName,
                className,
                insName,
                disciplineName,
                classDate,
                a.getStatus()
            });
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
        backBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        attTable = new javax.swing.JTable();
        searchTxt = new javax.swing.JTextField();
        instructorDropdown = new javax.swing.JComboBox<>();
        disciplineDropdown = new javax.swing.JComboBox<>();
        dateSearch = new com.github.lgooddatepicker.components.DatePicker();
        clearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("View Attendance");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        backBtn.setText("Back to Main Menu");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        attTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student", "Class", "Instructor", "Discipline", "Class Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(attTable);

        searchTxt.setToolTipText("Search student, class...");

        instructorDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        disciplineDropdown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        clearBtn.setText("Clear Filter");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(instructorDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disciplineDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disciplineDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instructorDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(clearBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        new AttendanceHome().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        searchTxt.setText("");
        disciplineDropdown.setSelectedItem("All");
        instructorDropdown.setSelectedItem("All");
        dateSearch.clear();
        applyFilter();
    }//GEN-LAST:event_clearBtnActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAtt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAtt().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attTable;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton clearBtn;
    private com.github.lgooddatepicker.components.DatePicker dateSearch;
    private javax.swing.JComboBox<String> disciplineDropdown;
    private javax.swing.JComboBox<String> instructorDropdown;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
