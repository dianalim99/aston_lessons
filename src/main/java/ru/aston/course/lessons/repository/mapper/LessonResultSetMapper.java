package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LessonResultSetMapper {
    LessonEntity map(ResultSet resultSet) throws SQLException;
}
