package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ProduktorTable {
    public ProduktorTable() {
        fetchAndDisplayPlaylist();
    }

    private void fetchAndDisplayPlaylist() {
        // SQL sorgusunda WHERE kullanarak kullanıcı adı ile eşleşen kayıtları çekiyoruz
        String sql = "SELECT a.albumadi, p.adsoyad " +
                     "FROM album a " +
                     "JOIN produktor p ON p.produktorid = a.produktorid" ;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Produktor");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] columns = {"Albüm Adı", "Produktor Adı"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            while (resultSet.next()) {
                Object[] row = new Object[2];
                row[0] = resultSet.getString("albumadi");
                row[1] = resultSet.getString("adsoyad");

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