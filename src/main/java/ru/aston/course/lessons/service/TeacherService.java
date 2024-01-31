package ru.aston.course.lessons.service;

import ru.aston.course.lessons.model.TeacherEntity;

import java.util.List;

public interface TeacherService {
    TeacherEntity findById(Long id);
    List<TeacherEntity> findAll();

    boolean deleteById(Long id);
    TeacherEntity save(TeacherEntity teacherEntity);
    TeacherEntity update(TeacherEntity teacherEntity);
}
