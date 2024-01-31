package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.StudentGrade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Override
    public StudentEntity map(ResultSet resultSet) throws SQLException {
        return map("", resultSet);
    }

    @Override
    public StudentEntity map(String prefix, ResultSet resultSet) throws SQLException {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId(resultSet.getLong(prefix + "id"));
        studentEntity.setFirstName(resultSet.getString(prefix + "first_name"));
        studentEntity.setLastName(resultSet.getString(prefix + "last_name"));
        studentEntity.setGrade(StudentGrade.fromName(resultSet.getString(prefix + "grade")));

        return studentEntity;
    }
}
