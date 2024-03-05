package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TeacherResultSetMapper {
    TeacherEntity map(ResultSet resultSet) throws SQLException;
}
