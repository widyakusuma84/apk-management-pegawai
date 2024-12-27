import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUI {
    private static List<Pegawai> Pegawai;

    public static void main(String[] args) {
        Pegawai = FileHandler.loadPegawai();
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Employee Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel with buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        leftPanel.add(addButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(editButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(deleteButton);

        // Right panel with table
        JPanel rightPanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"NIK", "Name", "Age", "Gender", "Phone", "Email", "Address", "Department", "Score", "Salary", "Image Path"}, 0
        );
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane);

        // Load Pegawai into table
        Pegawai.forEach(emp -> tableModel.addRow(emp.toRow()));

        // Add actions for buttons
        addButton.addActionListener(e -> addEmployee(tableModel));
        editButton.addActionListener(e -> editEmployee(tableModel, table));
        deleteButton.addActionListener(e -> deleteEmployee(tableModel, table));

        // Double-click on a row to show employee details
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        showEmployeeDetails(Pegawai.get(row));
                    }
                }
            }
        });

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Show frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void addEmployee(DefaultTableModel model) {
        JTextField nikField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField scoreField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField imagePathField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("NIK:"));
        panel.add(nikField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Score:"));
        panel.add(scoreField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Image Path:"));
        panel.add(imagePathField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Pegawai emp = new Pegawai(
                    nikField.getText(),
                    nameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    genderField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    departmentField.getText(),
                    Integer.parseInt(scoreField.getText()),
                    Double.parseDouble(salaryField.getText()),
                    imagePathField.getText()
            );
            Pegawai.add(emp);
            model.addRow(emp.toRow());
            FileHandler.savePegawai(Pegawai);
        }
    }

    private static void editEmployee(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Pegawai emp = Pegawai.get(selectedRow);
    
        JTextField nikField = new JTextField(emp.getNik());
        JTextField nameField = new JTextField(emp.getName());
        JTextField ageField = new JTextField(String.valueOf(emp.getAge()));
        JTextField genderField = new JTextField(emp.getGender());
        JTextField phoneField = new JTextField(emp.getPhone());
        JTextField emailField = new JTextField(emp.getEmail());
        JTextField addressField = new JTextField(emp.getAddress());
        JTextField departmentField = new JTextField(emp.getDepartment());
        JTextField scoreField = new JTextField(String.valueOf(emp.getScore()));
        JTextField salaryField = new JTextField(String.valueOf(emp.getSalary()));
        JTextField imagePathField = new JTextField(emp.getImagePath());
    
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("NIK:"));
        panel.add(nikField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Score:"));
        panel.add(scoreField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Image Path:"));
        panel.add(imagePathField);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            emp = new Pegawai(
                    nikField.getText(),
                    nameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    genderField.getText(),
                    phoneField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    departmentField.getText(),
                    Integer.parseInt(scoreField.getText()),
                    Double.parseDouble(salaryField.getText()),
                    imagePathField.getText()
            );
            Pegawai.set(selectedRow, emp);
            model.setValueAt(emp.getNik(), selectedRow, 0);
            model.setValueAt(emp.getName(), selectedRow, 1);
            model.setValueAt(emp.getAge(), selectedRow, 2);
            model.setValueAt(emp.getGender(), selectedRow, 3);
            model.setValueAt(emp.getPhone(), selectedRow, 4);
            model.setValueAt(emp.getEmail(), selectedRow, 5);
            model.setValueAt(emp.getAddress(), selectedRow, 6);
            model.setValueAt(emp.getDepartment(), selectedRow, 7);
            model.setValueAt(emp.getScore(), selectedRow, 8);
            model.setValueAt(emp.getSalary(), selectedRow, 9);
            model.setValueAt(emp.getImagePath(), selectedRow, 10);
            FileHandler.savePegawai(Pegawai);
            JOptionPane.showMessageDialog(null, "Employee details updated successfully.");
        }
    }

    private static void deleteEmployee(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Pegawai.remove(selectedRow);
            model.removeRow(selectedRow);
            FileHandler.savePegawai(Pegawai);
            JOptionPane.showMessageDialog(null, "Employee deleted successfully.");
        }
    }

    private static void showEmployeeDetails(Pegawai emp) {
        JFrame detailFrame = new JFrame("Employee Details");
        detailFrame.setSize(500, 300);

        JPanel detailPanel = new JPanel(new BorderLayout());

        // Load image
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(emp.getImagePath());
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Employee details
        JPanel details = new JPanel(new GridLayout(0, 1));
        details.add(new JLabel("NIK: " + emp.getNik()));
        details.add(new JLabel("Name: " + emp.getName()));
        details.add(new JLabel("Age: " + emp.getAge()));
        details.add(new JLabel("Gender: " + emp.getGender()));
        details.add(new JLabel("Phone: " + emp.getPhone()));
        details.add(new JLabel("Email: " + emp.getEmail()));
        details.add(new JLabel("Address: " + emp.getAddress()));
        details.add(new JLabel("Department: " + emp.getDepartment()));
        details.add(new JLabel("Score: " + emp.getScore()));
        details.add(new JLabel("Salary: " + emp.getSalary()));

        detailPanel.add(imageLabel, BorderLayout.WEST);
        detailPanel.add(details, BorderLayout.CENTER);

        detailFrame.add(detailPanel);
        detailFrame.setVisible(true);
    }
}
