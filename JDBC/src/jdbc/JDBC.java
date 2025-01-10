package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBC {
    private static final String URL = "jdbc:postgresql://localhost:5432/BluePlay";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public boolean authenticateUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM kullanici WHERE kullaniciadi = ? AND sifre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Kullanıcı bulunduysa true döner
        } catch (Exception e) {
            System.out.println("Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }
}