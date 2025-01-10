package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PodcastTable {
    public PodcastTable() {
        fetchAndDisplayPodcastTable(); // Bildirimleri getir ve görüntüle
    }

    private void fetchAndDisplayPodcastTable() {
        // SQL sorgusu: Kullanıcı ve bildirim tablosunu birleştirerek bildirim mesajları ve tarihlerini çekiyoruz
        String sql = "select podcastadi, bolumsayisi, konuklar, yayintarihi from podcast" ;
                     

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Podcast");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Tablo modeli
            String[] columns = {"Podcast Adı", "Bölüm Sayısı","Konuklar","Yayın Tarihi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("podcastadi");  // Bildirim mesajını alıyoruz
                row[1] = resultSet.getString("bolumsayisi");    // Bildirim tarihini alıyoruz
                row[2] = resultSet.getString("konuklar"); 
                row[3] = resultSet.getString("yayintarihi");
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