package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;

import java.text.SimpleDateFormat;

public class ObjectMapperHolder {
    private static final ObjectMapper INSTANCE = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    private ObjectMapperHolder() {
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
