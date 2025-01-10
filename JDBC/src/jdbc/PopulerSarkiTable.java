package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PopulerSarkiTable {
    public PopulerSarkiTable() {
        fetchAndDisplayPopulerSarkilar(); // Popüler şarkıları getir ve görüntüle
    }

    private void fetchAndDisplayPopulerSarkilar() {
        // SQL sorgusu: Popüler şarkı adlarını ve sanatçı adlarını çekiyoruz
        String sql = "SELECT s.sanatciadi, p.populersarkiadi FROM sanatci s JOIN populersarkilar p ON s.populersarkiid = p.populersarkiid";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Popüler Şarkılar");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Tablo modeli
            String[] columns = {"Sanatçı Adı", "Popüler Şarkılar"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("sanatciadi"), // Sanatçı adı
                        resultSet.getString("populersarkiadi") // Şarkı adı
                };
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PopulerSarkiTable::new); // Popüler Şarkılar tablosunu başlat
    }
}
