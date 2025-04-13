package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Department extends JFrame implements ActionListener {
    JTable table;
    JButton back;

    Department() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Header Labels
        JLabel l1 = new JLabel("Department");
        l1.setBounds(110, 10, 100, 20);
        add(l1);

        JLabel l2 = new JLabel("Budget");
        l2.setBounds(370, 10, 100, 20);
        add(l2);

        // Table with Scroll Pane
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 500, 400);
        add(scrollPane);

        // Fetch and display department data
        fetchDepartmentData();

        // Back Button
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 500, 120, 30);
        back.addActionListener(this);
        add(back);

        // Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/eight.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500, 0, 600, 600);
        add(image);

        // Frame Properties
        setBounds(300, 200, 1050, 600);
        setVisible(true);
    }

    private void fetchDepartmentData() {
        String query = "SELECT * FROM department";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            table.getTableHeader().setVisible(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching department data: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Reception();
    }

    public static void main(String[] args) {
        new Department();
    }
}
