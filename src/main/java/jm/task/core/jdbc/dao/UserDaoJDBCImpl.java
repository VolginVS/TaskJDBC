package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users"
                + "(id BIGINT not NULL AUTO_INCREMENT,"
                + "name VARCHAR(50) not NULL,"
                + "lastname VARCHAR(50) not NULL,"
                + "age TINYINT not NULL,"
                + " PRIMARY KEY (id))";

        try(Connection connection  = util.getConnection();
                Statement statement =connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Creating table...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try(Connection connection = util.getConnection();
                Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Table drop is success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";

        try(Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println(" User с именем – "+ name +" добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";

        try(Connection connection = util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            System.out.println("User was successfully deleted");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try(Connection connection = util.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try(Connection connection = util.getConnection();
                Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Table is cleaned");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
