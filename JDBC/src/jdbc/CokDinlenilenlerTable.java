package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CokDinlenilenlerTable {
    public CokDinlenilenlerTable() {
        fetchAndDisplayCokDinlenilenler();
    }

    private void fetchAndDisplayCokDinlenilenler() {
        // SQL sorgusu: Şarkı adı ve dinlenme sayısını getir
        String sql = "SELECT s.sarkiadi, d.dinlenmesayisi " +
                     "FROM cokdinlenilenler d " +
                     "JOIN sarki s ON s.dinlemeid = d.dinlemeid";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Çok Dinlenilenler Tablosu");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Tablo başlıkları
            String[] columns = {"Şarkı Adı", "Dinlenme Sayısı"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Verileri tabloya ekleme
            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getString("sarkiadi");       // Şarkı adı
                row[1] = resultSet.getInt("dinlenmesayisi");    // Dinlenme sayısı
                
                tableModel.addRow(row);
            }

            // JTable ve scroll paneli oluşturma
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Pencereyi görünür hale getirme
            frame.setVisible(true);

        } catch (SQLException e) {
            // Hata durumunda istisna detaylarını yazdır
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CokDinlenilenlerTable(); // Çok Dinlenilenler tablosunu başlat
    }
}