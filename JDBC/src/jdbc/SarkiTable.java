package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class SarkiTable {
    public SarkiTable() {
        fetchAndDisplaySarkiTable(); 
    }

    private void fetchAndDisplaySarkiTable() {

        String sql = "select s.sarkiadi, s.sure, s.yayintarihi, s.dinlemesayisi, t.turadi from sarki s  join muzikturu t on t.turid = s.turid" ;
                     

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Sarki");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Tablo modeli
            String[] columns = {"Şarkı Adı", "Süre", "Yayın Tarihi", "Tür"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("sarkiadi");  // Bildirim mesajını alıyoruz
                row[1] = resultSet.getTime("sure");
                row[2] = resultSet.getDate("yayintarihi");    // Bildirim tarihini alıyoruz
                tableModel.addRow(row);
            }

            // JTable'ı ve scroll paneli oluşturma
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Pencereyi göster
            frame.setVisible(true);

        } catch (SQLException e) {
            // Hata durumunda kullanıcıya bilgi veriyoruz
            JOptionPane.showMessageDialog(null, "Hata: " + e.getMessage(), "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }
}