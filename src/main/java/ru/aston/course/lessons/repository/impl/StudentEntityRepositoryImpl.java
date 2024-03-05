package ru.aston.course.lessons.repository.impl;

import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapper;
import ru.aston.course.lessons.repository.mapper.impl.StudentResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentEntityRepositoryImpl implements StudentEntityRepository {
    private static final String SQL_SELECT_ALL = "SELECT * FROM student";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO student (first_name, last_name) values (?,?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM student WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE student SET first_name = ?, last_name = ? WHERE id = ?";
    private StudentResultSetMapper resultSetMapper;
    private ConnectionManager connectionManager;

    public StudentEntityRepositoryImpl() {
        this(new ConnectionManagerImpl(), new StudentResultSetMapperImpl());
    }

    public StudentEntityRepositoryImpl(ConnectionManager connectionManager, StudentResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
        this.connectionManager = connectionManager;
    }


    @Override
    public StudentEntity findById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetMapper.map(resultSet);
            } else {
                return null;
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StudentEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ;

            ResultSet resultSet = preparedStatement.executeQuery();
            List<StudentEntity> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(resultSetMapper.map(resultSet));
            }
            return students;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, new String[]{"id"})) {
            preparedStatement.setString(1, studentEntity.getFirstName());
            preparedStatement.setString(2, studentEntity.getLastName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    studentEntity.setId(id);
                } else {
                    throw new SQLException("Failed to get the generated key.");
                }
            }
            return studentEntity;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setLong(3, studentEntity.getId());
            preparedStatement.setString(1, studentEntity.getFirstName());
            preparedStatement.setString(2, studentEntity.getLastName());
            preparedStatement.executeUpdate();
            return findById(studentEntity.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
