package jdbc; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class KullaniciTable {
    public KullaniciTable(String username) {
        fetchAndDisplayKullanici(username);
    }

    private void fetchAndDisplayKullanici(String username) {
        String sql = "SELECT k.kullaniciadi, k.eposta, k.takipedilenler FROM kullanici k WHERE k.kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            JFrame frame = new JFrame("Kullanıcı Tablosu");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            frame.getContentPane().setBackground(new Color(91, 140, 164));

            JLabel headerLabel = new JLabel("Kullanıcı Bilgileri", JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
            headerLabel.setForeground(Color.WHITE);
            frame.add(headerLabel, BorderLayout.NORTH);

            String[] columns = {"Kullanıcı Adı", "Kullanıcı E-posta", "Takip Edilenler"};
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            String currentEmail = "";
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getString("kullaniciadi");
                row[1] = resultSet.getString("eposta");
                row[2] = resultSet.getString("takipedilenler");
                tableModel.addRow(row);
                currentEmail = resultSet.getString("eposta");
            }

            JTable table = new JTable(tableModel);
            table.setFont(new Font("SansSerif", Font.PLAIN, 16));
            table.setRowHeight(30);
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
            table.getTableHeader().setBackground(new Color(227, 86, 102));
            table.getTableHeader().setForeground(Color.WHITE);
            table.setBackground(Color.WHITE);
            table.setForeground(Color.BLACK);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel updatePanel = new JPanel();
            updatePanel.setLayout(new FlowLayout());
            updatePanel.setBackground(new Color(245, 163, 127));

            JLabel emailLabel = new JLabel("Yeni E-posta: ");
            emailLabel.setForeground(Color.WHITE);
            emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

            JTextField emailField = new JTextField(20);
            emailField.setText(currentEmail);
            emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
            emailField.setBackground(new Color(250, 207, 177));
            emailField.setForeground(Color.BLACK);
            emailField.setCaretColor(Color.WHITE);

            JButton updateButton = new JButton("Güncelle");
            updateButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            updateButton.setBackground(new Color(173, 201, 210));
            updateButton.setForeground(Color.BLACK);
            updateButton.setFocusPainted(false);
            updateButton.setBorder(BorderFactory.createLineBorder(new Color(227, 86, 102)));

            updatePanel.add(emailLabel);
            updatePanel.add(emailField);
            updatePanel.add(updateButton);

            frame.add(updatePanel, BorderLayout.SOUTH);

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newEmail = emailField.getText();
                    if (!newEmail.isEmpty() && newEmail.contains("@")) {
                        updateEmail(username, newEmail);
                        JOptionPane.showMessageDialog(frame, "E-posta başarıyla güncellendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Geçerli bir e-posta adresi girin!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateEmail(String username, String newEmail) {
        String updateSql = "UPDATE kullanici SET eposta = ? WHERE kullaniciadi = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/BluePlay", "postgres", "1234");
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("E-posta güncellendi.");
            }

        } catch (SQLException e) {
            System.err.println("E-posta güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
