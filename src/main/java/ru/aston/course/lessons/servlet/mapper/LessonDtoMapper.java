package ru.aston.course.lessons.servlet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.servlet.dto.IncomingLessonDto;
import ru.aston.course.lessons.servlet.dto.OutgoingLessonDto;

import java.util.List;

@Mapper
public interface LessonDtoMapper {
    LessonDtoMapper INSTANCE = Mappers.getMapper(LessonDtoMapper.class);

    OutgoingLessonDto toDto(LessonEntity lesson);

    List<OutgoingLessonDto> toDto(List<LessonEntity> lessons);

    @Mapping(target = "students", source = "studentIds", qualifiedByName = "mapStudentIds")
    LessonEntity toEntity(IncomingLessonDto incomingLessonDto);

    @Named("mapStudentIds")
    default List<StudentEntity> mapStudentIds(List<Long> ids) {
        return ids
                .stream()
                .map(this::mapStudentId)
                .toList();
    }

    @Mapping(target = "id", source = ".")
    StudentEntity mapStudentId(Long id);
}
