package ru.aston.course.lessons.service;

import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;

import java.util.List;

public interface LessonService {

    LessonDtoOut findById(Long id);

    boolean deleteById(Long id);

    List<LessonDtoOut> findAll();

    LessonDtoOut save(LessonDtoIn lessonDtoIn);

    LessonDtoOut update(LessonDtoIn lessonDtoIn);
}

