package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static Connection con;

    public static Connection createDBConnection() {
        try {
            con = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

}
