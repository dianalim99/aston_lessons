package ru.aston.course.lessons.service;

import ru.aston.course.lessons.servlet.dto.TeacherDto;

import java.util.List;

public interface TeacherService {

    TeacherDto findById(Long id);

    boolean deleteById(Long id);

    List<TeacherDto> findAll();

    TeacherDto save(TeacherDto teacherDto);

    TeacherDto update(TeacherDto teacherDto);
}

