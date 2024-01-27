package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;

public class ObjectMapperHolder {
    private static final ObjectMapper INSTANCE = new ObjectMapper()
            .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

    private ObjectMapperHolder() {
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
