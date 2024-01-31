package ru.aston.course.lessons.service;

import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.TeacherEntity;

import java.util.List;

public interface LessonService {
    LessonEntity findById(Long id);
    List<LessonEntity> findAll();

    boolean deleteById(Long id);
    LessonEntity save(LessonEntity lessonEntity);
    LessonEntity update(LessonEntity lessonEntity);
}
