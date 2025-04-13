package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Date;
import java.awt.event.*;

public class AddCustomer extends JFrame implements ActionListener {

    JComboBox<String> comboid;
    JTextField tfnumber, tfname, tfcountry, tfdeposit;
    JRadioButton rmale, rfemale;
    Choice croom;
    JLabel checkintime;
    JButton add, back;

    AddCustomer() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("NEW CUSTOMER FORM");
        text.setBounds(100, 20, 300, 30);
        text.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(text);

        JLabel lblid = new JLabel("ID");
        lblid.setBounds(35, 80, 100, 20);
        lblid.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblid);

        String[] options = {"ID card", "Passport", "Driving License"};
        comboid = new JComboBox<>(options);
        comboid.setBounds(200, 80, 150, 25);
        comboid.setBackground(Color.WHITE);
        add(comboid);

        JLabel lblnumber = new JLabel("Number");
        lblnumber.setBounds(35, 120, 100, 20);
        lblnumber.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblnumber);

        tfnumber = new JTextField();
        tfnumber.setBounds(200, 120, 150, 25);
        add(tfnumber);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(35, 160, 100, 20);
        lblname.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 160, 150, 25);
        add(tfname);

        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(35, 200, 100, 20);
        lblgender.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblgender);

        rmale = new JRadioButton("Male");
        rmale.setBackground(Color.WHITE);
        rmale.setBounds(200, 200, 60, 25);
        add(rmale);

        rfemale = new JRadioButton("Female");
        rfemale.setBackground(Color.WHITE);
        rfemale.setBounds(270, 200, 100, 25);
        add(rfemale);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rmale);
        genderGroup.add(rfemale);

        JLabel lblcountry = new JLabel("Country");
        lblcountry.setBounds(35, 240, 100, 20);
        lblcountry.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblcountry);

        tfcountry = new JTextField();
        tfcountry.setBounds(200, 240, 150, 25);
        add(tfcountry);

        JLabel lblroom = new JLabel("Room Number");
        lblroom.setBounds(35, 280, 150, 20);
        lblroom.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lblroom);

        croom = new Choice();
        loadAvailableRooms();

        croom.setBounds(200, 280, 150, 25);
        add(croom);

        JLabel lbltime = new JLabel("Checkin time");
        lbltime.setBounds(35, 320, 150, 20);
        lbltime.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lbltime);

        Date date = new Date();
        checkintime = new JLabel(date.toString());
        checkintime.setBounds(200, 320, 210, 20);
        checkintime.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(checkintime);

        JLabel lbldeposit = new JLabel("Deposit");
        lbldeposit.setBounds(35, 360, 100, 25);
        lbldeposit.setFont(new Font("Raleway", Font.PLAIN, 20));
        add(lbldeposit);

        tfdeposit = new JTextField();
        tfdeposit.setBounds(200, 360, 150, 25);
        add(tfdeposit);

        add = new JButton("Add");
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.setBounds(50, 410, 120, 30);
        add.addActionListener(this);
        add(add);

        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(200, 410, 120, 30);
        back.addActionListener(this);
        add(back);

        setBounds(350, 200, 800, 550);
        setVisible(true);
    }

    private void loadAvailableRooms() {
        try (Conn conn = new Conn();
             PreparedStatement pstmt = conn.getConnection().prepareStatement("SELECT roomnumber FROM room WHERE availability = 'Available'")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                croom.add(rs.getString("roomnumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String id = (String) comboid.getSelectedItem();
            String number = tfnumber.getText();
            String name = tfname.getText();
            String gender = rmale.isSelected() ? "Male" : "Female";
            String country = tfcountry.getText();
            String room = croom.getSelectedItem();
            String ctime = checkintime.getText();
            String deposit = tfdeposit.getText();

            try (Conn conn = new Conn()) {
                Connection c = conn.getConnection();
                String query1 = "INSERT INTO customer (id, number, name, gender, country, room, checkintime, deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                String query2 = "UPDATE room SET availability = 'Occupied' WHERE roomnumber = ?";

                try (PreparedStatement pstmt1 = c.prepareStatement(query1);
                     PreparedStatement pstmt2 = c.prepareStatement(query2)) {

                    pstmt1.setString(1, id);
                    pstmt1.setString(2, number);
                    pstmt1.setString(3, name);
                    pstmt1.setString(4, gender);
                    pstmt1.setString(5, country);
                    pstmt1.setString(6, room);
                    pstmt1.setString(7, ctime);
                    pstmt1.setString(8, deposit);

                    pstmt2.setString(1, room);

                    pstmt1.executeUpdate();
                    pstmt2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "New customer added successfully");
                    setVisible(false);
                    new Reception();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Reception();
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
