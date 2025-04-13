package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelManagementSystem extends JFrame implements ActionListener {

    private JLabel text;
    private JButton next;

    public HotelManagementSystem() {
        setBounds(100, 100, 1366, 565);
        setLayout(null);
        
        // Háttérkép hozzáadása
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/first.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1366, 565);
        add(image);
        
        // Cím szöveg
        text = new JLabel("HOTEL MANAGEMENT SYSTEM");
        text.setBounds(20, 430, 1000, 90);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("serif", Font.PLAIN, 50));
        image.add(text);
        
        // "Next" gomb
        next = new JButton("Next");
        next.setBounds(1150, 450, 150, 50);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.MAGENTA);
        next.addActionListener(this);
        next.setFont(new Font("serif", Font.PLAIN, 24));
        image.add(next);
        
        // Villogó szöveg indítása külön szálon
        startBlinkingText();
        
        setVisible(true);
    }

    // Szöveg villogtatása külön szálban
    private void startBlinkingText() {
        new Thread(() -> {
            try {
                while (true) {
                    text.setVisible(false);
                    Thread.sleep(500);
                    text.setVisible(true);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login();  // Feltételezve, hogy létezik egy `Login` osztály
    }

    public static void main(String[] args) {
        new HotelManagementSystem();
    }
}
