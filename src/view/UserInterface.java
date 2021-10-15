package view;

import model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;

public class UserInterface extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;

    private JFrame addNewStaffFrame = null;
    private JFrame updateStaffFrame = null;
    private JFrame deleteStaffFrame = null;

    private HibernateActionListener hibernateActionListener;

    public UserInterface() {
        setupWindow();
    }

    private void setupWindow() {
        //text field for search by name
        JLabel searchLabel = new JLabel("Enter name: ");
        JTextField textField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(event -> {
            if (textField.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please, enter name to search.");
            else
                handleSearchButton(textField.getText());
        });

        //refresh button
        Icon icon = new ImageIcon("resources/refreshImage.png");
        JButton refreshButton = new JButton(icon);
        refreshButton.setPreferredSize(new Dimension(24, 24));
        refreshButton.setEnabled(false);
        refreshButton.setToolTipText("Refresh button for table");
        refreshButton.addActionListener(event -> {
            refreshTable();
        });

        //table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Address");
        tableModel.addColumn("Paycheck (DIN)");

        //setting wrap for cells
        for (int i = 0; i <= 4; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new WordWrapCellRenderer());
        }

        table.setEnabled(false);

        // add new staff button
        JButton addNewStaffButton = new JButton("Add New Staff");
        addNewStaffButton.addActionListener(event -> showAddNewStaffWindow());

        // update staff button
        JButton updateStaffButton = new JButton("Update Staff");
        updateStaffButton.addActionListener(event -> showUpdateStaffWindow());

        // delete staff button
        JButton deleteStaffButton = new JButton("Delete Staff");
        deleteStaffButton.addActionListener(event -> showDeleteStaffWindow());

        //checkbox for showing all data in table
        JCheckBox showDataCheckBox = new JCheckBox("Show All Staff");
        showDataCheckBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                removeAllRows();
                handleFillTableData();
                refreshButton.setEnabled(true);
            } else {
                refreshButton.setEnabled(false);
                removeAllRows();
            }
        });

        JFrame f = new JFrame();
        JPanel panel = new JPanel();
        f.setSize(550, 580);
        f.setTitle("Hibernate App");
        panel.add(searchLabel);
        panel.add(textField);
        panel.add(searchButton);
        panel.add(new JScrollPane(table));
        panel.add(addNewStaffButton);
        panel.add(updateStaffButton);
        panel.add(deleteStaffButton);
        panel.add(showDataCheckBox);
        panel.add(refreshButton);
        f.add(panel);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setVisible(true);
    }

    public void setListeners(HibernateActionListener hibernateActionListener) {
        this.hibernateActionListener = hibernateActionListener;
    }

    public void handleFillTableData() {
        if (hibernateActionListener != null)
            hibernateActionListener.fillTableData(tableModel);
    }

    public void handleSearchButton(String name) {
        if (hibernateActionListener != null)
            hibernateActionListener.searchStaffByName(name, tableModel, table);
    }

    private void removeAllRows() {
        tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
    }

    private void showAddNewStaffWindow() {
        JLabel nameLabel = new JLabel("Enter name: ", SwingConstants.RIGHT);
        nameLabel.setPreferredSize(new Dimension(92, 20));
        JTextField nameTf = new JTextField(15);

        JLabel ageLabel = new JLabel("Enter age: ", SwingConstants.RIGHT);
        ageLabel.setPreferredSize(new Dimension(92, 20));
        JTextField ageTf = new JTextField(15);

        JLabel addressLabel = new JLabel("Enter address: ", SwingConstants.RIGHT);
        addressLabel.setPreferredSize(new Dimension(92, 20));
        JTextField addressTf = new JTextField(15);

        JLabel paycheckLabel = new JLabel("Enter paycheck: ", SwingConstants.RIGHT);
        JTextField paycheckTf = new JTextField(15);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(event -> {
            if (nameTf.getText().isEmpty() || ageTf.getText().isEmpty() || addressTf.getText().isEmpty() || paycheckTf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please, enter all data required.");
            } else {
                Staff staff = new Staff(nameTf.getText(), Integer.parseInt(ageTf.getText()), addressTf.getText(), Integer.parseInt(paycheckTf.getText()));
                //handle add button
                if (hibernateActionListener != null)
                    hibernateActionListener.addNewStaff(staff);
                JOptionPane.showMessageDialog(null, "Staff added successfully!");
                addNewStaffFrame.dispose();
            }
        });

        addNewStaffFrame = new JFrame();
        JPanel panel = new JPanel();
        addNewStaffFrame.setSize(300, 200);
        addNewStaffFrame.setTitle("Add New Staff");
        panel.add(nameLabel);
        panel.add(nameTf);
        panel.add(ageLabel);
        panel.add(ageTf);
        panel.add(addressLabel);
        panel.add(addressTf);
        panel.add(paycheckLabel);
        panel.add(paycheckTf);
        panel.add(addButton);
        addNewStaffFrame.add(panel);
        addNewStaffFrame.setLocationRelativeTo(null);
        addNewStaffFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addNewStaffFrame.setResizable(false);
        addNewStaffFrame.setVisible(true);
    }

    private void showUpdateStaffWindow() {
        JLabel idLabel = new JLabel("Enter Staff ID: ", SwingConstants.RIGHT);
        idLabel.setPreferredSize(new Dimension(120, 20));
        idLabel.setForeground(Color.red);
        JTextField idTf = new JTextField(15);

        JLabel nameLabel = new JLabel("Enter new name: ", SwingConstants.RIGHT);
        nameLabel.setPreferredSize(new Dimension(120, 20));
        JTextField nameTf = new JTextField(15);

        JLabel ageLabel = new JLabel("Enter new age: ", SwingConstants.RIGHT);
        ageLabel.setPreferredSize(new Dimension(120, 20));
        JTextField ageTf = new JTextField(15);

        JLabel addressLabel = new JLabel("Enter new address: ", SwingConstants.RIGHT);
        addressLabel.setPreferredSize(new Dimension(120, 20));
        JTextField addressTf = new JTextField(15);

        JLabel paycheckLabel = new JLabel("Enter new paycheck: ", SwingConstants.RIGHT);
        JTextField paycheckTf = new JTextField(15);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(event -> {
            if (idTf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please, enter ID of staff you wish to update.");
            } else {
                if (nameTf.getText().isEmpty() && ageTf.getText().isEmpty() && addressTf.getText().isEmpty() && paycheckTf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please, enter at least one parameter you wish to update.");
                }

                //if user does not wish to update everything
                Staff staff = new Staff();
                staff.setStaffId(Integer.parseInt(idTf.getText()));

                Staff currentStuff = (Staff) hibernateActionListener.getStaffById(Integer.parseInt(idTf.getText()));
                if (nameTf.getText().isEmpty())
                    staff.setName(currentStuff.getName());
                else
                    staff.setName(nameTf.getText());

                if (ageTf.getText().isEmpty())
                    staff.setAge(currentStuff.getAge());
                else
                    staff.setAge(Integer.parseInt(ageTf.getText()));

                if (addressTf.getText().isEmpty())
                    staff.setAddress(currentStuff.getAddress());
                else
                    staff.setAddress(addressTf.getText());

                if (paycheckTf.getText().isEmpty())
                    staff.setPaycheck(currentStuff.getPaycheck());
                else
                    staff.setPaycheck(Integer.parseInt(paycheckTf.getText()));

                if (hibernateActionListener != null)
                    hibernateActionListener.updateStaff(staff);
                JOptionPane.showMessageDialog(null, "Staff updated successfully!");
                updateStaffFrame.dispose();
            }
        });

        updateStaffFrame = new JFrame();
        JPanel panel = new JPanel();
        updateStaffFrame.setSize(300, 200);
        updateStaffFrame.setTitle("Update Staff");
        panel.add(idLabel);
        panel.add(idTf);
        panel.add(nameLabel);
        panel.add(nameTf);
        panel.add(ageLabel);
        panel.add(ageTf);
        panel.add(addressLabel);
        panel.add(addressTf);
        panel.add(paycheckLabel);
        panel.add(paycheckTf);
        panel.add(updateButton);
        updateStaffFrame.add(panel);
        updateStaffFrame.setLocationRelativeTo(null);
        updateStaffFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateStaffFrame.setResizable(false);
        updateStaffFrame.setVisible(true);
    }

    private void showDeleteStaffWindow() {
        JLabel idLabel = new JLabel("Enter Staff ID: ", SwingConstants.RIGHT);
        idLabel.setForeground(Color.red);
        JTextField idTf = new JTextField(5);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> {
            if (idTf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please, enter desired ID of staff to delete.");
            } else {
                int response = JOptionPane.showOptionDialog(null, "Are you sure want to delete staff with this ID: " + idTf.getText(), "CONFIRMATION",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null);

                if (response == JOptionPane.YES_OPTION) {
                    int staffId = Integer.parseInt(idTf.getText());
                    if (hibernateActionListener != null)
                        hibernateActionListener.deleteStaff(staffId);
                    JOptionPane.showMessageDialog(null, "Staff deleted successfully!");
                    deleteStaffFrame.dispose();
                }
            }
        });

        deleteStaffFrame = new JFrame();
        JPanel panel = new JPanel();
        deleteStaffFrame.setSize(270, 100);
        deleteStaffFrame.setTitle("Delete Staff");
        panel.add(idLabel);
        panel.add(idTf);
        panel.add(deleteButton);
        deleteStaffFrame.add(panel);
        deleteStaffFrame.setLocationRelativeTo(null);
        deleteStaffFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        deleteStaffFrame.setResizable(false);
        deleteStaffFrame.setVisible(true);
    }

    private void refreshTable() {
        removeAllRows();
        handleFillTableData();
    }

    static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        WordWrapCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height + 20);
            }
            return this;
        }
    }

}
