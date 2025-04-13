package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class Pickup extends JFrame implements ActionListener {
    private JTable table;
    private JButton back, submit;
    private Choice typeofcar;

    public Pickup() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Pickup Service");
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        text.setBounds(400, 30, 200, 30);
        add(text);

        JLabel lbltypeofcar = new JLabel("Type of car");
        lbltypeofcar.setBounds(50, 100, 100, 20);
        add(lbltypeofcar);

        typeofcar = new Choice();
        typeofcar.setBounds(150, 100, 200, 25);
        add(typeofcar);

        // Load car brands into the dropdown
        loadCarBrands();

        // Automatically update table when car type changes
        typeofcar.addItemListener(e -> updateTable());

        // Table headers
        String[] headers = {"Name", "Age", "Gender", "Company", "Brand", "Availability", "Location"};
        int xPos = 30;
        for (String header : headers) {
            JLabel label = new JLabel(header);
            label.setBounds(xPos, 160, 100, 20);
            add(label);
            xPos += (header.equals("Company") ? 170 : 130); // Adjust spacing
        }

        table = new JTable();
        table.setBounds(0, 200, 1000, 300);
        add(table);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setBounds(300, 520, 120, 30);
        add(submit);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        back.setBounds(500, 520, 120, 30);
        add(back);

        setBounds(300, 200, 1000, 600);
        setVisible(true);

        updateTable(); // Load initial data
    }

    private void loadCarBrands() {
        try (Conn conn = new Conn();
             Statement stmt = conn.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT brand FROM driver")) {
            while (rs.next()) {
                typeofcar.add(rs.getString("brand"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTable() {
        String selectedBrand = typeofcar.getSelectedItem();
        String query = "SELECT * FROM driver WHERE brand = ?";
        
        try (Conn conn = new Conn();
             PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
            stmt.setString(1, selectedBrand);
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            updateTable();
        } else {
            setVisible(false);
            new Reception();
        }
    }

    public static void main(String[] args) {
        new Pickup();
    }
}
