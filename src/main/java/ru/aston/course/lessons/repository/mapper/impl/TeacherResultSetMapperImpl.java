package ru.aston.course.lessons.repository.mapper.impl;

import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.mapper.TeacherResultSetMapper;
import ru.aston.course.lessons.repository.mapper.TeacherResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherResultSetMapperImpl implements TeacherResultSetMapper {
    @Override
    public TeacherEntity map(ResultSet resultSet) {
        try {
            TeacherEntity teacherEntity = new TeacherEntity();
            teacherEntity.setId(resultSet.getLong("id"));
            teacherEntity.setFirstName(resultSet.getString("first_name"));
            teacherEntity.setLastName(resultSet.getString("last_name"));
            teacherEntity.setEmail(resultSet.getString("email"));
            teacherEntity.setPassword(resultSet.getString("password"));
            return teacherEntity;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
