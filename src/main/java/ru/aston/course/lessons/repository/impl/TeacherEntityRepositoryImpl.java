package ru.aston.course.lessons.repository.impl;

import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.TeacherEntityRepository;
import ru.aston.course.lessons.repository.mapper.TeacherResultSetMapper;
import ru.aston.course.lessons.repository.mapper.impl.TeacherResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherEntityRepositoryImpl implements TeacherEntityRepository {
    private static final String SQL_SELECT_ALL = "SELECT * FROM teacher";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM teacher WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO teacher (first_name, last_name, email, password) values (?,?,?,?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM teacher WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE teacher SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";
    private TeacherResultSetMapper resultSetMapper;
    private ConnectionManager connectionManager;

    public TeacherEntityRepositoryImpl() {
        this(new ConnectionManagerImpl(), new TeacherResultSetMapperImpl());
    }

    public TeacherEntityRepositoryImpl(ConnectionManager connectionManager, TeacherResultSetMapper resultSetMapper) {
        this.resultSetMapper = resultSetMapper;
        this.connectionManager = connectionManager;
    }


    @Override
    public TeacherEntity findById(Long id) {
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
    public List<TeacherEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ;

            ResultSet resultSet = preparedStatement.executeQuery();
            List<TeacherEntity> teachers = new ArrayList<>();
            while (resultSet.next()) {
                teachers.add(resultSetMapper.map(resultSet));
            }
            return teachers;

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
    public TeacherEntity save(TeacherEntity teacherEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, new String[]{"id"})) {
            preparedStatement.setString(1, teacherEntity.getFirstName());
            preparedStatement.setString(2, teacherEntity.getLastName());
            preparedStatement.setString(3, teacherEntity.getEmail());
            preparedStatement.setString(4, teacherEntity.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    teacherEntity.setId(id);
                } else {
                    throw new SQLException("Failed to get the generated key.");
                }
            }
            return teacherEntity;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TeacherEntity update(TeacherEntity teacherEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setLong(5, teacherEntity.getId());
            preparedStatement.setString(1, teacherEntity.getFirstName());
            preparedStatement.setString(2, teacherEntity.getLastName());
            preparedStatement.setString(3, teacherEntity.getEmail());
            preparedStatement.setString(4, teacherEntity.getPassword());
            preparedStatement.executeUpdate();
            return findById(teacherEntity.getId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
