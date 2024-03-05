package ru.aston.course.lessons.repository.mapper.impl;

import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.mapper.LessonResultSetMapper;
import ru.aston.course.lessons.repository.mapper.TeacherResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonResultSetMapperImpl implements LessonResultSetMapper {
    @Override
    public LessonEntity map(ResultSet resultSet) {
        try {
            LessonEntity lessonEntity = new LessonEntity();
            lessonEntity.setId(resultSet.getLong("id"));
            lessonEntity.setIdTeacher(resultSet.getLong("id_teacher"));
            lessonEntity.setDate(resultSet.getTimestamp("date"));
            return lessonEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
