package jdbc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class YorumTable {
    private final String username; // Kullanıcı adı

    public YorumTable(String username) {
        this.username = username;
        fetchAndDisplayYorums(); // Yorumları getir ve görüntüle
    }

    private void fetchAndDisplayYorums() {
        // SQL sorgusu: Sadece giriş yapan kullanıcıya ait yorumları getir
        String sql = """
                SELECT k.kullaniciadi, y.yorumicerigi, y.yorumtarihi
                FROM yorum y
                JOIN kullanici k ON k.yorumid = y.yorumid
                WHERE k.kullaniciadi = ?
                """;

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username); // Giriş yapan kullanıcı adı
            ResultSet resultSet = preparedStatement.executeQuery();

            // Pencere oluşturma
            JFrame frame = new JFrame("Yorum Tablosu");
            frame.setSize(700, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Tablo modeli
            String[] columns = {"Kullanıcı Adı", "Yorum İçeriği", "Yorum Tarihi"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            // Sonuçları tabloya ekleme
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("kullaniciadi"),  // Kullanıcı adı
                        resultSet.getString("yorumicerigi"), // Yorum içeriği
                        resultSet.getDate("yorumtarihi")     // Yorum tarihi
                };
                tableModel.addRow(row);
            }

            // JTable ve JScrollPane oluşturma
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            // Başlık etiketi
            JLabel headerLabel = new JLabel("Kullanıcı Yorumları", JLabel.CENTER);
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
        String username = JOptionPane.showInputDialog(null, "Kullanıcı adınızı girin:", "Giriş", JOptionPane.QUESTION_MESSAGE);
        if (username != null && !username.trim().isEmpty()) {
            SwingUtilities.invokeLater(() -> new YorumTable(username)); // Yorum tablosunu başlat
        } else {
            JOptionPane.showMessageDialog(null, "Geçerli bir kullanıcı adı giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}
