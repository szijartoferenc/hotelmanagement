package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CustomerInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    CustomerInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Header Labels
        JLabel l1 = new JLabel("Document Type");
        l1.setBounds(10, 10, 100, 20);
        add(l1);

        JLabel l2 = new JLabel("Number");
        l2.setBounds(160, 10, 100, 20);
        add(l2);

        JLabel l3 = new JLabel("Name");
        l3.setBounds(290, 10, 100, 20);
        add(l3);

        JLabel l4 = new JLabel("Gender");
        l4.setBounds(410, 10, 100, 20);
        add(l4);

        JLabel l5 = new JLabel("Country");
        l5.setBounds(540, 10, 100, 20);
        add(l5);

        JLabel l6 = new JLabel("Room Number");
        l6.setBounds(640, 10, 100, 20);
        add(l6);

        JLabel l7 = new JLabel("Check-in Time");
        l7.setBounds(760, 10, 100, 20);
        add(l7);

        JLabel l8 = new JLabel("Deposit");
        l8.setBounds(900, 10, 100, 20);
        add(l8);

        // Table with Scroll Pane
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 10, 1000, 400); // This removes the gap between header and table
        scrollPane.getViewport().setViewPosition(new Point(0, 0)); // Removes any additional padding/margin
        add(scrollPane);

        // Fetch and display customer data
        fetchCustomerData();

        // Back Button
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        // Frame Properties
        setBounds(300, 200, 1050, 600);
        setVisible(true);
    }

    private void fetchCustomerData() {
        String query = "SELECT * FROM customer";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.getTableHeader().setVisible(false); // Hide the column headers from the table

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching customer data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Reception();
    }

    public static void main(String[] args) {
        new CustomerInfo();
    }
}

