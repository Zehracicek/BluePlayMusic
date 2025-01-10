package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class EtkinlikTable {
    public EtkinlikTable(String username) {
        fetchAndDisplayEtkinlik(username);
    }

    private void fetchAndDisplayEtkinlik(String username) {
        // Etkinlik verilerini almak için SQL sorgusu
        String sql = "SELECT etkinlikadi, etkinliktarihi FROM etkinlik"; // Parametreli sorguya gerek yok

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(); // Kullanıcı adı parametreli sorgu eklenmesine gerek yok

            // Etkinlik penceresi oluşturuluyor
            JFrame frame = new JFrame("Etkinlik Tablosu");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Tablo başlıkları
            String[] columns = {"Etkinlik Adı", "Etkinlik Tarihi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getString("etkinlikadi");
                row[1] = resultSet.getDate("etkinliktarihi");
                tableModel.addRow(row);
            }

            // Tabloyu oluşturup ekleme
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
