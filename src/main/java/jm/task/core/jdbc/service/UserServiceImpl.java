package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS `userdb`.`user` (\n" +
                "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "  PRIMARY KEY (`id`));";
        try (Connection con = Util.createDBConnection()) {
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS userdb.user";
        try (Connection con = Util.createDBConnection()){
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO userdb.user (name, lastname, age) VALUE(?,?,?)";
        try (Connection con = Util.createDBConnection()){
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
        }catch (SQLException ex){
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM userdb.user WHERE id = ?";
        try(Connection con = Util.createDBConnection()){
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setLong(1,id);
            pstm.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM userdb.user";
        List<User> users = new ArrayList<>();
        try (Connection con = Util.createDBConnection()){
            PreparedStatement pstm = con.prepareStatement(query);
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                users.add(new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE userdb.user";
        try(Connection con = Util.createDBConnection()){
            Statement stm = con.createStatement();
            stm.execute(query);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
