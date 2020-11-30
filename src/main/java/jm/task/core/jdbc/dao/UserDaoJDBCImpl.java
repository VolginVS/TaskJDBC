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
        Connection connection = null;
        Statement statement = null;

        String sql = "CREATE TABLE IF NOT EXISTS users"
                + "(id BIGINT not NULL AUTO_INCREMENT,"
                + "name VARCHAR(50) not NULL,"
                + "lastname VARCHAR(50) not NULL,"
                + "age TINYINT not NULL,"
                + " PRIMARY KEY (id))";

        try {
            connection = util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("Creating table...");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Statement statement = null;

        String sql = "DROP TABLE IF EXISTS users";

        try {
            connection = util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("Table drop is success");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement preparedStatement  = null;

        String sql = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";

        try {
            connection = util.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println(" User с именем – "+ name +" добавлен в базу данных");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        PreparedStatement preparedStatement  = null;

        String sql = "DELETE FROM users WHERE id = ?";

        try{
            connection = util.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User was successfully deleted");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement  = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try {
            connection = util.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            connection.commit();
            System.out.println("List of Users created");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        Statement statement = null;

        String sql = "TRUNCATE TABLE users";

        try {
            connection = util.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("Table is cleaned");
        } catch (SQLException exception) {
            try{
                if(connection != null){
                    connection.rollback();
                }
            } catch (SQLException exceptionRollback){
                exceptionRollback.printStackTrace();
            }
            exception.printStackTrace();
        } finally {
            try{
                if(connection != null) {
                    connection.close();
                }
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException exceptionFinally) {
                exceptionFinally.printStackTrace();
            }
        }
    }
}
