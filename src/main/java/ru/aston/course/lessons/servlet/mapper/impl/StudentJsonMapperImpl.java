package ru.aston.course.lessons.servlet.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentJsonMapper;

import java.io.BufferedReader;
import java.util.List;

public final class StudentJsonMapperImpl implements StudentJsonMapper {
    private final ObjectMapper mapper;

    public StudentJsonMapperImpl() {
        this.mapper = new ObjectMapper();
    }

    public StudentJsonMapperImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toJson(StudentDto dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(List<StudentDto> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public StudentDto toDto(String json) {
        try {
            return mapper.readValue(json, StudentDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
