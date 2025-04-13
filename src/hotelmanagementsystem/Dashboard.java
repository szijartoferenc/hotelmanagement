package hotelmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    public Dashboard() {
        setBounds(0, 0, 1550, 1000);
        setLayout(null);
        
        // Háttérkép beállítása
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/third.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1550, 1000, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1550, 1000);
        add(image);
        
        // Címszöveg hozzáadása
        JLabel text = new JLabel("THE MAT GROUP WELCOMES YOU");
        text.setBounds(400, 80, 1000, 50);
        text.setFont(new Font("Tahoma", Font.PLAIN, 46));
        text.setForeground(Color.WHITE);
        image.add(text);

        // Menü inicializálása
        initializeMenu();
        
        setVisible(true);
    }

    private void initializeMenu() {
        JMenuBar mb = new JMenuBar();
        
        // Hotel Management menü
        JMenu hotel = new JMenu("HOTEL MANAGEMENT");
        hotel.setForeground(Color.RED);
        mb.add(hotel);
        
        JMenuItem reception = createMenuItem("RECEPTION", hotel);

        // Admin menü
        JMenu admin = new JMenu("ADMIN");
        admin.setForeground(Color.BLUE);
        mb.add(admin);

        JMenuItem addEmployee = createMenuItem("ADD EMPLOYEE", admin);
        JMenuItem addRooms = createMenuItem("ADD ROOMS", admin);
        JMenuItem addDrivers = createMenuItem("ADD DRIVERS", admin);

        // Menü beállítása az ablakhoz
        setJMenuBar(mb);
    }

    private JMenuItem createMenuItem(String name, JMenu parentMenu) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
        menuItem.addActionListener(this);
        parentMenu.add(menuItem);
        return menuItem;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        switch (command.toUpperCase()) {
            case "ADD EMPLOYEE":
                new AddEmployee();
                break;
            case "ADD ROOMS":
                new AddRooms();
                break;
            case "ADD DRIVERS":
                new AddDriver();
                break;
            case "RECEPTION":
                new Reception();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown action: " + command);
                break;
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
