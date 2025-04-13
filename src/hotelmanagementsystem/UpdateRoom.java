package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class UpdateRoom extends JFrame implements ActionListener {
    
    Choice ccustomer;
    JTextField tfroom, tfavailability, tfstatus;
    JButton check, update, back;
    
    UpdateRoom() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Update Room Status");
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
        text.setBounds(30, 20, 250, 30);
        text.setForeground(Color.BLUE);
        add(text);

        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 20);
        add(lblid);

        ccustomer = new Choice();
        ccustomer.setBounds(200, 80, 150, 25);
        add(ccustomer);
        
        loadCustomerIDs();

        addLabelAndTextField("Room Number", 130, tfroom = new JTextField());
        addLabelAndTextField("Availability", 180, tfavailability = new JTextField());
        addLabelAndTextField("Cleaning Status", 230, tfstatus = new JTextField());

        check = createButton("Check", 30, 300, this);
        update = createButton("Update", 150, 300, this);
        back = createButton("Back", 270, 300, this);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/seven.jpg"));
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(400, 50, 500, 300);
        add(image);

        setBounds(300, 200, 980, 450);
        setVisible(true);
    }
    
    private void loadCustomerIDs() {
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT number FROM customer")) {
            while (rs.next()) {
                ccustomer.add(rs.getString("number"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading customer IDs!");
            e.printStackTrace();
        }
    }

    private void addLabelAndTextField(String text, int y, JTextField field) {
        JLabel label = new JLabel(text);
        label.setBounds(30, y, 150, 20);
        add(label);

        field.setBounds(200, y, 150, 25);
        add(field);
    }

    private JButton createButton(String text, int x, int y, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBounds(x, y, 100, 30);
        button.addActionListener(listener);
        add(button);
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == check) {
            fetchRoomData();
        } else if (ae.getSource() == update) {
            updateRoomStatus();
        } else {
            setVisible(false);
            new Reception();
        }
    }

    private void fetchRoomData() {
        String id = ccustomer.getSelectedItem();
        String query = "SELECT room FROM customer WHERE number = ?";
        
        try (Conn c = new Conn();
             PreparedStatement pstmt = c.getConnection().prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                tfroom.setText(rs.getString("room"));
                loadRoomDetails(tfroom.getText());
            } else {
                JOptionPane.showMessageDialog(null, "No room found for this customer.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching room data: " + e.getMessage());
        }
    }
    
    private void loadRoomDetails(String roomNumber) {
        String query = "SELECT availability, cleaning_status FROM room WHERE roomnumber = ?";
        
        try (Conn c = new Conn();
             PreparedStatement pstmt = c.getConnection().prepareStatement(query)) {
            pstmt.setString(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                tfavailability.setText(rs.getString("availability"));
                tfstatus.setText(rs.getString("cleaning_status"));
            } else {
                JOptionPane.showMessageDialog(null, "Room details not found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching room details: " + e.getMessage());
        }
    }

    private void updateRoomStatus() {
        String room = tfroom.getText().trim();
        String available = tfavailability.getText().trim();
        String status = tfstatus.getText().trim();

        if (room.isEmpty() || available.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!");
            return;
        }

        String query = "UPDATE room SET availability = ?, cleaning_status = ? WHERE roomnumber = ?";
        
        try (Conn c = new Conn();
             PreparedStatement pstmt = c.getConnection().prepareStatement(query)) {
            pstmt.setString(1, available);
            pstmt.setString(2, status);
            pstmt.setString(3, room);
            
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Successfully!");
                setVisible(false);
                new Reception();
            } else {
                JOptionPane.showMessageDialog(null, "Room update failed! Check room number.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UpdateRoom();
    }
}
