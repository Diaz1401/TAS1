/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasakhir;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import TugasAkhir.KoneksiKashoes;

/**
 *
 * @author Diaz
 */
public class Profit extends javax.swing.JFrame {

    public static Connection cn;
    public static ResultSet rs;
    public static Statement st;
    public static PreparedStatement pst;

    /**
     * Creates new form Profit
     */
    public Profit() {
        initComponents();
        update();
        update_pengeluaran();
    }

    public int calc(int x, int y) {
        int total = (int) Tabel.getValueAt(x, 2) - y;
        return total;
    }

    public void update_pengeluaran() {
        int i = 0;
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Tahun/Bulan");
        tbl.addColumn("Total Pengeluaran");
        tbl.addColumn("Profit");
        TabelP.setModel(tbl);
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery("SELECT YEAR(pengeluaran.tanggal) AS year, MONTH(pengeluaran.tanggal) AS month, "
                    + "SUM(pengeluaran.nominal) AS total_pengeluaran "
                    + "FROM pengeluaran "
                    + "GROUP BY YEAR(pengeluaran.tanggal), MONTH(pengeluaran.tanggal) "
                    + "ORDER BY MONTH(pengeluaran.tanggal) DESC");
            while (rs.next()) {
                int year = rs.getInt("year");
                int month = rs.getInt("month");
                int totalPengeluaran = rs.getInt("total_pengeluaran");

                String monthYear = year + "/" + month; // Combine month and year for display
                tbl.addRow(new Object[]{
                    monthYear,
                    totalPengeluaran,
                    (int) Tabel.getValueAt(i, 2) - totalPengeluaran
                });
                TabelP.setModel(tbl);
                i++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
        }
    }

    public void update() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Tahun/Bulan");
        tbl.addColumn("Total Transaksi");
        tbl.addColumn("Total Pendapatan");
        Tabel.setModel(tbl);
        try {
            cn = KoneksiKashoes.koneksikashoesdB();
            st = cn.createStatement();
            rs = st.executeQuery("SELECT YEAR(transaksi_detil.tanggal) AS year, MONTH(transaksi_detil.tanggal) AS month, "
                    + "COUNT(transaksi.total_bayar) AS total_transaksi, "
                    + "SUM(transaksi.total_bayar) AS total_pendapatan "
                    + "FROM transaksi_detil "
                    + "JOIN transaksi ON transaksi.id_transaksi = transaksi_detil.id_transaksi "
                    + "GROUP BY YEAR(transaksi_detil.tanggal), MONTH(transaksi_detil.tanggal) "
                    + "ORDER BY MONTH(transaksi_detil.tanggal) DESC");

            while (rs.next()) {
                int year = rs.getInt("year");
                int month = rs.getInt("month");
                int totalTransaksi = rs.getInt("total_transaksi");
                int totalPendapatan = rs.getInt("total_pendapatan");

                String monthYear = year + "/" + month; // Combine month and year for display
                tbl.addRow(new Object[]{
                    monthYear,
                    totalTransaksi,
                    totalPendapatan}
                );
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

        jScrollPane1 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelP = new javax.swing.JTable();
        Hitung = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        Kembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabel);

        TabelP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TabelP);

        Hitung.setText("Hitung total keuntungan");
        Hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HitungActionPerformed(evt);
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Hitung)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Kembali))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Hitung)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Kembali)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HitungActionPerformed
        int total = 0;
        int[] row = TabelP.getSelectedRows();
        for (int which : row) {
            String value = TabelP.getValueAt(which, 2).toString();
            total += Integer.parseInt(value);
        }
        txtTotal.setText(String.valueOf(total));
    }//GEN-LAST:event_HitungActionPerformed

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
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Hitung;
    private javax.swing.JButton Kembali;
    private javax.swing.JTable Tabel;
    private javax.swing.JTable TabelP;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
