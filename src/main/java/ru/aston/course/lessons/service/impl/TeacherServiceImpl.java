package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.TeacherEntityRepository;
import ru.aston.course.lessons.repository.impl.TeacherEntityRepositoryImpl;
import ru.aston.course.lessons.service.TeacherService;
import ru.aston.course.lessons.servlet.dto.TeacherDto;
import ru.aston.course.lessons.servlet.mapper.TeacherDtoMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TeacherServiceImpl implements TeacherService {
    private TeacherEntityRepository teacherEntityRepository;
    private TeacherDtoMapper teacherMapstructMapper;

    public TeacherServiceImpl() {
        this.teacherEntityRepository = new TeacherEntityRepositoryImpl();
        this.teacherMapstructMapper = TeacherDtoMapper.INSTANCE;
    }

    public TeacherServiceImpl(TeacherEntityRepository teacherEntityRepository, TeacherDtoMapper teacherMapstructMapper) {
        this.teacherEntityRepository = teacherEntityRepository;
        this.teacherMapstructMapper = teacherMapstructMapper;
    }

    @Override
    public TeacherDto findById(Long id) {
        TeacherEntity teacherEntity = teacherEntityRepository.findById(id);
        return teacherMapstructMapper.toDto(teacherEntity);
    }

    @Override
    public boolean deleteById(Long id) {
        return id != null && teacherEntityRepository.deleteById(id);
    }

    @Override
    public List<TeacherDto> findAll(){
        return teacherEntityRepository.findAll().stream().map(teacherMapstructMapper :: toDto).collect(toList());
    }

    @Override
    public TeacherDto save(TeacherDto teacherDto) {
        return teacherDto != null ? teacherMapstructMapper.toDto(teacherEntityRepository.save(teacherMapstructMapper.toEntity(teacherDto))) : null;
    }

    @Override
    public TeacherDto update(TeacherDto teacherDto) {
        return teacherDto != null ? teacherMapstructMapper.toDto(teacherEntityRepository.update(teacherMapstructMapper.toEntity(teacherDto))) : null;

    }
}
