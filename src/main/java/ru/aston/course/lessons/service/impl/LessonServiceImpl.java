package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.repository.LessonEntityRepository;
import ru.aston.course.lessons.repository.impl.LessonEntityRepositoryImpl;
import ru.aston.course.lessons.service.LessonService;
import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;
import ru.aston.course.lessons.servlet.mapper.LessonDtoMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class LessonServiceImpl implements LessonService {
    private LessonEntityRepository lessonEntityRepository;
    private LessonDtoMapper lessonMapstructMapper;

    public LessonServiceImpl() {
        this.lessonEntityRepository = new LessonEntityRepositoryImpl();
        this.lessonMapstructMapper = LessonDtoMapper.INSTANCE;
    }

    public LessonServiceImpl(LessonEntityRepository lessonEntityRepository, LessonDtoMapper lessonMapstructMapper) {
        this.lessonEntityRepository = lessonEntityRepository;
        this.lessonMapstructMapper = lessonMapstructMapper;
    }

    @Override
    public LessonDtoOut findById(Long id) {
        LessonEntity lessonEntity = lessonEntityRepository.findById(id);
        return lessonMapstructMapper.toDto(lessonEntity);
    }

    @Override
    public boolean deleteById(Long id) {
        return id != null && lessonEntityRepository.deleteById(id);
    }

    @Override
    public List<LessonDtoOut> findAll(){
        return lessonEntityRepository.findAll().stream().map(lessonMapstructMapper :: toDto).collect(toList());
    }

    @Override
    public LessonDtoOut save(LessonDtoIn lessonDtoIn) {
        return lessonDtoIn != null ? lessonMapstructMapper.toDto(lessonEntityRepository.save(lessonMapstructMapper.toEntity(lessonDtoIn))) : null;
    }

    @Override
    public LessonDtoOut update(LessonDtoIn lessonDtoIn) {
        return lessonDtoIn != null ? lessonMapstructMapper.toDto(lessonEntityRepository.update(lessonMapstructMapper.toEntity(lessonDtoIn))) : null;

    }
}
