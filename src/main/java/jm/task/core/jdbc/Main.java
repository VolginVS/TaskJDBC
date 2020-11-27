package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
            Statement statement =connection.createStatement()){
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS jm_db");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Max", "Simons", (byte) 15);
        userService.saveUser("Maya", "Gogins", (byte) 25);
        userService.saveUser("Nastya", "Forova", (byte) 45);
        userService.saveUser("Vasya", "Pupkin", (byte) 78);

        List<User> userList = userService.getAllUsers();
        System.out.println("---UserList---");
        for(User user : userList){
            System.out.println(user);
        }
        System.out.println("-----");
        userService.cleanUsersTable();
    }
}
