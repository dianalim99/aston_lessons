package ru.aston.course.lessons.repository.mapper;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherResultSetMapperImpl implements TeacherResultSetMapper {
    @Override
    public TeacherEntity map(ResultSet resultSet) throws SQLException {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setId(resultSet.getLong("id"));
        teacherEntity.setFirstName(resultSet.getString("first_name"));
        teacherEntity.setLastName(resultSet.getString("last_name"));
        teacherEntity.setEmail(resultSet.getString("email"));
        teacherEntity.setPassword(resultSet.getString("password"));
        return teacherEntity;
    }
}
