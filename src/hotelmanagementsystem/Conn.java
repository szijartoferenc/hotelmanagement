package hotelmanagementsystem;

import java.sql.*;

public class Conn implements AutoCloseable {
    private Connection c;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hotelmanagementsystem?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "asdfélkj"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return c;
    }

    @Override
    public void close() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
