package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AramaGecmisiTable {
    public AramaGecmisiTable(String username) {
        fetchAndDisplayAramaGecmisi(username);
    }

    private void fetchAndDisplayAramaGecmisi(String username) {
        String sql = "SELECT ara.aramaid,ara.aramaterimi " +
                     "FROM aramagecmisi ara JOIN kullanici k ON k.aramaid = ara.aramaid " +
                     "WHERE k.kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username); // Kullanıcı adı ekleniyor
            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Arama Geçmişi");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Arama ID", "Arama Terimi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getInt("aramaid");
                row[1] = resultSet.getString("aramaterimi");
               
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