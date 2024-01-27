package ru.aston.course.lessons.service;

import ru.aston.course.lessons.model.StudentEntity;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    StudentEntity findById(Long id);
    List<StudentEntity> findAll();
}

