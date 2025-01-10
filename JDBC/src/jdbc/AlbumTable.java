package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AlbumTable {
    public AlbumTable(String username) {
        fetchAndDisplayAlbum(username);
    }

    private void fetchAndDisplayAlbum(String username) {
        String sql = "SELECT albumid, albumadi, cikistarihi FROM album"; // Parametreli sorguya gerek yok

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(); // Kullanıcı adı eklenmesine gerek yok

            JFrame frame = new JFrame("Albüm Tablosu");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Albüm ID", "Albüm Adı", "Çıkış Tarihi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("albumid");
                row[1] = resultSet.getString("albumadi");
                row[2] = resultSet.getDate("cikistarihi");
                tableModel.addRow(row);
            }

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