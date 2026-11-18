/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sdams.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sdams.model.FacilityProblem;
import sdams.util.FileUtil;

/**
 *
 * @author User
 */
public class FacilityProblemFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FacilityProblemFrame.class.getName());
    
    private ArrayList<FacilityProblem> list = new ArrayList<>();
    private static final String FILE = "facility_problem.txt";

    /**
     * Creates new form FacilitiesProblem
     */
    public FacilityProblemFrame() {
        initComponents();
        setLocationRelativeTo(null);
        
        tableProblem.setModel(new DefaultTableModel(
            new Object[]{"Problem ID", "Equipment ID", "Reported By", "Date Reported", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        txfSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilters(); }
            });
        cmbStatus.addActionListener(e -> applyFilters());
        dpFrom.addDateChangeListener(e -> applyFilters());
        dpTo.addDateChangeListener(e -> applyFilters());
        tableProblem.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableProblem.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showDetails();
            }
        });
        loadTable();
    }
    
    public void loadTable(){
        FileUtil.read(FILE, list, FacilityProblem::fromLine);
        applyFilters();
    }
    
    private void showInTable(ArrayList<FacilityProblem> data) {
        DefaultTableModel model = (DefaultTableModel) tableProblem.getModel();
        model.setRowCount(0);
        for (FacilityProblem fp : data) {
            model.addRow(new Object[]{fp.getProblemId(), fp.getEquipmentId(), fp.getReportedBy(),
                                        fp.getDateReported(), fp.getStatus()});
        }
    }
    
    public void applyFilters(){
        String key = txfSearch.getText().trim().toLowerCase();
        String sta = String.valueOf(cmbStatus.getSelectedItem());
        LocalDate from = dpFrom.getDate();
        LocalDate to = dpTo.getDate();
        
        ArrayList<FacilityProblem> result = new ArrayList<>();
        
        if (from != null && to != null && from.isAfter(to)){
            showInTable(result);
            return;
        }
        for (FacilityProblem fp: list){
            boolean matchSearch = key.isEmpty()
                    || fp.getEquipmentId().toLowerCase().contains(key)
                    || fp.getProblemId().toLowerCase().contains(key);
            boolean matchSta = sta.endsWith("All")
                    || fp.getStatus().equalsIgnoreCase(sta);
            boolean matchDate;
            try{
                LocalDate d = LocalDate.parse(fp.getDateReported());
                matchDate = (from == null || !d.isBefore(from))
                        && (to == null || !d.isAfter(to));
            }catch (DateTimeParseException ex){
                matchDate = (from == null && to == null);
            }
            
            if (matchSearch && matchSta && matchDate){
                result.add(fp);
            }
        }
        showInTable(result);
    }
    
    private FacilityProblem getSelectedProblem(){
        int row = tableProblem.getSelectedRow();
        if (row == -1) return null;
        
        String id = tableProblem.getValueAt(row, 0).toString();
        for (FacilityProblem fp : list){
            if(fp.getProblemId().equals(id)) return fp;
        }
        return null;
    }
    
    private void showDetails(){
        FacilityProblem fp = getSelectedProblem();
        if (fp == null){
            txaDescription.setText("");
            txaDetail.setText("");
        }else{
            txaDescription.setText(fp.getDescription());
            txaDetail.setText(fp.getResolutionDetails());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        txfSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProblem = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaDescription = new javax.swing.JTextArea();
        btnUpdate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        dpFrom = new com.github.lgooddatepicker.components.DatePicker();
        dpTo = new com.github.lgooddatepicker.components.DatePicker();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaDetail = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Facility Problems");

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        txfSearch.addActionListener(this::txfSearchActionPerformed);

        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Status: All", "Pending", "In Progress", "Resolved" }));
        cmbStatus.addActionListener(this::cmbStatusActionPerformed);

        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        jScrollPane1.setViewportView(tableProblem);

        jLabel2.setText("Description");

        txaDescription.setEditable(false);
        txaDescription.setColumns(20);
        txaDescription.setRows(5);
        jScrollPane2.setViewportView(txaDescription);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        jLabel3.setText("From");

        jLabel4.setText("To");

        jLabel5.setText("Resolution Detail");

        txaDetail.setEditable(false);
        txaDetail.setColumns(20);
        txaDetail.setRows(5);
        jScrollPane4.setViewportView(txaDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txfSearch))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnSearch)
                                        .addGap(124, 124, 124)
                                        .addComponent(btnReset))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dpFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dpTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(dpFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        new FacilitiesOfficerHome().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void txfSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSearchActionPerformed
        applyFilters();
    }//GEN-LAST:event_txfSearchActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txfSearch.setText("");
        cmbStatus.setSelectedIndex(0);
        dpFrom.clear();
        dpTo.clear();
        applyFilters();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        FacilityProblem fp = getSelectedProblem();
        if (fp.getStatus().equalsIgnoreCase("Resolved")) {
            JOptionPane.showMessageDialog(this, "This problem is already resolved.");
            return;
        }
        if(fp == null){
            JOptionPane.showMessageDialog(this, "Please select a problem to update.");
            return;
        }
        new UpdateProblem(this, fp).setVisible(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        applyFilters();
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        applyFilters();
    }//GEN-LAST:event_btnSearchActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.github.lgooddatepicker.components.DatePicker dpFrom;
    private com.github.lgooddatepicker.components.DatePicker dpTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tableProblem;
    private javax.swing.JTextArea txaDescription;
    private javax.swing.JTextArea txaDetail;
    private javax.swing.JTextField txfSearch;
    // End of variables declaration//GEN-END:variables
}
