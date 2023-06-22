package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection con;
    private final String CREATEUSERTABLE = "CREATE TABLE IF NOT EXISTS `userdb`.`user` (\n" +
            "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastName` VARCHAR(45) NULL,\n" +
            "  `age` TINYINT(3) NULL,\n" +
            "  PRIMARY KEY (id));";
    private final String DROPTABLEUSER = "DROP TABLE IF EXISTS userdb.user";
    private final String SAVEUSER = "INSERT INTO userdb.user (name, lastname, age) VALUES(?,?,?)";
    private final String DELETEUSERBYID = "DELETE FROM userdb.user WHERE id = ?";
    private final String GETALLUSERS = "SELECT * FROM userdb.user";
    private final String CLEANUSERSTABLE = "TRUNCATE TABLE userdb.user";

    public UserDaoJDBCImpl() {
        con = Util.createDBConnection();
    }

    public void createUsersTable() {
        try {
            PreparedStatement pstm = con.prepareStatement(CREATEUSERTABLE);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            PreparedStatement pstm = con.prepareStatement(DROPTABLEUSER);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement pstm = con.prepareStatement(SAVEUSER);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
            System.out.printf(" User c именем - %s добавлен в базу данных \n", name);
        } catch (SQLException ex) {
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement pstm = con.prepareStatement(DELETEUSERBYID);
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement pstm = con.prepareStatement(GETALLUSERS);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getByte(4)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        users.stream().forEach(System.out::println);
        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement stm = con.createStatement();
            stm.execute(CLEANUSERSTABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
