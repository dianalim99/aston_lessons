package ru.aston.course.lessons.repository.impl;

import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.LessonEntityRepository;
import ru.aston.course.lessons.repository.mapper.LessonResultSetMapper;
import ru.aston.course.lessons.repository.mapper.LessonResultSetMapperImpl;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapper;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LessonEntityRepositoryImpl implements LessonEntityRepository {
    private static final LessonEntityRepositoryImpl INSTANCE = new LessonEntityRepositoryImpl();
    private static final String SQL_SELECT_ALL = "SELECT lesson.id,\n" +
            "       id_teacher,\n" +
            "       date,\n" +
            "       id_lesson,\n" +
            "       id_student,\n" +
            "       student.id student_id,\n" +
            "       first_name student_first_name,\n" +
            "       last_name  student_last_name,\n" +
            "       grade      student_grade\n" +
            "FROM lesson\n" +
            "         JOIN lesson_to_student ON lesson.id = lesson_to_student.id_lesson\n" +
            "         JOIN student ON student.id = lesson_to_student.id_student\n";
    private static final String SQL_SELECT_BY_ID = "SELECT lesson.id,\n" +
            "       id_teacher,\n" +
            "       date,\n" +
            "       id_lesson,\n" +
            "       id_student,\n" +
            "       student.id student_id,\n" +
            "       first_name student_first_name,\n" +
            "       last_name  student_last_name,\n" +
            "       grade      student_grade\n" +
            "FROM lesson\n" +
            "         JOIN lesson_to_student ON lesson.id = lesson_to_student.id_lesson\n" +
            "         JOIN student ON student.id = lesson_to_student.id_student\n" +
            "WHERE lesson.id = ?";
    private static final String SQL_SELECT_BY_DATE = "SELECT * FROM lesson WHERE date = ?";
    private static final String SQL_INSERT = "INSERT INTO lesson (id_teacher, date) values (?,?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM lesson WHERE id = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE lesson SET id_teacher = ?, date = ? WHERE id = ?";

    private LessonResultSetMapper resultSetMapper = new LessonResultSetMapperImpl();
    private StudentResultSetMapper resultSetMapperStudent = new StudentResultSetMapperImpl();
    private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    private LessonEntityRepositoryImpl() {

    }

    @Override
    public LessonEntity findById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            LessonEntity lesson;
            if (resultSet.next()) {
                lesson = resultSetMapper.map(resultSet);
                do {
                    StudentEntity student = resultSetMapperStudent.map("student_", resultSet);
                    lesson.getStudents().add(student);
                } while (resultSet.next());
                return lesson;
            } else {
                return null;
            }

        } catch (SQLException e) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LessonEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<LessonEntity> lessons = new ArrayList<>();
            while (resultSet.next()) {
                Long resultSetId = resultSet.getLong("id");
                if (lessons.stream().anyMatch(lesson -> Objects.equals(lesson.getId(), resultSetId))) {
                    StudentEntity student = resultSetMapperStudent.map("student_", resultSet);
                    var existingLesson = lessons
                            .stream()
                            .filter(lesson -> Objects.equals(lesson.getId(), resultSetId))
                            .findFirst()
                            .get();
                    existingLesson.getStudents().add(student);
                } else {
                    LessonEntity lesson = resultSetMapper.map(resultSet);
                    StudentEntity student = resultSetMapperStudent.map("student_", resultSet);
                    lesson.getStudents().add(student);
                    lessons.add(lesson);
                }

            }
            return lessons;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LessonEntity save(LessonEntity lessonEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, new String[]{"id"})) {
            preparedStatement.setLong(1, lessonEntity.getIdTeacher());
            preparedStatement.setTimestamp(2, lessonEntity.getDate());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    lessonEntity.setId(id);
                } else {
                    throw new SQLException("Failed to get the generated key.");
                }
            }
            return lessonEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LessonEntity update(LessonEntity lessonEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setLong(3, lessonEntity.getId());
            preparedStatement.setLong(1, lessonEntity.getIdTeacher());
            preparedStatement.setTimestamp(2, lessonEntity.getDate());
            preparedStatement.executeUpdate();
            return findById(lessonEntity.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static LessonEntityRepositoryImpl getInstance() {
        return INSTANCE;
    }
}
