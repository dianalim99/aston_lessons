package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;
import ru.aston.course.lessons.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentEntityRepository studentEntityRepository = StudentEntityRepositoryImpl.getInstance();
    private static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
    private StudentServiceImpl(){}
    @Override
    public StudentEntity findById(Long id) {
        return studentEntityRepository.findById(id);
    }

    @Override
    public List<StudentEntity> findAll() {
        return studentEntityRepository.findAll();
    }

    public static StudentServiceImpl getInstance() {
        return INSTANCE;
    }
}
