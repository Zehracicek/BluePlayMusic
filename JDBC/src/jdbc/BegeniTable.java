package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class BegeniTable {
    public BegeniTable() {
        fetchAndDisplayBegenis(); // Beğenileri getir ve görüntüle
    }

    private void fetchAndDisplayBegenis() {
        // SQL sorgusu: Sanatçı adı, şarkı adı ve beğeni tarihini getir
        String sql = """
                SELECT sa.sanatciadi, s.sarkiadi, b.begenitarihi
                FROM begeni b
                JOIN sarki s ON s.begeniid = b.begeniid
                JOIN kullanici k ON k.begeniid = b.begeniid
                JOIN sanatci sa ON sa.sanatciid = s.sanatciid
                """;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Beğeni Tablosu");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Tablo modeli
            String[] columns = {"Sanatçı Adı", "Şarkı Adı", "Beğeni Tarihi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("sanatciadi"),  // Sanatçı adı
                        resultSet.getString("sarkiadi"),   // Şarkı adı
                        resultSet.getDate("begenitarihi")  // Beğeni tarihi
                };
                tableModel.addRow(row);
            }

            // JTable ve JScrollPane oluşturma
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            // Başlık etiketi
            JLabel headerLabel = new JLabel("Beğeni Listesi", JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
            frame.add(headerLabel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Pencereyi görünür yap
            frame.setVisible(true);

        } catch (SQLException e) {
            // Hata mesajını göster
            JOptionPane.showMessageDialog(null, 
                "Veritabanına bağlanırken bir hata oluştu:\n" + e.getMessage(), 
                "Hata", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BegeniTable::new); // Beğeni tablosunu başlat
    }
}