package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface StudentResultSetMapper {
    StudentEntity map(ResultSet resultSet) throws SQLException;
}
