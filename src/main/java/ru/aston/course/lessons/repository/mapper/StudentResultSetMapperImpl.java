package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.StudentGrade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Override
    public StudentEntity map(ResultSet resultSet) throws SQLException {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId(resultSet.getLong("id"));
        studentEntity.setFirstName(resultSet.getString("first_name"));
        studentEntity.setLastName(resultSet.getString("last_name"));
        studentEntity.setGrade(StudentGrade.fromName(resultSet.getString("grade")));

        return studentEntity;
    }
}
