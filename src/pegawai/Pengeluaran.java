/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pegawai;

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
        setExtendedState(Pengeluaran.MAXIMIZED_BOTH);
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
        jLabel6 = new javax.swing.JLabel();
        Kembali = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTarif.setBackground(new java.awt.Color(217, 217, 217));
        txtTarif.setBorder(null);
        getContentPane().add(txtTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 500, 410, 40));

        Hapus.setText("Hapus");
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });
        getContentPane().add(Hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 740, -1, -1));

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabel);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 220, 810, 510));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Pengeluaran");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 130, -1));

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kode Biaya");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 410, -1, -1));

        txtnominal.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtnominal.setForeground(new java.awt.Color(255, 255, 255));
        txtnominal.setText("Nominal");
        getContentPane().add(txtnominal, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, -1, -1));

        txtpengeluaran.setBorder(null);
        getContentPane().add(txtpengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 410, 40));

        txtnominal1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        txtnominal1.setForeground(new java.awt.Color(255, 255, 255));
        txtnominal1.setText("Tanggal");
        getContentPane().add(txtnominal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 550, -1, -1));

        txttanggal.setBackground(new java.awt.Color(217, 217, 217));
        txttanggal.setBorder(null);
        txttanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttanggalActionPerformed(evt);
            }
        });
        getContentPane().add(txttanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 410, 40));

        Simpan.setText("Simpan");
        Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SimpanActionPerformed(evt);
            }
        });
        getContentPane().add(Simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 740, -1, -1));

        cmbKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Biaya" }));
        getContentPane().add(cmbKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 450, 40));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/expenditure/field.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, -1, -1));

        Kembali.setText("Kembali");
        Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KembaliActionPerformed(evt);
            }
        });
        getContentPane().add(Kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/expenditure/field id.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/expenditure/field.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/expenditure/bt tambah.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 640, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pegawai/gambar/expenditure/bg ex.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
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
    }//GEN-LAST:event_jLabel7MouseClicked

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
    private javax.swing.JComboBox<String> cmbKode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTarif;
    private javax.swing.JLabel txtnominal;
    private javax.swing.JLabel txtnominal1;
    private javax.swing.JTextField txtpengeluaran;
    private javax.swing.JTextField txttanggal;
    // End of variables declaration//GEN-END:variables
}
