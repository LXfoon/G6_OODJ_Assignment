/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sdams.util;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import sdams.model.Admin;

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
