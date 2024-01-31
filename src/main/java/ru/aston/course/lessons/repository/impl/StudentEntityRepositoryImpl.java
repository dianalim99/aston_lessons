package ru.aston.course.lessons.repository.impl;

import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapper;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentEntityRepositoryImpl implements StudentEntityRepository {
    private static final StudentEntityRepositoryImpl INSTANCE = new StudentEntityRepositoryImpl();
    private static final String SQL_SELECT_ALL = "SELECT * FROM student";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private StudentResultSetMapper resultSetMapper = new StudentResultSetMapperImpl();
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private StudentEntityRepositoryImpl() {
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

        } catch (SQLException e) {
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        return null;
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity) {
        return null;
    }

    public static StudentEntityRepositoryImpl getInstance() {
        return INSTANCE;
    }
}
