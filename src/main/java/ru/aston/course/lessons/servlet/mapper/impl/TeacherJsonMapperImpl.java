package ru.aston.course.lessons.servlet.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.course.lessons.servlet.dto.TeacherDto;
import ru.aston.course.lessons.servlet.mapper.TeacherJsonMapper;

import java.util.List;

public final class TeacherJsonMapperImpl implements TeacherJsonMapper {
    private final ObjectMapper mapper;

    public TeacherJsonMapperImpl() {
        this.mapper = new ObjectMapper();
    }

    public TeacherJsonMapperImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toJson(TeacherDto dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(List<TeacherDto> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public TeacherDto toDto(String json) {
        try {
            return mapper.readValue(json, TeacherDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
