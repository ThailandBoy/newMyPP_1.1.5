package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String url = "jdbc:mysql://localhost:3306/userdb";
    private static final String name = "root";
    private static final String password = "root";
    private static Connection con;

    public static Connection createDBConnection(){
        try{
            con = DriverManager.getConnection(url,name,password);
            System.out.println("Connection successful!");

        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return con;
    }

}
