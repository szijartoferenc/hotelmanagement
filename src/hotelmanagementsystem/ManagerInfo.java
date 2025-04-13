package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ManagerInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    ManagerInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Add header labels in a panel for better alignment
        JPanel headerPanel = new JPanel(new GridLayout(1, 8, 10, 10));
        headerPanel.setBounds(0, 10, 1000, 30);
        headerPanel.add(new JLabel("Name", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Age", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Gender", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Job", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Salary", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Phone", SwingConstants.CENTER));
        headerPanel.add(new JLabel("Email", SwingConstants.CENTER));
        headerPanel.add(new JLabel("SSN", SwingConstants.CENTER));
        add(headerPanel);

        // Table for displaying manager information
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 50, 1000, 400);
        add(scrollPane);

        // Fetch and display data
        fetchManagerData();

        // Back button
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        // Frame properties
        setBounds(300, 200, 1050, 600);
        setVisible(true);
    }

    private void fetchManagerData() {
        String query = "SELECT * FROM employee WHERE job = 'Manager'";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.getTableHeader().setVisible(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching manager data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Reception();
    }

    public static void main(String[] args) {
        new ManagerInfo();
    }
}
