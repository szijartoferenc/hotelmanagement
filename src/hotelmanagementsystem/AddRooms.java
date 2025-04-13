package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddRooms extends JFrame implements ActionListener {
    JButton add, cancel;
    JTextField tfroom, tfprice;
    JComboBox<String> typecombo, availablecombo, cleancombo;

    AddRooms() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Add Rooms");
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        heading.setBounds(150, 20, 200, 20);
        add(heading);

        JLabel lblroomno = new JLabel("Room Number");
        lblroomno.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblroomno.setBounds(60, 80, 120, 30);
        add(lblroomno);

        tfroom = new JTextField();
        tfroom.setBounds(200, 80, 150, 30);
        add(tfroom);

        JLabel lblavailable = new JLabel("Available");
        lblavailable.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblavailable.setBounds(60, 130, 120, 30);
        add(lblavailable);

        String[] availableOptions = { "Available", "Occupied" };
        availablecombo = new JComboBox<>(availableOptions);
        availablecombo.setBounds(200, 130, 150, 30);
        availablecombo.setBackground(Color.WHITE);
        add(availablecombo);

        JLabel lblclean = new JLabel("Cleaning Status");
        lblclean.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblclean.setBounds(60, 180, 120, 30);
        add(lblclean);

        String[] cleanOptions = { "Cleaned", "Dirty" };
        cleancombo = new JComboBox<>(cleanOptions);
        cleancombo.setBounds(200, 180, 150, 30);
        cleancombo.setBackground(Color.WHITE);
        add(cleancombo);

        JLabel lblprice = new JLabel("Price");
        lblprice.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblprice.setBounds(60, 230, 120, 30);
        add(lblprice);

        tfprice = new JTextField();
        tfprice.setBounds(200, 230, 150, 30);
        add(tfprice);

        JLabel lbltype = new JLabel("Bed Type");
        lbltype.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lbltype.setBounds(60, 280, 120, 30);
        add(lbltype);

        String[] typeOptions = { "Single Bed", "Double Bed" };
        typecombo = new JComboBox<>(typeOptions);
        typecombo.setBounds(200, 280, 150, 30);
        typecombo.setBackground(Color.WHITE);
        add(typecombo);

        add = new JButton("Add Room");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.BLACK);
        add.setBounds(60, 350, 130, 30);
        add.addActionListener(this);
        add(add);

        cancel = new JButton("Cancel");
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.BLACK);
        cancel.setBounds(220, 350, 130, 30);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/twelve.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 30, 500, 300);
        add(image);

        setBounds(330, 200, 940, 470);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            addRoomToDatabase();
        } else {
            setVisible(false);
        }
    }

    private void addRoomToDatabase() {
        String roomNumber = tfroom.getText().trim();
        String availability = (String) availablecombo.getSelectedItem();
        String cleaningStatus = (String) cleancombo.getSelectedItem();
        String priceText = tfprice.getText().trim();
        String bedType = (String) typecombo.getSelectedItem();

        // Validation: Room number & price should not be empty
        if (roomNumber.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room number and price cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation: Price should be a valid number
        int price;
        try {
            price = Integer.parseInt(priceText);
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and insertion
        String checkRoomQuery = "SELECT * FROM room WHERE roomnumber = ?";
        String insertQuery = "INSERT INTO room (roomnumber, availability, cleaning_status, price, bed_type) VALUES (?, ?, ?, ?, ?)";

        try (Conn conn = new Conn();
             PreparedStatement checkStmt = conn.getConnection().prepareStatement(checkRoomQuery);
             PreparedStatement insertStmt = conn.getConnection().prepareStatement(insertQuery)) {

            // Check if room already exists
            checkStmt.setString(1, roomNumber);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Room number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //  Insert new room
            insertStmt.setString(1, roomNumber);
            insertStmt.setString(2, availability);
            insertStmt.setString(3, cleaningStatus);
            insertStmt.setInt(4, price);
            insertStmt.setString(5, bedType);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "New room added successfully!");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Room addition failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddRooms();
    }
}
