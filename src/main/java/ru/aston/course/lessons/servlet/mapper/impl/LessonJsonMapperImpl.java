package ru.aston.course.lessons.servlet.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.course.lessons.servlet.ObjectMapperHolder;
import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;
import ru.aston.course.lessons.servlet.mapper.LessonJsonMapper;

import java.util.List;

public final class LessonJsonMapperImpl implements LessonJsonMapper {
    private final ObjectMapper mapper;

    public LessonJsonMapperImpl() {
        this.mapper = ObjectMapperHolder.getInstance();
    }

    public LessonJsonMapperImpl(ObjectMapperHolder mapper) {
        this.mapper = ObjectMapperHolder.getInstance();
    }

    @Override
    public String toJson(LessonDtoOut dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJson(List<LessonDtoOut> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public LessonDtoIn toDto(String json) {
        try {
            return mapper.readValue(json, LessonDtoIn.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
