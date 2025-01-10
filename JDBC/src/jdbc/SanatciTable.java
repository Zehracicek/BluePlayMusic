package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class SanatciTable {
    public SanatciTable() {
        fetchAndDisplaySanatciTable(); 
    }

    private void fetchAndDisplaySanatciTable() {

        String sql = "select sa.sanatciadi, s.sarkiadi from sanatci sa join sarki s on s.sanatciid = sa.sanatciid" ;
                     

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Sanatçı");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Tablo modeli
            String[] columns = {"Sanatçı Adı", "Şarkı Adı"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("sanatciadi");  // Bildirim mesajını alıyoruz
                row[1] = resultSet.getString("sarkiadi");    // Bildirim tarihini alıyoruz
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