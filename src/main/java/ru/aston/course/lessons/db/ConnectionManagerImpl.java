package ru.aston.course.lessons.db;

import ru.aston.course.lessons.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {
    private String DB_USERNAME = "db.username";
    private String DB_PASSWORD = "db.password";
    private String DB_URL = "db.url";
    private Properties properties;
    private static final String DB_DRIVER = "org.postgresql.Driver";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectionManagerImpl() {
        this.properties = PropertiesUtil.get();
    }

    public ConnectionManagerImpl(Properties properties) {
        this.properties = properties;

    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            return DriverManager.getConnection(
                    properties.getProperty(DB_URL),
                    properties.getProperty(DB_USERNAME),
                    properties.getProperty(DB_PASSWORD)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}