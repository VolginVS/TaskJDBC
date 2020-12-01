package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/jm_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/jm_db?jm_db?serverTimezone=UTC";

    private static SessionFactory sessionFactory = null;
    public Connection getConnection(){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties properties = new Properties();
            properties.put("connection.driver_class", "com.mysql.jdbc.Driver");
            properties.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
            properties.put("hibernate.connection.url",
                            "jdbc:mysql://localhost:3306/jm_db?serverTimezone=UTC");
            properties.put("hibernate.connection.username", "root");
            properties.put("hibernate.connection.password", "root");
            properties.put("hibernate.current_session_context_class", "thread");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}



