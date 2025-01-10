package jdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ARAYUZ {
    private final JDBC dbManager;

    public ARAYUZ() {
        this.dbManager = new JDBC();
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Kullanıcı Girişi");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Kullanıcı Adı:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Şifre:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Giriş Yap");
        JLabel resultLabel = new JLabel("");

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);
        frame.add(resultLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                boolean isAuthenticated = dbManager.authenticateUser(username, password);

                if (isAuthenticated) {
                    resultLabel.setForeground(Color.GREEN);
                    resultLabel.setText("Giriş başarılı! Hoş geldiniz, " + username + "!");
                    frame.dispose();
                    new ControlPanel(username); // Kullanıcı adını gönder
                }
                else {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("Giriş başarısız! Kullanıcı adı veya şifre hatalı.");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ARAYUZ();
    }
}