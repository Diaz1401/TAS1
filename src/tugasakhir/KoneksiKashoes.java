/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TugasAkhir;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class KoneksiKashoes {

    public static Connection koneksi;

    public static Connection koneksikashoesdB() {
        String url = "jdbc:mysql://localhost:3306/kashoes";
        String user = "root";
        String pass = "kashoes";
        try {
            koneksi = DriverManager.getConnection(url, user, pass);
            return koneksi;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
