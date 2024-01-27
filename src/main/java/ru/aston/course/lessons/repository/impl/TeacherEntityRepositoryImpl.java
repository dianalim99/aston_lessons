package ru.aston.course.lessons.repository.impl;

import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.TeacherEntityRepository;

import java.util.List;

public class TeacherEntityRepositoryImpl implements TeacherEntityRepository {
    @Override
    public TeacherEntity findById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<TeacherEntity> findAll() {
        return null;
    }

    @Override
    public TeacherEntity save(TeacherEntity teacherEntity) {
        return null;
    }

    @Override
    public void update(TeacherEntity teacherEntity) {

    }
}
