package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PlaylistTable {
    public PlaylistTable(String username) {
        fetchAndDisplayPlaylist(username);
    }

    private void fetchAndDisplayPlaylist(String username) {
        String sql = "SELECT p.playlistadi, p.olusturmatarihi, s.sarkiadi " +
                     "FROM playlist p " +
                     "JOIN kullanici k ON k.playlistid = p.playlistid " +
                     "JOIN sarki s ON s.playlistid = p.playlistid " +
                     "WHERE k.kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username); // Kullanıcı adı ekleniyor
            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Playlist");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Playlist Adı", "Oluşturma Tarihi", "Şarkı Adı"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getString("playlistadi");
                row[1] = resultSet.getDate("olusturmatarihi");
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