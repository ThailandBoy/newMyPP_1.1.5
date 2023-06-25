package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        
        userService.dropUsersTable();
        
        userService.createUsersTable();

        userService.saveUser("User1","lastNameUser1", (byte) 28);
        userService.saveUser("User2","lastNameUser2", (byte) 38);
        userService.saveUser("User3","lastNameUser3", (byte) 48);
        userService.saveUser("User4","lastNameUser4", (byte) 58);
      
        userService.getAllUsers();
        userService.removeUserById(3l);
        userService.cleanUsersTable();
        userService.dropUsersTable();

 
        


    }
}
