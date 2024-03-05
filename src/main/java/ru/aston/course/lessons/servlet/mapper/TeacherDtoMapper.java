package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.servlet.dto.TeacherDto;

import java.util.List;

@Mapper
public interface TeacherDtoMapper {
    TeacherDtoMapper INSTANCE = Mappers.getMapper(TeacherDtoMapper.class);
    TeacherDto toDto(TeacherEntity teacher);
    List <TeacherDto> toDto(List<TeacherEntity> teachers);
    TeacherEntity toEntity(TeacherDto teacherDto);



}
