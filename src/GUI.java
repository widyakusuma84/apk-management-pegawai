import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kelas GUI adalah ui pengguna untuk Sistem Manajemen Pegawai.
 * Aplikasi ini memungkinkan pengguna untuk melakukan login, menambah,
 * mengedit, menghapus, dan menampilkan informasi pegawai.
 */
public class GUI {
    private static List<Pegawai> Pegawai;
    private static Map<String, String> userCredentials = new HashMap<>();

    public static void main(String[] args) {
        userCredentials.put("admin", "admin");

        Pegawai = FileHandler.loadPegawai();
        SwingUtilities.invokeLater(GUI::showLogin);
    }

    /**
     * Menampilkan ui login.
     */
    private static void showLogin() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(350, 300);
        loginFrame.setLayout(null);
    
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 80, 25);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 30, 120, 25);
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 80, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 120, 25);
    
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 110, 100, 30);
    
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setBounds(50, 150, 250, 25);
        messageLabel.setForeground(Color.RED);
    
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(messageLabel);
    
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
    
            if (authenticate(username, password)) {
                loginFrame.dispose();
                SwingUtilities.invokeLater(GUI::TampilkanGUI);
            } else {
                messageLabel.setText("Username atau Password salah!");
            }
        });
    
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    /**
     * Metode untuk autentikasi pengguna.
     * @param username Nama pengguna.
     * @param password Kata sandi.
     * @return True jika autentikasi berhasil, sebaliknya false.
     */
    private static boolean authenticate(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    /**
     * Menampilkan ui utama sistem manajemen pegawai.
     */
    public static void TampilkanGUI() {
        JFrame frame = new JFrame("Sistem Managemen Pegawai");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
    
        JLabel headerLabel = new JLabel("Sistem Manajemen Pegawai", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerLabel.setForeground(Color.WHITE);
        mainPanel.add(headerLabel, BorderLayout.NORTH);
    
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Tambah Pegawai");
        JButton editButton = new JButton("Edit Pegawai");
        JButton showDetailsButton = new JButton("Tampilkan Detail");
        JButton deleteButton = new JButton("Hapus Pegawai");
    
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(showDetailsButton);
        bottomPanel.add(deleteButton);
    
        JPanel rightPanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"ID", "Nama", "Umur", "Jenis Kelamin", "No. hp", "Email", "Alamat", "Department", "Nilai", "Gaji"}, 0
        );
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane);
    
        Pegawai.forEach(emp -> {
            tableModel.addRow(new Object[]{
                    emp.getNik(),
                    emp.getName(),
                    emp.getAge(),
                    emp.getGender(),
                    emp.getPhone(),
                    emp.getEmail(),
                    emp.getAddress(),
                    emp.getDepartment(),
                    emp.getScore(),
                    emp.getSalaryFormatted()
            });
        });
    
        addButton.addActionListener(e -> TambahPegawai(tableModel));
        editButton.addActionListener(e -> editPegawai(tableModel, table));
        deleteButton.addActionListener(e -> deletePegawai(tableModel, table));
    
        showDetailsButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilihlah baris di tabel untuk menampilkan detail pegawai tersebut!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                showEmployeeDetails(Pegawai.get(selectedRow));
            }
        });
    
        mainPanel.setBackground(Color.BLUE);
        rightPanel.setBackground(Color.BLUE);
        bottomPanel.setBackground(Color.BLUE);
    
        Color buttonBackground = new Color(240, 240, 240);
        Color buttonForeground = Color.BLACK;
        addButton.setBackground(buttonBackground);
        editButton.setBackground(buttonBackground);
        showDetailsButton.setBackground(buttonBackground);
        deleteButton.setBackground(buttonBackground);
        addButton.setForeground(buttonForeground);
        editButton.setForeground(buttonForeground);
        showDetailsButton.setForeground(buttonForeground);
        deleteButton.setForeground(buttonForeground);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
    
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Menampilkan dialog untuk menambahkan pegawai baru.
     * Data pegawai yang dimasukkan akan ditambahkan ke tabel dan disimpan ke dalam file.
     *
     * @param model DefaultTableModel dari tabel pegawai yang akan diperbarui.
     */
    private static void TambahPegawai(DefaultTableModel model) {
        JTextField nikField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JComboBox<String> genderField = new JComboBox<>(new String[]{"Laki-laki", "Perempuan", "Lainnya???"});
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextArea addressField = new JTextArea(3, 20);
        JScrollPane addressScroll = new JScrollPane(addressField);
        JTextField departmentField = new JTextField();
        JTextField scoreField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField imagePathField = new JTextField();
    
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nikField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Umur:"), gbc);
        gbc.gridx = 1;
        formPanel.add(ageField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        formPanel.add(genderField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("No. hp:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressScroll, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Nilai:"), gbc);
        gbc.gridx = 1;
        formPanel.add(scoreField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Gaji:"), gbc);
        gbc.gridx = 1;
        formPanel.add(salaryField, gbc);
    
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("File Gambar:"), gbc);
        gbc.gridx = 1;
        formPanel.add(imagePathField, gbc);
    
        mainPanel.add(new JLabel("Tambah Pegawai"), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
    
        int result = JOptionPane.showConfirmDialog(null, mainPanel, "Add Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String imagePath = "images/" + imagePathField.getText().trim();
    
                Pegawai emp = new Pegawai(
                        nikField.getText().trim(),
                        nameField.getText().trim(),
                        Integer.parseInt(ageField.getText().trim()),
                        genderField.getSelectedItem().toString(),
                        phoneField.getText().trim(),
                        emailField.getText().trim(),
                        addressField.getText().trim(),
                        departmentField.getText().trim(),
                        Integer.parseInt(scoreField.getText().trim()),
                        Double.parseDouble(salaryField.getText().trim()),
                        imagePath
                );
    
                Pegawai.add(emp);
                model.addRow(emp.toRow());
                FileHandler.savePegawai(Pegawai);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Format angka tidak valid. Check ulang input Umur, Nilai, dan Gaji.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Converts a Rupiah formatted string (e.g., "Rp10.000.000,00") to a double value.
     * 
     * @param rupiahString The Rupiah formatted string to be converted (e.g., "Rp10.000.000,00").
     * @return The double value representing the number (e.g., 10000000.0).
     * @throws NumberFormatException if the string cannot be parsed into a valid double.
     */
    public static double RupiahToDouble(String rupiahString) {
        String cleanString = rupiahString.replaceAll("[^0-9.,]", "");
        cleanString = cleanString.replace(",", ".");
        cleanString = cleanString.replaceAll("\\.(?=.*\\.)", "");        
        return Double.parseDouble(cleanString);
    }
    
    /**
     * Menampilkan dialog untuk mengedit data pegawai yang dipilih di tabel.
     * Data pegawai yang telah diperbarui akan disimpan ke dalam tabel dan file.
     *
     * @param model DefaultTableModel dari tabel pegawai yang akan diperbarui.
     * @param table JTable tempat data pegawai ditampilkan.
     */
    private static void editPegawai(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilihlah baris di tabel untuk edit detail pegawai tersebut!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (selectedRow < 0 || selectedRow >= Pegawai.size()) {
            JOptionPane.showMessageDialog(null, "Baris yang dipilih tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
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
        JTextField salaryField = new JTextField(String.valueOf(emp.getSalaryFormatted()));
        JTextField imagePathField = new JTextField(emp.getImagePath());
    
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("ID:"));
        panel.add(nikField);
        panel.add(new JLabel("Nama:"));
        panel.add(nameField);
        panel.add(new JLabel("Umur:"));
        panel.add(ageField);
        panel.add(new JLabel("Jenis Kelamin:"));
        panel.add(genderField);
        panel.add(new JLabel("No. hp:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Alamat:"));
        panel.add(addressField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Nilai:"));
        panel.add(scoreField);
        panel.add(new JLabel("Gaji:"));
        panel.add(salaryField);
        panel.add(new JLabel("File Gambar:"));
        panel.add(imagePathField);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Pegawai", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nik = nikField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String gender = genderField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                String address = addressField.getText().trim();
                String department = departmentField.getText().trim();
                int score = Integer.parseInt(scoreField.getText().trim());
                double salary = RupiahToDouble(salaryField.getText().trim());
                String imagePath = imagePathField.getText().trim();
    
                Pegawai updatedEmp = new Pegawai(nik, name, age, gender, phone, email, address, department, score, salary, imagePath);
                Pegawai.set(selectedRow, updatedEmp);
    
                model.setValueAt(updatedEmp.getNik(), selectedRow, 0);
                model.setValueAt(updatedEmp.getName(), selectedRow, 1);
                model.setValueAt(updatedEmp.getAge(), selectedRow, 2);
                model.setValueAt(updatedEmp.getGender(), selectedRow, 3);
                model.setValueAt(updatedEmp.getPhone(), selectedRow, 4);
                model.setValueAt(updatedEmp.getEmail(), selectedRow, 5);
                model.setValueAt(updatedEmp.getAddress(), selectedRow, 6);
                model.setValueAt(updatedEmp.getDepartment(), selectedRow, 7);
                model.setValueAt(updatedEmp.getScore(), selectedRow, 8);
                model.setValueAt(updatedEmp.getSalaryFormatted(), selectedRow, 9);
    
                FileHandler.savePegawai(Pegawai);
    
                JOptionPane.showMessageDialog(null, "Sukses memperbarui detail pegawai.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Format angka salah! Periksa kembali input umur, nilai, dan gaji.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Menghapus data pegawai yang dipilih dari tabel dan file.
     * Menampilkan dialog konfirmasi sebelum menghapus data.
     *
     * @param model DefaultTableModel dari tabel pegawai yang akan diperbarui.
     * @param table JTable tempat data pegawai ditampilkan.
     */
    private static void deletePegawai(DefaultTableModel model, JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilihlah baris di tabel untuk menghapus data pegawai tersebut!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data pegawai ini?", "Konfirmasi Penghapusan Data", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Pegawai.remove(selectedRow);
            model.removeRow(selectedRow);
            FileHandler.savePegawai(Pegawai);
            JOptionPane.showMessageDialog(null, "Data pegawai sukses dihapus.");
        }
    }

    /**
     * Menampilkan detail informasi pegawai dalam sebuah jendela baru.
     * Termasuk data umum seperti ID, nama, umur, jenis kelamin, gaji, dan lainnya.
     *
     * @param emp Objek Pegawai yang detailnya akan ditampilkan.
     */
    private static void showEmployeeDetails(Pegawai emp) {
        JFrame detailFrame = new JFrame("Informasi Pegawai");
        detailFrame.setSize(500, 400);
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setLayout(new BorderLayout());
    
        JLabel headerLabel = new JLabel("Informasi Pegawai", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
    
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(emp.getImagePath());
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsPanel.add(new JLabel("ID: " + emp.getNik()));
        detailsPanel.add(new JLabel("Nama: " + emp.getName()));
        detailsPanel.add(new JLabel("Umur: " + emp.getAge()));
        detailsPanel.add(new JLabel("Jenis Kelamin: " + emp.getGender()));
        detailsPanel.add(new JLabel("No. hp: " + emp.getPhone()));
        detailsPanel.add(new JLabel("Email: " + emp.getEmail()));
        detailsPanel.add(new JLabel("Alamat: " + emp.getAddress()));
        detailsPanel.add(new JLabel("Department: " + emp.getDepartment()));
        detailsPanel.add(new JLabel("Nilai: " + emp.getScore()));
        detailsPanel.add(new JLabel("Gaji: " + emp.getSalaryFormatted()));
    
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContentPanel.add(imageLabel, BorderLayout.WEST);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
    
        detailFrame.add(headerLabel, BorderLayout.NORTH);
        detailFrame.add(mainContentPanel, BorderLayout.CENTER);
    
        detailFrame.setLocationRelativeTo(null);
        detailFrame.setVisible(true);
    }
}
