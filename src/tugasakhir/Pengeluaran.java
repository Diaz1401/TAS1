/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasakhir;

import TugasAkhir.KoneksiKashoes;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

/**
 *
 * @author 3D_SERVICE
 */
public class Pengeluaran extends javax.swing.JFrame {

    public static Connection cn;
    public static ResultSet rs;
    public static Statement st;
    public static PreparedStatement pst;

    /**
     * Creates new form Pengeluaran
     */
    public Pengeluaran() {
        initComponents();
        datatable();
        LocalDate currentDate = LocalDate.now();
        txttanggal.setText(currentDate.toString());
        update_cmb();
    }

    public void update_cmb() {
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery("SELECT kode_biaya FROM master_biaya");
            while (rs.next()) {
                String column = rs.getString("kode_biaya");
                cmbKode.addItem(column);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }

    public void datatable() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID Pengeluaran");
        tbl.addColumn("Kode Biaya");
        tbl.addColumn("Nominal");
        tbl.addColumn("Tanggal");
        Tabel.setModel(tbl);
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();

            // Update tabel
            rs = st.executeQuery("SELECT * FROM pengeluaran");
            while (rs.next()) {
                tbl.addRow(new Object[]{
                    rs.getString("id_pengeluaran"),
                    rs.getString("kode_biaya"),
                    rs.getString("nominal"),
                    rs.getString("tanggal"),});
                Tabel.setModel(tbl);
            }
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

        txtTarif = new javax.swing.JTextField();
        Tambah = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnominal = new javax.swing.JLabel();
        txtpengeluaran = new javax.swing.JTextField();
        txtnominal1 = new javax.swing.JLabel();
        txttanggal = new javax.swing.JTextField();
        Simpan = new javax.swing.JButton();
        cmbKode = new javax.swing.JComboBox<>();
        Kembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Tambah.setText("Tambah");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });

        Hapus.setText("Hapus");
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabel);

        jLabel1.setText("ID Pengeluaran");

        jLabel2.setText("Kode Biaya");

        txtnominal.setText("Nominal");

        txtnominal1.setText("Tanggal");

        txttanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttanggalActionPerformed(evt);
            }
        });

        Simpan.setText("Simpan");
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });

        cmbKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Biaya" }));

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpengeluaran))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtnominal)
                                    .addComponent(txtnominal1))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txttanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(txtTarif, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(cmbKode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(Tambah))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Kembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Simpan))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtpengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnominal)
                            .addComponent(txtTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnominal1)
                            .addComponent(txttanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tambah))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Hapus)
                    .addComponent(Simpan)
                    .addComponent(Kembali))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        if (txtpengeluaran.getText().isEmpty()
                || txtTarif.getText().isEmpty() || txttanggal.getText().isEmpty()) {
            // Show an error message
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi");
            return;
        }
        String id_pengeluaran = txtpengeluaran.getText();
        String kode_biaya = cmbKode.getSelectedItem().toString();
        String nominal = txtTarif.getText();
        String tanggal = txttanggal.getText();

        try {
            // Connect
            String sql = "INSERT INTO pengeluaran (id_pengeluaran, kode_biaya, nominal, tanggal) VALUES (?, ?, ?, ?)";
            cn = KoneksiKashoes.koneksikashoesdB();
            pst = cn.prepareStatement(sql);

            // Set paramater
            pst.setString(1, id_pengeluaran);
            pst.setString(2, kode_biaya);
            pst.setString(3, nominal);
            pst.setString(4, tanggal);

            // Execute
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage());
        }
        // Update table
        datatable();
    }//GEN-LAST:event_TambahActionPerformed

    private void HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusActionPerformed
        int row = Tabel.getSelectedRow();

        if (row < 0) {
            // Error if row not selected
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus");
            return;
        }

        // get value from first row
        String id_pengeluaran = Tabel.getValueAt(row, 0).toString();

        try {
            String sql_del = "DELETE from pengeluaran WHERE id_pengeluaran = ?";
            cn = KoneksiKashoes.koneksikashoesdB();
            pst = cn.prepareStatement(sql_del);

            // Set parameter
            pst.setString(1, id_pengeluaran);

            // Execute
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
        }
        datatable();
    }//GEN-LAST:event_HapusActionPerformed

    private void txttanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttanggalActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txttanggalActionPerformed

    private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimpanActionPerformed
        // Get the selected row index
        int row = Tabel.getSelectedRow();
        if (row < 0) {
            // No row is selected, show an error message
            JOptionPane.showMessageDialog(null, "Pilih data yang akan disimpan");
            return;
        }

        // Get the value of the column in the selected row
        String id_pengeluaran = Tabel.getValueAt(row, 0).toString();
        String kode_biaya = Tabel.getValueAt(row, 1).toString();
        String nominal = Tabel.getValueAt(row, 2).toString();
        String tanggal = Tabel.getValueAt(row, 3).toString();

        try {
            // Connect
            String sql = "UPDATE pengeluaran SET kode_biaya = ?, nominal = ?, tanggal = ? WHERE id_pengeluaran = ?";
            cn = KoneksiKashoes.koneksikashoesdB();
            pst = cn.prepareStatement(sql);

            // Set the parameter values
            pst.setString(1, kode_biaya);
            pst.setString(2, nominal);
            pst.setString(3, tanggal);
            pst.setString(4, id_pengeluaran);

            // Execute the statement
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
        }
        // Update table
        datatable();
    }//GEN-LAST:event_SimpanActionPerformed

    private void KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KembaliActionPerformed
        new Order().setVisible(true);
        dispose();
    }//GEN-LAST:event_KembaliActionPerformed

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
            java.util.logging.Logger.getLogger(Pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pengeluaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Hapus;
    private javax.swing.JButton Kembali;
    private javax.swing.JButton Simpan;
    private javax.swing.JTable Tabel;
    private javax.swing.JButton Tambah;
    private javax.swing.JComboBox<String> cmbKode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTarif;
    private javax.swing.JLabel txtnominal;
    private javax.swing.JLabel txtnominal1;
    private javax.swing.JTextField txtpengeluaran;
    private javax.swing.JTextField txttanggal;
    // End of variables declaration//GEN-END:variables
}
