package ru.aston.course.lessons.service.impl;

import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;
import ru.aston.course.lessons.service.StudentService;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentDtoMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StudentServiceImpl implements StudentService {
    private StudentEntityRepository studentEntityRepository;
    private StudentDtoMapper studentMapstructMapper;

    public StudentServiceImpl() {
        this.studentEntityRepository = new StudentEntityRepositoryImpl();
        this.studentMapstructMapper = StudentDtoMapper.INSTANCE;
    }

    public StudentServiceImpl(StudentEntityRepository studentEntityRepository, StudentDtoMapper studentMapstructMapper) {
        this.studentEntityRepository = studentEntityRepository;
        this.studentMapstructMapper = studentMapstructMapper;
    }

    @Override
    public StudentDto findById(Long id) {
        StudentEntity studentEntity = studentEntityRepository.findById(id);
        return studentMapstructMapper.toDto(studentEntity);
    }

    @Override
    public boolean deleteById(Long id) {
        return id != null && studentEntityRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> findAll(){
        return studentEntityRepository.findAll().stream().map(studentMapstructMapper :: toDto).collect(toList());
    }

    @Override
    public StudentDto save(StudentDto studentDto) {
        return studentDto != null ? studentMapstructMapper.toDto(studentEntityRepository.save(studentMapstructMapper.toEntity(studentDto))) : null;
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        return studentDto != null ? studentMapstructMapper.toDto(studentEntityRepository.update(studentMapstructMapper.toEntity(studentDto))) : null;

    }
}
