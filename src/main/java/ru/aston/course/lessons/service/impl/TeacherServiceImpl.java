package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.repository.TeacherEntityRepository;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;
import ru.aston.course.lessons.repository.impl.TeacherEntityRepositoryImpl;
import ru.aston.course.lessons.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherEntityRepository teacherEntityRepository = TeacherEntityRepositoryImpl.getInstance();
    private static final TeacherServiceImpl INSTANCE = new TeacherServiceImpl();
    private TeacherServiceImpl(){}
    @Override
    public TeacherEntity findById(Long id) {
        return teacherEntityRepository.findById(id);
    }

    @Override
    public List<TeacherEntity> findAll() {
        return teacherEntityRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        return teacherEntityRepository.deleteById(id);
    }

    @Override
    public TeacherEntity save(TeacherEntity teacherEntity) {
        return teacherEntityRepository.save(teacherEntity);
    }

    @Override
    public TeacherEntity update(TeacherEntity teacherEntity) {
        return teacherEntityRepository.update(teacherEntity);
    }

    public static TeacherServiceImpl getInstance() {
        return INSTANCE;
    }
}
