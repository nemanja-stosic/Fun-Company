package view;

import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public interface HibernateActionListener {
    void fillTableData(DefaultTableModel tableModel);
    void addNewStaff(Staff staff);
    void updateStaff(Staff staff);
    void deleteStaff(int id);
    void searchStaffByName(String name, DefaultTableModel tableModel, JTable table);
    Object getStaffById(int id);
}
