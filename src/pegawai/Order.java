/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pegawai;

import java.sql.*;
import TugasAkhir.KoneksiKashoes;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tugasakhir.Login;

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
        DefaultTableModel tbl = new DefaultTableModel(), tbldet = new DefaultTableModel();
        tbl.addColumn("ID Transaksi");
        tbl.addColumn("Nama");
        tbl.addColumn("Telephone");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        tbldet.addColumn("ID Transaksi detil");
        tbldet.addColumn("ID transaksi");
        tbldet.addColumn("ID_paket");
        tbldet.addColumn("Bahan");
        tbldet.addColumn("Keterangan");
        tbldet.addColumn("Tanggal");
        Tabel.setModel(tbl);
        TabelKet.setModel(tbldet);
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
            rs = st.executeQuery("SELECT id_transaksidetil, id_transaksi, id_paket, bahan, keterangan, tanggal FROM transaksi_detil");
            while (rs.next()) {
                tbldet.addRow(new Object[]{
                    rs.getString("id_transaksidetil"),
                    rs.getString("id_transaksi"),
                    rs.getString("id_paket"),
                    rs.getString("bahan"),
                    rs.getString("keterangan"),
                    rs.getString("tanggal")
                });
                TabelKet.setModel(tbldet);
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
        txtTotal.setText(String.valueOf(tarif + rego));
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
                if (rs.next()) {
                    paket = rs.getString("paket");
                }
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

        jLabel25 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbBahan = new javax.swing.JComboBox<>();
        txtTransaksi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtKeterangan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtTelephone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        cmbPaket = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelKet = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        TabelProses = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtTransaksiDetil = new javax.swing.JTextField();
        txtKode = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        txtBahan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(217, 217, 217));
        jLabel25.setText("Kode");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 720, -1, 30));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(217, 217, 217));
        jLabel1.setText("ID Transaksi");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, -1, 30));

        cmbBahan.setForeground(new java.awt.Color(35, 25, 87));
        cmbBahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih bahan" }));
        cmbBahan.setToolTipText("");
        cmbBahan.setPreferredSize(new java.awt.Dimension(89, 22));
        cmbBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBahanActionPerformed(evt);
            }
        });
        getContentPane().add(cmbBahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 620, -1, -1));

        txtTransaksi.setBackground(new java.awt.Color(222, 225, 229));
        txtTransaksi.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtTransaksi.setForeground(new java.awt.Color(35, 25, 87));
        txtTransaksi.setBorder(null);
        txtTransaksi.setEnabled(false);
        txtTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(txtTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, 210, 34));

        jLabel6.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(217, 217, 217));
        jLabel6.setText("Total Harga");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 880, -1, -1));

        txtTotal.setBackground(new java.awt.Color(92, 48, 85));
        txtTotal.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(125, 223, 0));
        txtTotal.setBorder(null);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 910, 100, 30));

        jLabel4.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(217, 217, 217));
        jLabel4.setText("Nama");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 473, -1, 30));

        txtKeterangan.setBackground(new java.awt.Color(217, 217, 217));
        txtKeterangan.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        txtKeterangan.setBorder(null);
        txtKeterangan.setCaretColor(new java.awt.Color(35, 25, 87));
        getContentPane().add(txtKeterangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 780, 410, 80));

        jLabel7.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(217, 217, 217));
        jLabel7.setText("No. Telephone");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 580, -1, -1));

        txtNama.setBackground(new java.awt.Color(217, 217, 217));
        txtNama.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtNama.setForeground(new java.awt.Color(35, 25, 87));
        txtNama.setBorder(null);
        txtNama.setPreferredSize(new java.awt.Dimension(64, 34));
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        getContentPane().add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 470, 200, -1));

        txtTelephone.setBackground(new java.awt.Color(217, 217, 217));
        txtTelephone.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtTelephone.setForeground(new java.awt.Color(35, 25, 87));
        txtTelephone.setBorder(null);
        txtTelephone.setCaretColor(new java.awt.Color(35, 25, 87));
        txtTelephone.setPreferredSize(new java.awt.Dimension(64, 34));
        getContentPane().add(txtTelephone, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 570, 200, -1));

        jLabel9.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(217, 217, 217));
        jLabel9.setText("Jumlah");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, -1, -1));

        txtJumlah.setBackground(new java.awt.Color(217, 217, 217));
        txtJumlah.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtJumlah.setForeground(new java.awt.Color(35, 25, 87));
        txtJumlah.setBorder(null);
        txtJumlah.setCaretColor(new java.awt.Color(35, 25, 87));
        txtJumlah.setPreferredSize(new java.awt.Dimension(64, 34));
        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        getContentPane().add(txtJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 140, -1));

        cmbPaket.setForeground(new java.awt.Color(35, 25, 87));
        cmbPaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih paket" }));
        cmbPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaketActionPerformed(evt);
            }
        });
        getContentPane().add(cmbPaket, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 620, -1, -1));

        Tabel.setBackground(new java.awt.Color(108, 192, 0));
        Tabel.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        Tabel.setForeground(new java.awt.Color(35, 25, 87));
        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        Tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabel);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 340, 770, 200));

        TabelKet.setBackground(new java.awt.Color(108, 192, 0));
        TabelKet.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        TabelKet.setForeground(new java.awt.Color(35, 25, 87));
        TabelKet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TabelKet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelKetMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelKet);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 550, 770, 160));

        TabelProses.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        TabelProses.setForeground(new java.awt.Color(35, 25, 87));
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

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 660, 450, 100));

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(217, 217, 217));
        jLabel2.setText("ID Transaksi Detail");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, -1, 30));

        txtTransaksiDetil.setBackground(new java.awt.Color(222, 225, 229));
        txtTransaksiDetil.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtTransaksiDetil.setForeground(new java.awt.Color(35, 25, 87));
        txtTransaksiDetil.setBorder(null);
        txtTransaksiDetil.setEnabled(false);
        txtTransaksiDetil.setPreferredSize(new java.awt.Dimension(64, 34));
        getContentPane().add(txtTransaksiDetil, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, 210, -1));

        txtKode.setBackground(new java.awt.Color(108, 192, 0));
        txtKode.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtKode.setForeground(new java.awt.Color(35, 25, 87));
        txtKode.setBorder(null);
        txtKode.setEnabled(false);
        txtKode.setPreferredSize(new java.awt.Dimension(64, 22));
        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });
        getContentPane().add(txtKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 750, 120, -1));

        txtTanggal.setBackground(new java.awt.Color(58, 42, 76));
        txtTanggal.setForeground(new java.awt.Color(125, 223, 0));
        txtTanggal.setBorder(null);
        getContentPane().add(txtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 80, -1));

        txtBahan.setBackground(new java.awt.Color(108, 192, 0));
        txtBahan.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        txtBahan.setForeground(new java.awt.Color(35, 25, 87));
        txtBahan.setBorder(null);
        txtBahan.setPreferredSize(new java.awt.Dimension(64, 34));
        txtBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBahanActionPerformed(evt);
            }
        });
        getContentPane().add(txtBahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 750, 120, 22));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field tr (2).png"))); // NOI18N
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Order.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 30));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Cost.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 60, 30));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Expenditure.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 160, 30));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/bt proses.png"))); // NOI18N
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 520, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Income.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 160, -1, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Logout.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1750, 170, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field order.png"))); // NOI18N
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 570, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/bt order.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 880, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field order.png"))); // NOI18N
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 520, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field tr (2).png"))); // NOI18N
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, -1, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field order.png"))); // NOI18N
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field keterangan.png"))); // NOI18N
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 780, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/field cari.png"))); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 50, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/Vector.png"))); // NOI18N
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1710, 50, -1, 40));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/bi_save-fill.png"))); // NOI18N
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1590, 300, -1, -1));

        jLabel27.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(217, 217, 217));
        jLabel27.setText("Bahan");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 720, -1, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/order/bg order.png"))); // NOI18N
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

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        addRow(Integer.parseInt(txtJumlah.getText()));
    }//GEN-LAST:event_jLabel21MouseClicked

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

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

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
         new pegawai.MasterBiaya().setVisible(true);
        
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        new pegawai.Pengeluaran().setVisible(true);
        dispose();
       
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        new pegawai.Profit().setVisible(true);
        
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
       try{
            Connection kon = KoneksiKashoes.koneksikashoesdB();
            Statement st = kon.createStatement();
           
                String sql_up = "UPDATE transaksi SET nama='" + txtNama.getText()
                        + "',no_telp='" + txtTelephone.getText()
                        + "',jml_sepatu='" + txtJumlah.getText()
                         + "',total_bayar='" + txtTotal.getText()+"' WHERE id_transaksi='"+txtTransaksi.getText()+"'";
                st.execute(sql_up);
                JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
       
       try{
            Connection kon = KoneksiKashoes.koneksikashoesdB();
            Statement st = kon.createStatement();
           
                String sql_up = "UPDATE transaksi_detil SET id_paket='" + txtKode.getText()
                        + "',bahan='" + txtBahan.getText()
                        + "',keterangan='" + txtKeterangan.getText()
                         + "',tanggal='" + txtTanggal.getText()+"' WHERE id_transaksidetil='"+txtTransaksiDetil.getText()+"'";
                st.execute(sql_up);
                JOptionPane.showMessageDialog(null, "Data Berhasil di Update");
            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        update();
    }//GEN-LAST:event_jLabel24MouseClicked

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeActionPerformed

    private void txtBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBahanActionPerformed

    private void TabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMouseClicked
        // TODO add your handling code here:
        int bar = Tabel.getSelectedRow();
        String a = Tabel.getValueAt(bar, 0).toString();
        String b = Tabel.getValueAt(bar, 1).toString();
        String c = Tabel.getValueAt(bar, 3).toString();
        String d = Tabel.getValueAt(bar, 2).toString();
        String e = Tabel.getValueAt(bar, 4).toString();
        String f = TabelKet.getValueAt(bar, 4).toString();
        String g = TabelKet.getValueAt(bar, 2).toString();
        String h = TabelKet.getValueAt(bar, 3).toString();
        
        txtTransaksi.setText(a);
        txtNama.setText(b);
        txtJumlah.setText(c);
        txtTelephone.setText(d);
        txtTotal.setText(e);
        
        txtKeterangan.setText(f);
        txtKode.setText(g);
        txtBahan.setText(h);
        
        TabelKet.setRowSelectionInterval(bar, bar);
        TabelKet.scrollRectToVisible(TabelKet.getCellRect(bar, 0, true));
        TabelKet.getValueAt(bar, 0);
       
    }//GEN-LAST:event_TabelMouseClicked

    private void TabelKetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelKetMouseClicked
        // TODO add your handling code here:
        int bar = TabelKet.getSelectedRow();
        String a = TabelKet.getValueAt(bar, 0).toString();
        String b = TabelKet.getValueAt(bar, 1).toString();
        String c = TabelKet.getValueAt(bar, 2).toString();
        String d = TabelKet.getValueAt(bar, 3).toString();
        String e = TabelKet.getValueAt(bar, 4).toString();
        String f = Tabel.getValueAt(bar, 0).toString();
        String g = Tabel.getValueAt(bar, 1).toString();
        String h = Tabel.getValueAt(bar, 3).toString();
        String i = Tabel.getValueAt(bar, 2).toString();
        String j = Tabel.getValueAt(bar, 4).toString();
        
        txtTransaksiDetil.setText(a);
        txtTransaksi.setText(b);
        txtKode.setText(c);
        txtBahan.setText(d);
        txtKeterangan.setText(e);
        
        txtTransaksi.setText(f);
        txtNama.setText(g);
        txtJumlah.setText(h);
        txtTelephone.setText(i);
        txtTotal.setText(j);
        Tabel.setRowSelectionInterval(bar, bar);
        Tabel.scrollRectToVisible(Tabel.getCellRect(bar, 0, true));
    }//GEN-LAST:event_TabelKetMouseClicked

    private void clear() {
        
        txtNama.setText("");
        txtJumlah.setText("");
        txtTelephone.setText("");
        cmbPaket.setSelectedItem(null);
        cmbBahan.setSelectedItem(null);
        txtKeterangan.setText("");
      
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTextField txtBahan;
    public javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKeterangan;
    public javax.swing.JTextField txtKode;
    public javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTanggal;
    public javax.swing.JTextField txtTelephone;
    private javax.swing.JTextField txtTotal;
    public javax.swing.JTextField txtTransaksi;
    public javax.swing.JTextField txtTransaksiDetil;
    // End of variables declaration//GEN-END:variables
}
