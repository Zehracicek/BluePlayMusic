package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AbonelikTable {
    public AbonelikTable(String username) {
        fetchAndDisplayAbonelik(username);
    }

    private void fetchAndDisplayAbonelik(String username) {
        String sql = "SELECT a.aboneid, a.abonePlani, a.abonelikSuresi, a.abonelikUcreti " +
                     "FROM abonelik a JOIN kullanici k ON k.aboneid = a.aboneid " +
                     "WHERE k.kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username); // Kullanıcı adı ekleniyor
            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Abonelik Tablosu");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Abone ID", "Abone Planı", "Abonelik Süresi", "Abonelik Ücreti"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getInt("aboneid");
                row[1] = resultSet.getString("abonePlani");
                row[2] = resultSet.getString("abonelikSuresi");
                row[3] = resultSet.getInt("abonelikUcreti");
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