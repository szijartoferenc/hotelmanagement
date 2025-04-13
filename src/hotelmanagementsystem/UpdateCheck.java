package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class UpdateCheck extends JFrame implements ActionListener {
    
    Choice ccustomer;
    JTextField tfroom, tfname, tfcheckin, tfpaid, tfpending;
    JButton check, update, back;
    
    UpdateCheck() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Update Status");
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        text.setBounds(90, 20, 200, 30);
        text.setForeground(Color.BLUE);
        add(text);

        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 20);
        add(lblid);

        ccustomer = new Choice();
        ccustomer.setBounds(200, 80, 150, 25);
        add(ccustomer);

        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT number FROM customer")) {
            
            while (rs.next()) {
                ccustomer.add(rs.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addLabelAndTextField("Room Number", 120, tfroom = new JTextField());
        addLabelAndTextField("Name", 160, tfname = new JTextField());
        addLabelAndTextField("Check-in", 200, tfcheckin = new JTextField());
        addLabelAndTextField("Amount Paid", 240, tfpaid = new JTextField());
        addLabelAndTextField("Pending Amount", 280, tfpending = new JTextField());

        check = createButton("Check", 30, 340, this);
        update = createButton("Update", 150, 340, this);
        back = createButton("Back", 270, 340, this);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/nine.png"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 50, 500, 300);
        add(image);

        setBounds(300, 200, 980, 500);
        setVisible(true);
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
            fetchCustomerData();
        } else if (ae.getSource() == update) {
            updateCustomerData();
        } else {
            setVisible(false);
            new Reception();
        }
    }

    private void fetchCustomerData() {
        String id = ccustomer.getSelectedItem();
        String query = "SELECT * FROM customer WHERE number = ?";
        
        try (Conn c = new Conn();
             PreparedStatement pstmt = c.getConnection().prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tfroom.setText(rs.getString("room"));
                tfname.setText(rs.getString("name"));
                tfcheckin.setText(rs.getString("checkintime"));
                tfpaid.setText(rs.getString("deposit"));
            }
            
            String roomQuery = "SELECT price FROM room WHERE roomnumber = ?";
            try (PreparedStatement pstmt2 = c.getConnection().prepareStatement(roomQuery)) {
                pstmt2.setString(1, tfroom.getText());
                ResultSet rs2 = pstmt2.executeQuery();
                if (rs2.next()) {
                    int price = Integer.parseInt(rs2.getString("price"));
                    int paidAmount = Integer.parseInt(tfpaid.getText());
                    tfpending.setText(String.valueOf(price - paidAmount));
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage());
        }
    }

    private void updateCustomerData() {
        String number = ccustomer.getSelectedItem();
        String room = tfroom.getText().trim();
        String name = tfname.getText().trim();
        String checkin = tfcheckin.getText().trim();
        String deposit = tfpaid.getText().trim();

        if (room.isEmpty() || name.isEmpty() || checkin.isEmpty() || deposit.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!");
            return;
        }

        try {
            double depositAmount = Double.parseDouble(deposit);
            if (depositAmount < 0) {
                JOptionPane.showMessageDialog(null, "Deposit cannot be negative!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Deposit must be a valid number!");
            return;
        }

        String query = "UPDATE customer SET room = ?, name = ?, checkintime = ?, deposit = ? WHERE number = ?";
        try (Conn c = new Conn();
             PreparedStatement pstmt = c.getConnection().prepareStatement(query)) {
            
            pstmt.setString(1, room);
            pstmt.setString(2, name);
            pstmt.setString(3, checkin);
            pstmt.setString(4, deposit);
            pstmt.setString(5, number);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated Successfully!");
            setVisible(false);
            new Reception();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UpdateCheck();
    }
}
