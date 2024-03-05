package ru.aston.course.lessons.servlet.mapper;

import ru.aston.course.lessons.servlet.dto.TeacherDto;

import java.util.List;

public interface TeacherJsonMapper {

    String toJson(TeacherDto dto);

    String toJson(List<TeacherDto> list);

    TeacherDto toDto(String json);
}