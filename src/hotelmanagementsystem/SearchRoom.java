package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class SearchRoom extends JFrame implements ActionListener {
    private JTable table;
    private JButton back, submit;
    private JComboBox<String> bedType;
    private JCheckBox available;

    SearchRoom() {
        setTitle("Search Room");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocation(300, 200);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // === Cím ===
        JLabel text = new JLabel("Search for Room", JLabel.CENTER);
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(text, BorderLayout.NORTH);

        // === Szűrőpanel ===
        JPanel filterPanel = new JPanel(new FlowLayout());

        JLabel lblbed = new JLabel("Bed Type:");
        filterPanel.add(lblbed);

        bedType = new JComboBox<>(new String[] { "Single Bed", "Double Bed" });
        filterPanel.add(bedType);

        available = new JCheckBox("Only display Available");
        available.setBackground(Color.WHITE);
        filterPanel.add(available);

        // === Táblázat görgetőpanellel ===
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 300));

        // === Gombok ===
        JPanel buttonPanel = new JPanel();

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        buttonPanel.add(submit);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        buttonPanel.add(back);

        // === Főpanel a helyes elrendezéshez ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // === Alapértelmezett táblázatadatok betöltése ===
        loadRoomData("Single Bed", false);

        setVisible(true);
    }

    private void loadRoomData(String bedType, boolean onlyAvailable) {
        String query = "SELECT * FROM room WHERE bed_type = ?";
        if (onlyAvailable) {
            query += " AND LOWER(availability) = 'available'";
        }

        try (Conn c = new Conn();
             PreparedStatement pst = c.getConnection().prepareStatement(query)) {

            pst.setString(1, bedType);
            ResultSet rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            table.getTableHeader().setVisible(false);
            table.repaint(); // Biztosítjuk, hogy a friss adatok megjelenjenek

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String selectedBedType = (String) bedType.getSelectedItem();
            boolean onlyAvailable = available.isSelected();
            loadRoomData(selectedBedType, onlyAvailable);
        } else if (ae.getSource() == back) {
            dispose(); // Ablak helyes lezárása
            new Reception();
        }
    }

    public static void main(String[] args) {
        new SearchRoom();
    }
}
