/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasakhir;

import TugasAkhir.KoneksiKashoes;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class MasterOrder extends javax.swing.JFrame {

    public static Connection cn;
    public static ResultSet rs;
    public static Statement st;
    public static PreparedStatement pst;

    /**
     * Creates new form MasterOrder
     */
    public MasterOrder() {
        initComponents();
        txtTransaksi.setText(generate_id());
        txtTransaksiDetil.setText(generate_idDet());
        table();

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
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelDet = new javax.swing.JTable();
        btsimpan = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        bttambah = new javax.swing.JButton();
        txtTransaksi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelephone = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTransaksiDetil = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtBahan = new javax.swing.JTextField();
        Bersih = new javax.swing.JButton();
        Kembali = new javax.swing.JButton();

        jLabel1.setText("ID Transaksi");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        TabelDet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TabelDet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelDetMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelDet);

        btsimpan.setText("Simpan");
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        bthapus.setText("Hapus");
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });

        bttambah.setText("Tambah");
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        txtTransaksi.setEnabled(false);
        txtTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiActionPerformed(evt);
            }
        });

        jLabel4.setText("Nama");

        jLabel9.setText("Jumlah");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jLabel7.setText("No. Telephone");

        jLabel2.setText("ID Transaksi");

        txtTransaksiDetil.setEnabled(false);
        txtTransaksiDetil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransaksiDetilActionPerformed(evt);
            }
        });

        jLabel3.setText("ID Transaksi Detail");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane3.setViewportView(txtKeterangan);

        jLabel8.setText("Keterangan");

        txtKode.setEnabled(false);
        txtKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeActionPerformed(evt);
            }
        });

        jLabel5.setText("Kode Paket");

        jLabel6.setText("Bahan");

        jLabel11.setText("Total Harga");

        txtBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBahanActionPerformed(evt);
            }
        });

        Bersih.setText("Bersih");
        Bersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BersihActionPerformed(evt);
            }
        });

        Kembali.setText("Kembali");
        Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bthapus))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bttambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bersih))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(txtTransaksiDetil, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelephone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Kembali))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btsimpan)
                    .addComponent(bthapus)
                    .addComponent(bttambah)
                    .addComponent(Bersih))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTransaksiDetil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtBahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Kembali))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // Get the selected row index
        int row = Tabel.getSelectedRow();
        int rowdet = TabelDet.getSelectedRow();
        if (row < 0 && rowdet < 0) {
            // No row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah");
            return;
        }
        try {
            if (row >= 0) {
                // tabel transaksi
                String id_transaksi_pk = Tabel.getValueAt(row, 0).toString();
                String nama = Tabel.getValueAt(row, 1).toString();
                String no_telp = Tabel.getValueAt(row, 2).toString();
                String jml_sepatu = Tabel.getValueAt(row, 3).toString();
                String total_bayar = Tabel.getValueAt(row, 4).toString();
                // Connect
                cn = KoneksiKashoes.koneksikashoesdB();

                // Set the parameter values
                String sql = "UPDATE transaksi SET nama = ?, no_telp = ?, jml_sepatu = ?, total_bayar = ? WHERE id_transaksi = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, nama);
                pst.setString(2, no_telp);
                pst.setString(3, jml_sepatu);
                pst.setString(4, total_bayar);
                pst.setString(5, id_transaksi_pk);
                // Execute the statement
                pst.executeUpdate();
            }
            if (rowdet >= 0) {
                // tabel transaksi detil
                String id_transaksidetil = TabelDet.getValueAt(row, 0).toString();
                String id_transaksi_fk = TabelDet.getValueAt(row, 1).toString();
                String id_paket = TabelDet.getValueAt(row, 2).toString();
                String bahan = TabelDet.getValueAt(row, 3).toString();
                String keterangan = TabelDet.getValueAt(row, 4).toString();
                String tanggal = TabelDet.getValueAt(row, 5).toString();
                // Connect
                cn = KoneksiKashoes.koneksikashoesdB();

                // Set the parameter values
                String sqldet = "UPDATE transaksi_detil SET id_transaksi = ?, id_paket = ?, bahan = ?, keterangan = ?, tanggal = ? WHERE id_transaksidetil = ?";
                pst = cn.prepareStatement(sqldet);
                pst.setString(1, id_transaksi_fk);
                pst.setString(2, id_paket);
                pst.setString(3, bahan);
                pst.setString(4, keterangan);
                pst.setString(5, tanggal);
                pst.setString(6, id_transaksidetil);
                // Execute the statement
                pst.executeUpdate();

            }
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
        // Update table
        table();
        clear();
    }//GEN-LAST:event_btsimpanActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
        int row = Tabel.getSelectedRow();
        int rowdet = TabelDet.getSelectedRow();
        if (row < 0 && rowdet < 0) {
            // No row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah");
            return;
        }
        int update = JOptionPane.showOptionDialog(this, "apakah yakin hapus data?", "Hapus Data",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (update == JOptionPane.YES_OPTION) {
            try {
                if (row >= 0) {
                    // tabel transaksi
                    String id_transaksi = Tabel.getValueAt(row, 0).toString();
                    // Connect
                    cn = KoneksiKashoes.koneksikashoesdB();

                    // Set the parameter values
                    String sql = "DELETE from transaksi WHERE id_transaksi = ?";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, id_transaksi);
                    // Execute the statement
                    pst.executeUpdate();
                }
                if (rowdet >= 0) {
                    // tabel transaksi detil
                    String id_transaksidetil = TabelDet.getValueAt(row, 0).toString();
                    // Connect
                    cn = KoneksiKashoes.koneksikashoesdB();

                    // Set the parameter values
                    String sql = "DELETE from transaksi_detil WHERE id_transaksidetil = ?";
                    pst = cn.prepareStatement(sql);
                    pst.setString(1, id_transaksidetil);
                    // Execute the statement
                    pst.executeUpdate();

                }
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
            }
        }
        table();
        clear();
    }//GEN-LAST:event_bthapusActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        tambah();
        table();
        clear();
    }//GEN-LAST:event_bttambahActionPerformed

    private void TabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMouseClicked
        // TODO add your handling code here:
        int bar = Tabel.getSelectedRow();
        String a = Tabel.getValueAt(bar, 0).toString();
        String b = Tabel.getValueAt(bar, 1).toString();
        String c = Tabel.getValueAt(bar, 2).toString();
        String d = Tabel.getValueAt(bar, 3).toString();
        String e = Tabel.getValueAt(bar, 4).toString();

        txtTransaksi.setText(a);
        txtNama.setText(b);
        txtJumlah.setText(c);
        txtTelephone.setText(d);
        txtTotal.setText(e);
        TabelDet.setRowSelectionInterval(bar, bar);
        TabelDet.scrollRectToVisible(TabelDet.getCellRect(bar, 0, true));
    }//GEN-LAST:event_TabelMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiActionPerformed

    private void txtTransaksiDetilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransaksiDetilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransaksiDetilActionPerformed

    private void TabelDetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelDetMouseClicked
        // TODO add your handling code here:
        int bar = TabelDet.getSelectedRow();
        String a = TabelDet.getValueAt(bar, 0).toString();
        String b = TabelDet.getValueAt(bar, 1).toString();
        String c = TabelDet.getValueAt(bar, 2).toString();
        String d = TabelDet.getValueAt(bar, 3).toString();
        String e = TabelDet.getValueAt(bar, 4).toString();

        txtTransaksiDetil.setText(a);
        txtTransaksi.setText(b);
        txtKode.setText(c);
        txtBahan.setText(d);
        txtKeterangan.setText(e);
        Tabel.setRowSelectionInterval(bar, bar);
        Tabel.scrollRectToVisible(Tabel.getCellRect(bar, 0, true));
    }//GEN-LAST:event_TabelDetMouseClicked

    private void txtKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeActionPerformed

    private void txtBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBahanActionPerformed

    private void BersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BersihActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_BersihActionPerformed

    private void KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KembaliActionPerformed
        new Order().setVisible(true);
        dispose();
    }//GEN-LAST:event_KembaliActionPerformed

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
        String iddet = "TRD" + String.valueOf(row + 1);

        return iddet;
    }

    private void tambah() {
        try {

            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();

            pst = cn.prepareStatement("INSERT INTO transaksi (id_transaksi,nama,no_telp,jml_sepatu,total_bayar) VALUES(?,?,?,?,?)");

            pst.setString(1, txtTransaksi.getText());
            pst.setString(2, txtNama.getText());
            pst.setString(3, txtJumlah.getText());
            pst.setString(4, txtTelephone.getText());
            pst.setString(5, txtTotal.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Masukan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data: " + e.getMessage());
        }
    }

    private void table() {
        DefaultTableModel tbl = new DefaultTableModel(), tblDet = new DefaultTableModel();
        tbl.addColumn("ID Transaksi");
        tbl.addColumn("Nama");
        tbl.addColumn("Telephone");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total Harga");
        tblDet.addColumn("ID Transaksi Detil");
        tblDet.addColumn("ID Transaksi");
        tblDet.addColumn("ID Paket");
        tblDet.addColumn("Bahan");
        tblDet.addColumn("Keterangan");
        tblDet.addColumn("Tanggal");

        String sql_data = "SELECT * FROM transaksi ORDER BY id_transaksi ASC";

        String sql_dataDet = "SELECT * FROM transaksi_detil ORDER BY id_transaksidetil ASC";

        //tabel transaksi
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery(sql_data);
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("nama"),
                    rs.getString("no_telp"),
                    rs.getString("jml_sepatu"),
                    rs.getString("total_bayar")
                });
            }
            Tabel.setModel(tbl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        //tabel transaksi detil
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery(sql_dataDet);
            while (rs.next()) {
                tblDet.addRow(new Object[]{
                    rs.getString("id_transaksidetil"),
                    rs.getString("id_transaksi"),
                    rs.getString("id_paket"),
                    rs.getString("bahan"),
                    rs.getString("keterangan"),
                    rs.getString("tanggal")
                });
            }

            TabelDet.setModel(tblDet);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void clear() {
        txtTransaksi.setText("");
        txtNama.setText("");
        txtJumlah.setText("");
        txtTelephone.setText("");
        txtTransaksiDetil.setText("");
        txtTransaksi.setText("");
        txtKode.setText("");
        txtBahan.setText("");
        txtKeterangan.setText("");
        table();
    }

//     private void cari() {
//         DefaultTableModel model = new DefaultTableModel();
//         int row = model.getRowCount();
//         for (int c = 0; c < row; c++) {
//             model.removeRow(0);
//         }
//         
//         String cari = txtcari.getText();
//    String query = "SELECT * FROM tbpertama WHERE nim LIKE '%" + cari + "%' OR nama LIKE '%" + cari + "%'";
//
//    try {
//        Connection connect = coneksi.koneksidB();
//        Statement sttmnt = connect.createStatement();
//        ResultSet rslt = sttmnt.executeQuery(query);
//
//        while (rslt.next()) {
//            String d1 = rslt.getString("nim");
//            String d2 = rslt.getString("nama");
//            String d3 = rslt.getString("alamat");
//            String d4 = rslt.getString("tgl_lahir");
//            String d5 = rslt.getString("jmlh_saudara");
//            String d6 = rslt.getString("ipk");
//
//            String a = rslt.getString("nim");
//            String b = rslt.getString("nama");
//            String c = rslt.getString("alamat");
//            String d = rslt.getString("tgl_lahir");
//            String e = rslt.getString("jmlh_saudara");
//            String f = rslt.getString("ipk");
//
//            txtnim.setText(a);
//            txtnama.setText(b);
//            txtalamat.setText(c);
//            txttgl.setText(d);
//            txtsau.setText(e);
//            txtipk.setText(f);
//
//            String[] data = {d1, d2, d3, d4, d5, d6};
//            model.addRow(data);
//        }
//    } catch (Exception e) {
//        System.out.println(e);
//    }
//    }

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
            java.util.logging.Logger.getLogger(MasterOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton Bersih;
    private javax.swing.JButton Kembali;
    public javax.swing.JTable Tabel;
    private javax.swing.JTable TabelDet;
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTextField txtBahan;
    public javax.swing.JTextField txtJumlah;
    public javax.swing.JTextArea txtKeterangan;
    public javax.swing.JTextField txtKode;
    public javax.swing.JTextField txtNama;
    public javax.swing.JTextField txtTelephone;
    private javax.swing.JTextField txtTotal;
    public javax.swing.JTextField txtTransaksi;
    public javax.swing.JTextField txtTransaksiDetil;
    // End of variables declaration//GEN-END:variables
}
