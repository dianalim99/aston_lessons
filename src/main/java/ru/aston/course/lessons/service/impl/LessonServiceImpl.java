package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.repository.LessonEntityRepository;
import ru.aston.course.lessons.repository.impl.LessonEntityRepositoryImpl;
import ru.aston.course.lessons.service.LessonService;

import java.util.List;

public class LessonServiceImpl implements LessonService {
    private final LessonEntityRepository lessonEntityRepository = LessonEntityRepositoryImpl.getInstance();
    private static final LessonServiceImpl INSTANCE = new LessonServiceImpl();
    private LessonServiceImpl(){}
    @Override
    public LessonEntity findById(Long id) {
        return lessonEntityRepository.findById(id);
    }

    @Override
    public List<LessonEntity> findAll() {
        return lessonEntityRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        return lessonEntityRepository.deleteById(id);
    }

    @Override
    public LessonEntity save(LessonEntity lessonEntity) {
        return lessonEntityRepository.save(lessonEntity);
    }

    @Override
    public LessonEntity update(LessonEntity lessonEntity) {
        return lessonEntityRepository.update(lessonEntity);
    }

    public static LessonServiceImpl getInstance() {
        return INSTANCE;
    }
}
