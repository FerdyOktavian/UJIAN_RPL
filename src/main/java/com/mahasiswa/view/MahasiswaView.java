package com.mahasiswa.view;

import com.mahasiswa.model.Mahasiswa;
import com.mahasiswa.service.MahasiswaService;
import com.mahasiswa.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MahasiswaView extends JFrame {
    
    private MahasiswaService mahasiswaService;
    
    // Komponen Form
    private JTextField txtNim, txtNama, txtJurusan, txtAngkatan, txtEmail, txtCari;
    private JButton btnTambah, btnUpdate, btnHapus, btnReset, btnCari;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedId = -1;
    
    public MahasiswaView() {
        // Inisialisasi Spring Context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        mahasiswaService = context.getBean(MahasiswaService.class);
        
        // Setup Frame
        setTitle("Sistem Informasi Data Mahasiswa");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form (Atas)
        JPanel panelForm = createFormPanel();
        add(panelForm, BorderLayout.NORTH);
        
        // Panel Table (Tengah)
        JPanel panelTable = createTablePanel();
        add(panelTable, BorderLayout.CENTER);
        
        // Panel Tombol (Bawah)
        JPanel panelButton = createButtonPanel();
        add(panelTable, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
        
        // Load data awal
        loadTableData();
        
        setVisible(true);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Form Input Data Mahasiswa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // NIM
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1;
        txtNim = new JTextField(20);
        panel.add(txtNim, gbc);
        
        // Nama
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 3;
        txtNama = new JTextField(20);
        panel.add(txtNama, gbc);
        
        // Jurusan
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Jurusan:"), gbc);
        gbc.gridx = 1;
        txtJurusan = new JTextField(20);
        panel.add(txtJurusan, gbc);
        
        // Angkatan
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Angkatan:"), gbc);
        gbc.gridx = 3;
        txtAngkatan = new JTextField(20);
        panel.add(txtAngkatan, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Data Mahasiswa"));
        
        // Table
        String[] columns = {"ID", "NIM", "Nama", "Jurusan", "Angkatan", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua cell tidak bisa diedit langsung
            }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                fillFormFromSelectedRow();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel Pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Nama:"));
        txtCari = new JTextField(20);
        searchPanel.add(txtCari);
        btnCari = new JButton("Cari");
        btnCari.addActionListener(e -> searchData());
        searchPanel.add(btnCari);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadTableData());
        searchPanel.add(btnRefresh);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(e -> tambahData());
        panel.add(btnTambah);
        
        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateData());
        panel.add(btnUpdate);
        
        btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> hapusData());
        panel.add(btnHapus);
        
        btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> resetForm());
        panel.add(btnReset);
        
        return panel;
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0); // Clear table
        try {
            List<Mahasiswa> listMahasiswa = mahasiswaService.getAllMahasiswa();
            for (Mahasiswa mhs : listMahasiswa) {
                Object[] row = {
                    mhs.getId(),
                    mhs.getNim(),
                    mhs.getNama(),
                    mhs.getJurusan(),
                    mhs.getAngkatan(),
                    mhs.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tambahData() {
        if (!validateForm()) return;
        
        try {
            Mahasiswa mhs = new Mahasiswa();
            mhs.setNim(txtNim.getText().trim());
            mhs.setNama(txtNama.getText().trim());
            mhs.setJurusan(txtJurusan.getText().trim());
            mhs.setAngkatan(Integer.parseInt(txtAngkatan.getText().trim()));
            mhs.setEmail(txtEmail.getText().trim());
            
            mahasiswaService.saveMahasiswa(mhs);
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            resetForm();
            loadTableData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih data yang akan diupdate!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateForm()) return;
        
        try {
            Mahasiswa mhs = mahasiswaService.getMahasiswaById(selectedId);
            mhs.setNim(txtNim.getText().trim());
            mhs.setNama(txtNama.getText().trim());
            mhs.setJurusan(txtJurusan.getText().trim());
            mhs.setAngkatan(Integer.parseInt(txtAngkatan.getText().trim()));
            mhs.setEmail(txtEmail.getText().trim());
            
            mahasiswaService.updateMahasiswa(mhs);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            resetForm();
            loadTableData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih data yang akan dihapus!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus data ini?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                mahasiswaService.deleteMahasiswa(selectedId);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                resetForm();
                loadTableData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchData() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }
        
        tableModel.setRowCount(0);
        try {
            List<Mahasiswa> listMahasiswa = mahasiswaService.searchMahasiswaByNama(keyword);
            for (Mahasiswa mhs : listMahasiswa) {
                Object[] row = {
                    mhs.getId(),
                    mhs.getNim(),
                    mhs.getNama(),
                    mhs.getJurusan(),
                    mhs.getAngkatan(),
                    mhs.getEmail()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            selectedId = (int) tableModel.getValueAt(row, 0);
            txtNim.setText(tableModel.getValueAt(row, 1).toString());
            txtNama.setText(tableModel.getValueAt(row, 2).toString());
            txtJurusan.setText(tableModel.getValueAt(row, 3).toString());
            txtAngkatan.setText(tableModel.getValueAt(row, 4).toString());
            txtEmail.setText(tableModel.getValueAt(row, 5).toString());
        }
    }
    
    private void resetForm() {
        txtNim.setText("");
        txtNama.setText("");
        txtJurusan.setText("");
        txtAngkatan.setText("");
        txtEmail.setText("");
        txtCari.setText("");
        selectedId = -1;
        table.clearSelection();
    }
    
    private boolean validateForm() {
        if (txtNim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM harus diisi!");
            txtNim.requestFocus();
            return false;
        }
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama harus diisi!");
            txtNama.requestFocus();
            return false;
        }
        if (txtJurusan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jurusan harus diisi!");
            txtJurusan.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(txtAngkatan.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Angkatan harus berupa angka!");
            txtAngkatan.requestFocus();
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MahasiswaView());
    }
}