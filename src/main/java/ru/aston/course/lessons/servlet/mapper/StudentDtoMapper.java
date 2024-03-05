package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.servlet.dto.StudentDto;

import java.util.List;

@Mapper
public interface StudentDtoMapper {
    StudentDtoMapper INSTANCE = Mappers.getMapper(StudentDtoMapper.class);

    StudentDto toDto(StudentEntity student);

    List<StudentDto> toDto(List<StudentEntity> students);

    StudentEntity toEntity(StudentDto studentDto);

}
