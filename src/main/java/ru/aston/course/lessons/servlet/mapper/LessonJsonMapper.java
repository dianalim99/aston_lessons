package ru.aston.course.lessons.servlet.mapper;

import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;

import java.util.List;

public interface LessonJsonMapper {

    String toJson(LessonDtoOut dto);

    String toJson(List<LessonDtoOut> list);

    LessonDtoIn toDto(String json);
}