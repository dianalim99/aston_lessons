package ru.aston.course.lessons.servlet.mapper;

import ru.aston.course.lessons.servlet.dto.StudentDto;

import java.io.BufferedReader;
import java.util.List;

public interface StudentJsonMapper {

    String toJson(StudentDto dto);

    String toJson(List<StudentDto> list);

    StudentDto toDto(String json);
}