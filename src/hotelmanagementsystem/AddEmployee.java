package hotelmanagementsystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddEmployee extends JFrame implements ActionListener {
    JTextField tfname, tfemail, tfphone, tfage, tfsalary, tfssn;
    JRadioButton rbmale, rbfemale;
    JButton submit;
    JComboBox<String> cbjob;

    AddEmployee() {
        setLayout(null);

        JLabel lblname = new JLabel("NAME");
        lblname.setBounds(60, 30, 120, 30);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 30, 150, 30);
        add(tfname);

        JLabel lblage = new JLabel("AGE");
        lblage.setBounds(60, 80, 120, 30);
        lblage.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblage);

        tfage = new JTextField();
        tfage.setBounds(200, 80, 150, 30);
        add(tfage);

        JLabel lblgender = new JLabel("GENDER");
        lblgender.setBounds(60, 130, 120, 30);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblgender);

        rbmale = new JRadioButton("Male");
        rbmale.setBounds(200, 130, 70, 30);
        rbmale.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbmale.setBackground(Color.WHITE);
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setBounds(280, 130, 70, 30);
        rbfemale.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rbfemale.setBackground(Color.WHITE);
        add(rbfemale);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbmale);
        bg.add(rbfemale);

        JLabel lbljob = new JLabel("JOB");
        lbljob.setBounds(60, 180, 120, 30);
        lbljob.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lbljob);

        String[] jobs = {"Front Desk Clerks", "Doormen", "Housekeeping", "Kitchen Staff", "Room Service", "Chefs", "Waiter/Waitress", "Manager", "Bookkeeper"};
        cbjob = new JComboBox<>(jobs);
        cbjob.setBounds(200, 180, 150, 30);
        cbjob.setBackground(Color.WHITE);
        add(cbjob);

        JLabel lblsalary = new JLabel("SALARY");
        lblsalary.setBounds(60, 230, 120, 30);
        lblsalary.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblsalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(200, 230, 150, 30);
        add(tfsalary);

        JLabel lblphone = new JLabel("PHONE");
        lblphone.setBounds(60, 280, 120, 30);
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(200, 280, 150, 30);
        add(tfphone);

        JLabel lblemail = new JLabel("EMAIL");
        lblemail.setBounds(60, 330, 120, 30);
        lblemail.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 330, 150, 30);
        add(tfemail);

        JLabel lblssn = new JLabel("SSN");
        lblssn.setBounds(60, 380, 120, 30);
        lblssn.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblssn);

        tfssn = new JTextField();
        tfssn.setBounds(200, 380, 150, 30);
        add(tfssn);

        submit = new JButton("SUBMIT");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(200, 430, 150, 30);
        submit.addActionListener(this);
        add(submit);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(380, 60, 450, 370);
        add(image);

        getContentPane().setBackground(Color.WHITE);
        setBounds(350, 200, 850, 540);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText().trim();
        String ageText = tfage.getText().trim();
        String salaryText = tfsalary.getText().trim();
        String phone = tfphone.getText().trim();
        String email = tfemail.getText().trim();
        String ssn = tfssn.getText().trim();
        
        if (name.isEmpty() || ageText.isEmpty() || salaryText.isEmpty() || phone.isEmpty() || email.isEmpty() || ssn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled!");
            return;
        }
        
        String gender = rbmale.isSelected() ? "Male" : rbfemale.isSelected() ? "Female" : null;
        if (gender == null) {
            JOptionPane.showMessageDialog(null, "Please select a gender");
            return;
        }
        
        String job = (String) cbjob.getSelectedItem();
        
        try (Conn conn = new Conn(); PreparedStatement pstmt = conn.getConnection().prepareStatement(
            "INSERT INTO employee (name, age, gender, job, salary, phone, email, ssn) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");) {
            
            pstmt.setString(1, name);
            pstmt.setInt(2, Integer.parseInt(ageText));
            pstmt.setString(3, gender);
            pstmt.setString(4, job);
            pstmt.setDouble(5, Double.parseDouble(salaryText));
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, ssn);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee added successfully");
            setVisible(false);
            
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}

