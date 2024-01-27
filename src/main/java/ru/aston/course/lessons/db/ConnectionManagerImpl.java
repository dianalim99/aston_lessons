package ru.aston.course.lessons.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManagerImpl implements ConnectionManager {
    private static final ConnectionManagerImpl INSTANCE = new ConnectionManagerImpl();
    private ConnectionManagerImpl() {

    }
    private static final DataSource dataSource;

    static {
        try (InputStream inputStream = ConnectionManagerImpl.class.getClassLoader()
                .getResourceAsStream("db.properties")) {

            Properties props = new Properties();
            props.load(inputStream);

            dataSource = new HikariDataSource(new HikariConfig(props));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static ConnectionManagerImpl getInstance() {
        return INSTANCE;
    }
}