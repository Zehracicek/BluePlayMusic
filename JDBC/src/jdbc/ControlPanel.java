package jdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {
    private final String username; // Kullanıcı adı

    public ControlPanel(String username) {
        this.username = username;
        initializeUI();
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Kontrol Paneli");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttonLabels = {
            "Abonelik", "Albüm", "Arama Geçmişi", "Beğeni",
            "Bildirim", "Çalma Geçmişi", "Çok Dinlenenler", "Etkinlik",
            "Kullanıcı", "Müzik Türü", "Önerilen Şarkılar", "Playlist",
            "Podcast", "Popüler Şarkılar", "Prodüktör", "Radyo",
            "Sanatçı", "Şarkı", "Yayıncı", "Yorum"
        };

        // Butonları ekleyip, her bir butona tıklandığında yapılacak işlemleri belirliyoruz
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Kullanıcı adıyla ilgili işlemleri yapan sınıfları çağırıyoruz
                    if (label.equals("Abonelik"))
                        new AbonelikTable(username); 
                    
                    else if (label.equals("Albüm")) 
                        new AlbumTable(username); 
                    
                    else if (label.equals("Arama Geçmişi")) 
                        new AramaGecmisiTable(username); 
                    
                    else if (label.equals("Çok Dinlenenler")) 
                        new CokDinlenilenlerTable();
                    
                    else if (label.equals("Bildirim")) 
                        new BildirimTable(); 
                    
                    else if (label.equals("Beğeni")) 
                        new BegeniTable();
                    
                    else if (label.equals("Müzik Türü")) 
                        new MuzukTuruTable(); 
                    
                    else if(label.equals("Önerilen Şarkılar"))
                        new OnerilenSarkilarTable();
                    
                    else if(label.equals("Podcast"))
                        new PodcastTable();
                    
                    else if(label.equals("Etkinlik"))
                        new EtkinlikTable(username);
                    
                    else if(label.equals("Kullanıcı"))
                        new KullaniciTable(username);
                    
                    else if(label.equals("Playlist"))
                        new PlaylistTable(username);
                    
                    else if(label.equals("Çalma Geçmişi"))
                        new CalmaGecmisiTable(username);
                    
                    else if(label.equals("Radyo"))
                        new RadyoTable();
                    
                    else if(label.equals("Sanatçı"))
                        new SanatciTable();
                    
                    else if(label.equals("Yayıncı"))
                        new YayinciTable();
                    
                    else if(label.equals("Prodüktör"))
                        new ProduktorTable();
                    
                    else if(label.equals("Şarkı"))
                        new SarkiTable();
                    
                    else if(label.equals("Yorum"))
                        new YorumTable(username);
                    
                    else if(label.equals("Popüler Şarkılar"))
                        new PopulerSarkiTable();
                    else {
                        // Diğer butonlar için basit bir mesaj gösterimi
                    }
                }
            });
            frame.add(button);
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog(null, "Kullanıcı adınızı girin:", "Giriş", JOptionPane.QUESTION_MESSAGE);
        if (username != null && !username.trim().isEmpty()) {
            new ControlPanel(username); // Kullanıcı adı ile kontrol panelini başlat
        } else {
            JOptionPane.showMessageDialog(null, "Geçerli bir kullanıcı adı giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}