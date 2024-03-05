package ru.aston.course.lessons.service;

import ru.aston.course.lessons.servlet.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto findById(Long id);

    boolean deleteById(Long id);

    List<StudentDto> findAll();

    StudentDto save(StudentDto studentDto);

    StudentDto update(StudentDto studentDto);
}

