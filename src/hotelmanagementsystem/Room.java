package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Room extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    Room() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Load image safely
        ImageIcon i1 = null;
        try {
            i1 = new ImageIcon(getClass().getResource("/icons/eight.jpg"));
            Image i2 = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
            i1 = new ImageIcon(i2);
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
        }

        JLabel image = new JLabel(i1);
        image.setBounds(500, 0, 600, 600);
        add(image);

        // Create a panel for labels using GridLayout
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBounds(0, 10, 500, 30);
        panel.add(new JLabel("Room Number"));
        panel.add(new JLabel("Availability"));
        panel.add(new JLabel("Status"));
        panel.add(new JLabel("Price"));
        panel.add(new JLabel("Bed Type"));
        add(panel);

        // Create and add table
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 50, 500, 400);
        add(scrollPane);

        // Fetch and display data from the database
        fetchRoomData();

        // Back button
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        // Set frame properties
        setBounds(300, 200, 1050, 600);
        setVisible(true);
    }

    private void fetchRoomData() {
        String query = "SELECT * FROM room";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            table.getTableHeader().setVisible(false);
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching room data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Reception();
    }

    public static void main(String[] args) {
        new Room();
    }
}
