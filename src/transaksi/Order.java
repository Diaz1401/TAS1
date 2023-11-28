/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package transaksi;

import master.MasterOrder;
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
        setExtendedState(Order.MAXIMIZED_BOTH);
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
        DefaultTableModel tbl = new DefaultTableModel(), tblket = new DefaultTableModel(), tbltang = new DefaultTableModel();
        tbl.addColumn("ID Transaksi");
        tbl.addColumn("Nama");
        tbl.addColumn("Telephone");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        tblket.addColumn("Keterangan");
        tbltang.addColumn("ID Transaksi");
        tbltang.addColumn("Tanggal");
        Tabel.setModel(tbl);
        TabelKet.setModel(tblket);
        tabletgl.setModel(tbltang);
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();

            // Update tabel tanpa setmodel
            rs = st.executeQuery("SELECT * FROM transaksi" 
                    + "");
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
            rs = st.executeQuery("SELECT keterangan FROM transaksi_detil");
            while (rs.next()) {
                tblket.addRow(new Object[]{
                    rs.getString("keterangan")
                });
                TabelKet.setModel(tblket);
            }
            // Update tabel tanggal
            rs = st.executeQuery("SELECT id_transaksi, tanggal FROM transaksi_detil");
            while (rs.next()) {
                tbltang.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("tanggal")
                });
                tabletgl.setModel(tbltang);
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

        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelKet = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        TabelProses = new javax.swing.JTable();
        txtTransaksiDetil = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabletgl = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCari.setEditable(false);
        txtCari.setBackground(new java.awt.Color(35, 25, 87));
        txtCari.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtCari.setForeground(new java.awt.Color(255, 255, 255));
        txtCari.setText("Cari");
        txtCari.setBorder(null);
        txtCari.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });
        getContentPane().add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 60, 400, 40));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 61 (1).png"))); // NOI18N
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 340, -1, 40));

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel1.setText("ID Transaksi");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 240, -1, -1));

        cmbBahan.setBackground(new java.awt.Color(35, 25, 87));
        cmbBahan.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        cmbBahan.setForeground(new java.awt.Color(255, 255, 255));
        cmbBahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih bahan" }));
        cmbBahan.setToolTipText("");
        cmbBahan.setBorder(null);
        cmbBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBahanActionPerformed(evt);
            }
        });
        getContentPane().add(cmbBahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, -1));

        txtTransaksi.setBackground(new java.awt.Color(242, 242, 242));
        txtTransaksi.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtTransaksi.setForeground(new java.awt.Color(35, 25, 87));
        txtTransaksi.setBorder(null);
        txtTransaksi.setEnabled(false);
        txtTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(txtTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 240, 130, 20));

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel6.setText("Total Harga");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 730, -1, -1));

        txtTotal.setBackground(new java.awt.Color(35, 25, 87));
        txtTotal.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(228, 105, 81));
        txtTotal.setBorder(null);
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 750, 60, 20));

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel4.setText("Nama");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 320, -1, -1));

        txtNama.setBackground(new java.awt.Color(35, 25, 87));
        txtNama.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtNama.setForeground(new java.awt.Color(255, 255, 255));
        txtNama.setBorder(null);
        getContentPane().add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, 130, 20));

        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel7.setText("No. Telephone");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, -1, -1));

        txtTelephone.setBackground(new java.awt.Color(35, 25, 87));
        txtTelephone.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtTelephone.setForeground(new java.awt.Color(255, 255, 255));
        txtTelephone.setBorder(null);
        getContentPane().add(txtTelephone, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 380, 130, 20));

        jLabel9.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel9.setText("Jumlah");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 350, -1, -1));

        txtJumlah.setBackground(new java.awt.Color(35, 25, 87));
        txtJumlah.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtJumlah.setForeground(new java.awt.Color(255, 255, 255));
        txtJumlah.setBorder(null);
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        getContentPane().add(txtJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 350, 130, 20));

        cmbPaket.setBackground(new java.awt.Color(35, 25, 87));
        cmbPaket.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        cmbPaket.setForeground(new java.awt.Color(255, 255, 255));
        cmbPaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih paket" }));
        cmbPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaketActionPerformed(evt);
            }
        });
        getContentPane().add(cmbPaket, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 410, 100, -1));

        Tabel.setBackground(new java.awt.Color(35, 25, 87));
        Tabel.setForeground(new java.awt.Color(255, 255, 255));
        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Tabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tabel.setSelectionBackground(new java.awt.Color(255, 255, 255));
        Tabel.setSelectionForeground(new java.awt.Color(35, 25, 87));
        jScrollPane1.setViewportView(Tabel);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 240, 680, 190));

        jLabel8.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel8.setText("Keterangan");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 560, -1, -1));

        TabelKet.setBackground(new java.awt.Color(35, 25, 87));
        TabelKet.setForeground(new java.awt.Color(255, 255, 255));
        TabelKet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TabelKet.setFocusable(false);
        jScrollPane2.setViewportView(TabelKet);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 450, 680, 190));

        jScrollPane3.setBackground(new java.awt.Color(35, 25, 87));
        jScrollPane3.setBorder(null);

        txtKeterangan.setBackground(new java.awt.Color(35, 25, 87));
        txtKeterangan.setColumns(20);
        txtKeterangan.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtKeterangan.setForeground(new java.awt.Color(255, 255, 255));
        txtKeterangan.setRows(5);
        txtKeterangan.setBorder(null);
        jScrollPane3.setViewportView(txtKeterangan);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 580, 230, 140));

        TabelProses.setBackground(new java.awt.Color(242, 242, 242));
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

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 440, 250, 110));

        txtTransaksiDetil.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtTransaksiDetil.setBorder(null);
        txtTransaksiDetil.setEnabled(false);
        getContentPane().add(txtTransaksiDetil, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 270, 131, 20));

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        jLabel2.setText("ID Transaksi Detail");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 270, -1, -1));

        txtTanggal.setBackground(new java.awt.Color(222, 225, 229));
        txtTanggal.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtTanggal.setForeground(new java.awt.Color(228, 105, 81));
        txtTanggal.setBorder(null);
        txtTanggal.setFocusable(false);
        getContentPane().add(txtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 220, 100, -1));

        tabletgl.setForeground(new java.awt.Color(35, 25, 87));
        tabletgl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tabletgl);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 660, 680, 110));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 84.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 80, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 82.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 83.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, -1, -1));

        jLabel12.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Customer Order");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, -1, -1));

        jLabel14.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Management Order");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 72 (2).png"))); // NOI18N
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 72 (2).png"))); // NOI18N
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, -1, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 72.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 380, 150, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 72.png"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 320, 150, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 72.png"))); // NOI18N
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 350, 150, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 74.png"))); // NOI18N
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 580, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 76.png"))); // NOI18N
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 750, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 62 (1).png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 740, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Rectangle 90.png"))); // NOI18N
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 60, -1, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Vector (1).png"))); // NOI18N
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1570, 60, -1, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/transaksi/gambar/Group 81.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void txtTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        new MasterOrder().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        new MasterOrder().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
        addRow(Integer.parseInt(txtJumlah.getText()));
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
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
        clear();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        // TODO add your handling code here:
        if (txtCari.getText().equals("Cari")){
            txtCari.setText("");
        }
    }//GEN-LAST:event_txtCariFocusGained

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        // TODO add your handling code here:
        if (txtCari.getText().isBlank()){
            txtCari.setText("Cari");
        }
    }//GEN-LAST:event_txtCariFocusLost
    private void clear() {
        txtTransaksi.setText((generate_id()));
        txtNama.setText("");
        txtJumlah.setText("");
        txtTelephone.setText("");
        txtTransaksiDetil.setText((generate_id()));
        
        
        txtKeterangan.setText("");
        txtCari.setText("Cari");
        txtTotal.setText("");
        DefaultTableModel clear = (DefaultTableModel) TabelProses.getModel();
        clear.setRowCount(0);
        
    }
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
    public javax.swing.JTable Tabel;
    public javax.swing.JTable TabelKet;
    private javax.swing.JTable TabelProses;
    public javax.swing.JComboBox<String> cmbBahan;
    public javax.swing.JComboBox<String> cmbPaket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable tabletgl;
    private javax.swing.JTextField txtCari;
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
