package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeInfo extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    EmployeeInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Panel for custom column headers
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

        // Table with scroll pane
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 50, 1000, 400);
        add(scrollPane);

        // Fetch and display employee data
        fetchEmployeeData();

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

    private void fetchEmployeeData() {
        String query = "SELECT * FROM employee";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Prepare data model with just the data (no column names)
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Object[][] data = new Object[100][columnCount]; // Assuming max 100 rows for now
            int rowIndex = 0;

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowIndex][i - 1] = rs.getObject(i); // Fetch data
                }
                rowIndex++;
            }

            // Set the table model with the data (no column names)
            table.setModel(new DefaultTableModel(data, new String[columnCount])); // Empty column names

            // Hide table column headers
            table.getTableHeader().setVisible(false);  // This hides the column names

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching employee data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Reception();
    }

    public static void main(String[] args) {
        new EmployeeInfo();
    }
}
