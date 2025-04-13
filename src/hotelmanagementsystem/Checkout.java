package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Date;
import java.awt.event.*;

public class Checkout extends JFrame implements ActionListener {
    
    Choice ccustomer;
    JLabel lblroomnumber, lblcheckintime, lblcheckouttime;
    JButton checkout, back;

    Checkout() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Checkout");
        text.setBounds(100, 20, 100, 30);
        text.setForeground(Color.BLUE);
        text.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(text);

        JLabel lblid = new JLabel("Customer ID");
        lblid.setBounds(30, 80, 100, 30);
        add(lblid);

        ccustomer = new Choice();
        ccustomer.setBounds(150, 80, 150, 25);
        add(ccustomer);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tick.png"));
        Image i2 = i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel tick = new JLabel(i3);
        tick.setBounds(310, 80, 20, 20);
        add(tick);

        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(30, 130, 100, 30);
        add(lblroom);

        lblroomnumber = new JLabel();
        lblroomnumber.setBounds(150, 130, 100, 30);
        add(lblroomnumber);

        JLabel lblcheckin = new JLabel("Check-in Time");
        lblcheckin.setBounds(30, 180, 100, 30);
        add(lblcheckin);

        lblcheckintime = new JLabel();
        lblcheckintime.setBounds(150, 180, 200, 30);
        add(lblcheckintime);

        JLabel lblcheckout = new JLabel("Checkout Time");
        lblcheckout.setBounds(30, 230, 100, 30);
        add(lblcheckout);

        lblcheckouttime = new JLabel("" + new Date());
        lblcheckouttime.setBounds(150, 230, 200, 30);
        add(lblcheckouttime);

        checkout = new JButton("Checkout");
        checkout.setBackground(Color.BLACK);
        checkout.setForeground(Color.WHITE);
        checkout.setBounds(30, 280, 120, 30);
        checkout.addActionListener(this);
        add(checkout);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(170, 280, 120, 30);
        back.addActionListener(this);
        add(back);

        fetchCustomerData();

        ccustomer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                updateRoomAndCheckinTime();
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/six.jpg"));
        Image i5 = i4.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image = new JLabel(i6);
        image.setBounds(350, 50, 400, 250);
        add(image);

        setBounds(300, 200, 800, 400);
        setVisible(true);
    }

    private void fetchCustomerData() {
        String query = "SELECT number FROM customer";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ccustomer.add(rs.getString("number"));
            }

            updateRoomAndCheckinTime();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching customer data: " + e.getMessage());
        }
    }

    private void updateRoomAndCheckinTime() {
        String selectedCustomer = ccustomer.getSelectedItem();
        if (selectedCustomer == null) return;

        String query = "SELECT room, checkintime FROM customer WHERE number = '" + selectedCustomer + "'";
        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                lblroomnumber.setText(rs.getString("room"));
                lblcheckintime.setText(rs.getString("checkintime"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching customer details: " + e.getMessage());
        }
    }

    private void processCheckout() {
        String customerId = ccustomer.getSelectedItem();
        String roomNumber = lblroomnumber.getText();

        if (customerId == null || roomNumber == null || roomNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No customer selected for checkout.");
            return;
        }

        String query1 = "DELETE FROM customer WHERE number = '" + customerId + "'";
        String query2 = "UPDATE room SET availability = 'Available' WHERE roomnumber = '" + roomNumber + "'";

        try (Conn c = new Conn();
             Statement stmt = c.getConnection().createStatement()) {

            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);

            JOptionPane.showMessageDialog(null, "Checkout successful!");

            setVisible(false);
            new Reception();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error during checkout: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == checkout) {
            processCheckout();
        } else {
            setVisible(false);
            new Reception();
        }
    }

    public static void main(String[] args) {
        new Checkout();
    }
}

