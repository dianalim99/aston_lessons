package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.servlet.dto.IncomingTeacherDto;
import ru.aston.course.lessons.servlet.dto.OutgoingStudentDto;
import ru.aston.course.lessons.servlet.dto.OutgoingTeacherDto;

import java.util.List;

@Mapper
public interface TeacherDtoMapper {
    TeacherDtoMapper INSTANCE = Mappers.getMapper(TeacherDtoMapper.class);
    OutgoingTeacherDto toDto(TeacherEntity teacher);
    List<OutgoingTeacherDto> toDto(List<TeacherEntity> teachers);

    TeacherEntity toEntity(IncomingTeacherDto incomingTeacherDto);

}
