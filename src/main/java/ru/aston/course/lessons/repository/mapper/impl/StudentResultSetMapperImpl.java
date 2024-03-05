package ru.aston.course.lessons.repository.mapper.impl;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.mapper.StudentResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Override
    public StudentEntity map(ResultSet resultSet){
        return map("", resultSet);

    }

    @Override
    public StudentEntity map(String prefix, ResultSet resultSet){
        try {
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setId(resultSet.getLong(prefix + "id"));
            studentEntity.setFirstName(resultSet.getString(prefix + "first_name"));
            studentEntity.setLastName(resultSet.getString(prefix + "last_name"));
            return studentEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
