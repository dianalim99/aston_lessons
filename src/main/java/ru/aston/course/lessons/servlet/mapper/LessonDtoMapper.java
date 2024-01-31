package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.servlet.dto.IncomingLessonDto;
import ru.aston.course.lessons.servlet.dto.OutgoingLessonDto;

import java.util.List;

@Mapper
public interface LessonDtoMapper {
    LessonDtoMapper INSTANCE = Mappers.getMapper(LessonDtoMapper.class);
    OutgoingLessonDto toDto(LessonEntity lesson);
    List<OutgoingLessonDto> toDto(List<LessonEntity> lessons);

    LessonEntity toEntity(IncomingLessonDto incomingLessonDto);

}
