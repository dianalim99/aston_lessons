package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.servlet.dto.OutgoingStudentDto;

import java.util.List;

@Mapper
public interface StudentDtoMapper {
    StudentDtoMapper INSTANCE = Mappers.getMapper(StudentDtoMapper.class);
    OutgoingStudentDto toDto(StudentEntity student);
    List <OutgoingStudentDto> toDto(List<StudentEntity> students);



}
