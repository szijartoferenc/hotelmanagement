package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * AddDriver class for adding new drivers into the system.
 * Integrates with the updated Conn class and ensures database transactions are properly managed.
 */
public class AddDriver extends JFrame implements ActionListener {
    private JButton add, cancel;
    private JTextField tfname, tfage, tfcompany, tfmodel, tflocation;
    private JComboBox<String> gendercombo, availablecombo;

    public AddDriver() {
        setTitle("Add Driver");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        addLabel("Add Drivers", 150, 10, 200, 20, 18, true);
        tfname = addTextField("Name", 70);
        tfage = addTextField("Age", 110);
        gendercombo = addComboBox("Gender", 150, new String[]{"Male", "Female"});
        tfcompany = addTextField("Car Company", 190);
        tfmodel = addTextField("Car Model", 230);
        availablecombo = addComboBox("Available", 270, new String[]{"Available", "Busy"});
        tflocation = addTextField("Location", 310);

        add = addButton("Add Driver", 60, 370);
        cancel = addButton("Cancel", 220, 370);

        add.addActionListener(this);
        cancel.addActionListener(this);

        addImage("icons/eleven.jpg", 400, 30, 500, 300);

        setBounds(300, 200, 980, 470);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfname.getText().trim();
            String ageText = tfage.getText().trim();
            String gender = (String) gendercombo.getSelectedItem();
            String company = tfcompany.getText().trim();
            String brand = tfmodel.getText().trim();
            String available = (String) availablecombo.getSelectedItem();
            String location = tflocation.getText().trim();

            // Input Validation
            if (name.isEmpty() || company.isEmpty() || brand.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 0 || age > 100) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid age (1-100)");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number");
                return;
            }

            // Database Operation
            String query = "INSERT INTO driver (name, age, gender, company, brand, available, location) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Conn conn = new Conn(); 
                 PreparedStatement pstmt = conn.getConnection().prepareStatement(query)) {

                pstmt.setString(1, name);
                pstmt.setInt(2, age);
                pstmt.setString(3, gender);
                pstmt.setString(4, company);
                pstmt.setString(5, brand);
                pstmt.setString(6, available);
                pstmt.setString(7, location);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "New driver added successfully");
                setVisible(false);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        } else {
            setVisible(false);
        }
    }

    // Utility Methods for UI Components
    private void addLabel(String text, int x, int y, int width, int height, int fontSize, boolean isBold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", isBold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        add(label);
    }

    private JTextField addTextField(String label, int y) {
        addLabel(label, 60, y, 120, 30, 16, false);
        JTextField textField = new JTextField();
        textField.setBounds(200, y, 150, 30);
        add(textField);
        return textField;
    }

    private JComboBox<String> addComboBox(String label, int y, String[] options) {
        addLabel(label, 60, y, 120, 30, 16, false);
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setBounds(200, y, 150, 30);
        comboBox.setBackground(Color.WHITE);
        add(comboBox);
        return comboBox;
    }

    private JButton addButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setBounds(x, y, 130, 30);
        add(button);
        return button;
    }

    private void addImage(String path, int x, int y, int width, int height) {
        try {
            ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource(path));
            Image i2 = i1.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            JLabel image = new JLabel(new ImageIcon(i2));
            image.setBounds(x, y, width, height);
            add(image);
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddDriver();
    }
}
