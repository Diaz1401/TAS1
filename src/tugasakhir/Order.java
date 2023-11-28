/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasakhir;

import java.sql.*;
import TugasAkhir.KoneksiKashoes;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Diaz
 */
public class Order extends javax.swing.JFrame {

    public static Connection cn;
    public static ResultSet rs;
    public static Statement st;
    public static PreparedStatement pst;      

    /**
     * Creates new form Order
     */
    public Order() {
        initComponents();
        update_cmb();
        update();
        txtTransaksi.setText(generate_id());
        txtTransaksiDetil.setText(generate_idDet());
        LocalDate currentDate = LocalDate.now();
        txtTanggal.setText(currentDate.toString());
    }

    private void addRow(int x) {
        DefaultTableModel model = (DefaultTableModel) TabelProses.getModel();
        for (int i = 0; i < x; i++) {
            model.addRow(new Object[]{"", ""});
        }
    }

    public String generate_id() {
        int row = 0;
        try {
            String sql = "SELECT COUNT(*) FROM transaksi";
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
        String id = "TR" + String.valueOf(row + 1);
        return id;
    }

    public String generate_idDet() {
        int row = 0;
        try {
            String sql = "SELECT COUNT(*) FROM transaksi_detil";
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
        String id = "TRD" + String.valueOf(row + 1);
        return id;
    }

    public void update_cmb() {
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM paket");
            while (rs.next()) {
                String column = rs.getString("id_paket");
                cmbPaket.addItem(column);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }

    public void update() {
        DefaultTableModel tbl = new DefaultTableModel(), tblket = new DefaultTableModel();
        tbl.addColumn("ID Transaksi");
        tbl.addColumn("Nama");
        tbl.addColumn("Telephone");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        tblket.addColumn("Tanggal");
        tblket.addColumn("Keterangan");
        Tabel.setModel(tbl);
        TabelKet.setModel(tblket);
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();

            // Update tabel tanpa setmodel
            rs = st.executeQuery("SELECT * FROM transaksi");
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("nama"),
                    rs.getString("no_telp"),
                    rs.getString("jml_sepatu"),
                    rs.getString("total_bayar")
                });
                Tabel.setModel(tbl);
            }

            // Update tabel keterangan
            rs = st.executeQuery("SELECT keterangan, tanggal FROM transaksi_detil");
            while (rs.next()) {
                tblket.addRow(new Object[]{
                    rs.getString("tanggal"),
                    rs.getString("keterangan")
                });
                TabelKet.setModel(tblket);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }

    public void tarif() {
        int rego = 0;
        int tarif = 0;
        try {
            DefaultTableModel model = (DefaultTableModel) TabelProses.getModel();
            int row = model.getRowCount();
            
            // Scan kolom bahan + tambah harga
            for (int i = 0; i < row; i++) {
                String value = model.getValueAt(i, 0).toString();
                switch (value) {
                    case "Canvas": {
                        rego += 5;
                        break;
                    }
                    case "Kulit": {
                        rego += 10;
                        break;
                    }
                    case "Suede": {
                        rego += 15;
                        break;
                    }
                    case "Nubuck": {
                        rego += 20;
                        break;
                    }
                    case "Karet": {
                        rego += 25;
                        break;
                    }
                    case "Denim": {
                        rego += 30;
                        break;
                    }
                    case "Mesh": {
                        rego += 35;
                        break;
                    }
                }
            }

            // Scan kolom paket + tambah harga
            for (int i = 0; i < row; i++) {
                String sql = "SELECT tarif FROM paket WHERE id_paket = ?";
                cn = KoneksiKashoes.koneksikashoesdB();
                pst = cn.prepareStatement(sql);
                pst.setString(1, model.getValueAt(i, 1).toString());
                rs = pst.executeQuery();
                if (rs.next()) {
                    tarif += rs.getInt("tarif");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
        txtTotal.setText(String.valueOf(tarif+rego));
    }

    public void keterangan() {
        try {
            DefaultTableModel model = (DefaultTableModel) TabelProses.getModel();
            int row = model.getRowCount();
            String bahan = null;
            String paket = null;
            String txtketerangan = "";

            for (int i = 0; i < row; i++) {
                String sql = "SELECT paket FROM paket WHERE id_paket = ?";
                bahan = model.getValueAt(i, 0).toString();
                cn = KoneksiKashoes.koneksikashoesdB();
                pst = cn.prepareStatement(sql);
                pst.setString(1, model.getValueAt(i, 1).toString());
                rs = pst.executeQuery();
                if (rs.next()) paket = rs.getString("paket");
                txtketerangan += bahan + "+" + paket + ", ";
            }
            txtKeterangan.setText(generate_idDet() + ": " + txtketerangan);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        cmbBahan = new javax.swing.JComboBox<>();
        txtTransaksi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelephone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        cmbPaket = new javax.swing.JComboBox<>();
        btOrder = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btProses = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelKet = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        TabelProses = new javax.swing.JTable();
        manageOrder = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTransaksiDetil = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        MasterBiaya = new javax.swing.JButton();
        Pengeluaran = new javax.swing.JButton();
        Profit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("ID Transaksi");

        cmbBahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih bahan" }));
        cmbBahan.setToolTipText("");
        cmbBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBahanActionPerformed(evt);
            }
        });

        txtTransaksi.setEnabled(false);
        txtTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiActionPerformed(evt);
            }
        });

        jLabel6.setText("Total Harga");

        jLabel4.setText("Nama");

        jLabel7.setText("No. Telephone");

        jLabel9.setText("Jumlah");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        cmbPaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih paket" }));
        cmbPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaketActionPerformed(evt);
            }
        });

        btOrder.setText("Order");
        btOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOrderActionPerformed(evt);
            }
        });

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabel);

        jLabel8.setText("Keterangan");

        btProses.setText("Proses");
        btProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProsesActionPerformed(evt);
            }
        });

        TabelKet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TabelKet);

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane3.setViewportView(txtKeterangan);

        TabelProses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bahan", "Paket"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(TabelProses);

        manageOrder.setText("Management");
        manageOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageOrderActionPerformed(evt);
            }
        });

        jLabel2.setText("ID Transaksi Detail");

        txtTransaksiDetil.setEnabled(false);

        jLabel3.setText("Tanggal");

        MasterBiaya.setText("Biaya");
        MasterBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MasterBiayaActionPerformed(evt);
            }
        });

        Pengeluaran.setText("Pengeluaran");
        Pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PengeluaranActionPerformed(evt);
            }
        });

        Profit.setText("Profit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(manageOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MasterBiaya)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Pengeluaran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Profit)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                                    .addComponent(txtNama)
                                                    .addComponent(txtTelephone))
                                                .addGap(23, 23, 23)
                                                .addComponent(btProses))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(41, 41, 41)
                                                .addComponent(cmbBahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cmbPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(25, 25, 25))
                                    .addComponent(jLabel9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(31, 31, 31)
                                        .addComponent(txtTransaksi))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtTransaksiDetil)))
                                .addGap(120, 120, 120))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(71, 71, 71)
                                        .addComponent(btOrder))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTransaksiDetil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btProses))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbBahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btOrder))
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageOrder)
                    .addComponent(MasterBiaya)
                    .addComponent(Pengeluaran)
                    .addComponent(Profit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBahanActionPerformed
        DefaultTableModel model = (DefaultTableModel) TabelProses.getModel();
        int row = TabelProses.getSelectedRow();
        if (row != -1) {
            // Modify values of the selected row
            model.setValueAt(cmbBahan.getSelectedItem(), row, 0);
        } else {
            // No row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Pilih baris pada tabel");
        }
        tarif();
        keterangan();
    }//GEN-LAST:event_cmbBahanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        cmbBahan.addItem("Canvas");
        cmbBahan.addItem("Kulit");
        cmbBahan.addItem("Suede");
        cmbBahan.addItem("Nubuck");
        cmbBahan.addItem("Karet");
        cmbBahan.addItem("Denim");
        cmbBahan.addItem("Mesh");
    }//GEN-LAST:event_formWindowActivated

    private void cmbPaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaketActionPerformed
        DefaultTableModel model = (DefaultTableModel) TabelProses.getModel();
        int row = TabelProses.getSelectedRow();
        if (row != -1) {
            // Modify values of the selected row
            model.setValueAt(cmbPaket.getSelectedItem(), row, 1);
        } else {
            // No row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Pilih baris pada tabel");
        }
        tarif();
        keterangan();
    }//GEN-LAST:event_cmbPaketActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOrderActionPerformed
        String id_transaksi = txtTransaksi.getText();                                    
        String id_transaksidetil = txtTransaksiDetil.getText();
        String nama = txtNama.getText();
        String no_telp = txtTelephone.getText();
        String jml_sepatu = txtJumlah.getText();
        String total_bayar = txtTotal.getText();
        String keterangan = txtKeterangan.getText();
        String tanggal = txtTanggal.getText();
        String bahan = cmbBahan.getSelectedItem().toString();
        String id_paket = cmbPaket.getSelectedItem().toString();

        // insert transaksi
        try {
            // Connect
            String sql = "INSERT INTO transaksi (id_transaksi, nama, no_telp, jml_sepatu, total_bayar) VALUES (?, ?, ?, ?, ?)";
            
            cn = KoneksiKashoes.koneksikashoesdB();
            pst = cn.prepareStatement(sql);

            // Set paramater
            pst.setString(1, id_transaksi);
            pst.setString(2, nama);
            pst.setString(3, no_telp);
            pst.setString(4, jml_sepatu);
            pst.setString(5, total_bayar);

            // Execute
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Order berhasil disimpan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
        
        // Insert transaksi detil
        try {
            // Connect
            String sql = "INSERT INTO transaksi_detil (id_transaksidetil, id_transaksi, id_paket, bahan, keterangan, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
            
            cn = KoneksiKashoes.koneksikashoesdB();
            pst = cn.prepareStatement(sql);

            // Set paramater
            pst.setString(1, id_transaksidetil);
            pst.setString(2, id_transaksi);
            pst.setString(3, id_paket);
            pst.setString(4, bahan);
            pst.setString(5, keterangan);
            pst.setString(6, tanggal);

            // Execute
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Order berhasil disimpan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
        // Update table
        update();
    }//GEN-LAST:event_btOrderActionPerformed

    private void btProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProsesActionPerformed
        addRow(Integer.parseInt(txtJumlah.getText()));
    }//GEN-LAST:event_btProsesActionPerformed

    private void manageOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageOrderActionPerformed
        // TODO add your handling code here:
        new MasterOrder().setVisible(true);
        dispose();
    }//GEN-LAST:event_manageOrderActionPerformed

    private void txtTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiActionPerformed

    private void MasterBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MasterBiayaActionPerformed
        new MasterBiaya().setVisible(true);
        dispose();
    }//GEN-LAST:event_MasterBiayaActionPerformed

    private void PengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PengeluaranActionPerformed
        new Pengeluaran().setVisible(true);
        dispose();
    }//GEN-LAST:event_PengeluaranActionPerformed

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
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Order().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton MasterBiaya;
    private javax.swing.JButton Pengeluaran;
    private javax.swing.JButton Profit;
    public javax.swing.JTable Tabel;
    public javax.swing.JTable TabelKet;
    private javax.swing.JTable TabelProses;
    private javax.swing.JButton btOrder;
    private javax.swing.JButton btProses;
    public javax.swing.JComboBox<String> cmbBahan;
    public javax.swing.JComboBox<String> cmbPaket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton manageOrder;
    public javax.swing.JTextField txtJumlah;
    public javax.swing.JTextArea txtKeterangan;
    public javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTanggal;
    public javax.swing.JTextField txtTelephone;
    private javax.swing.JTextField txtTotal;
    public javax.swing.JTextField txtTransaksi;
    public javax.swing.JTextField txtTransaksiDetil;
    // End of variables declaration//GEN-END:variables
}
