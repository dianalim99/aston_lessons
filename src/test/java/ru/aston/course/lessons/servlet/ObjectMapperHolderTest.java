package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperHolderTest {
    @Test
    public void testGetInstance() {
        ObjectMapper objectMapper1 = ObjectMapperHolder.getInstance();
        ObjectMapper objectMapper2 = ObjectMapperHolder.getInstance();

        assertNotNull(objectMapper1);
        assertNotNull(objectMapper2);
        assertSame(objectMapper1, objectMapper2);
    }

    @Test
    public void testSerializationFeature() {
        ObjectMapper objectMapper = ObjectMapperHolder.getInstance();

        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
    }

    @Test
    public void testDateFormat() {
        ObjectMapper objectMapper = ObjectMapperHolder.getInstance();

        SimpleDateFormat dateFormat = (SimpleDateFormat) objectMapper.getDateFormat();
        assertNotNull(dateFormat);
        assertEquals("yyyy-MM-dd HH:mm:ss", dateFormat.toPattern());
    }

}