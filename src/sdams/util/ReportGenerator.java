/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sdams.util;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Foon
 */
public interface ReportGenerator<T> {
    void loadData();
    void applyFilter();
    void resetFilters();
    void populateFilters();
    void updateSummary(DefaultTableModel model);
}
