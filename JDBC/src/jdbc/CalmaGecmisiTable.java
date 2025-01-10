package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class CalmaGecmisiTable {
    public CalmaGecmisiTable(String username) {
        fetchAndDisplayCalmaGecmisi(username);
    }

    private void fetchAndDisplayCalmaGecmisi(String username) {
        String sql = "SELECT c.gecmisid, c.dinlenmetarihi, s.sarkiadi " +
                     "FROM calmagecmisi c " +
                     "JOIN kullanici k ON k.gecmisid = c.gecmisid " +
                     "JOIN sarki s ON s.gecmisid = c.gecmisid " +
                     "WHERE k.kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username); // Kullanıcı adı ekleniyor
            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Çalma Geçmişi");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Geçmiş ID", "Dinlenme Tarihi", "Şarkı Adı"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("gecmisid");
                row[1] = resultSet.getString("dinlenmetarihi");
                row[2] = resultSet.getString("sarkiadi");

                tableModel.addRow(row);
            }

            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}