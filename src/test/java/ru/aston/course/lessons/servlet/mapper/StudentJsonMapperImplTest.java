package ru.aston.course.lessons.servlet.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.impl.StudentJsonMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class StudentJsonMapperImplTest {

    private StudentJsonMapperImpl mapper;

    @BeforeEach
    public void setup() {
        mapper = new StudentJsonMapperImpl();
    }

    @Test
    void testToJsonWithSingleDto() throws JsonProcessingException {
        StudentDto dto = new StudentDto(1L, "John", "Doe");
        String expectedJsonString = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}";

        String jsonString = mapper.toJson(dto);

        assertEquals(expectedJsonString, jsonString);
    }

    @Test
    void testToJsonWithDtoList() throws JsonProcessingException {
        List<StudentDto> dtoList = new ArrayList<>();
        StudentDto dto1 = new StudentDto(1L, "John", "Doe");;
        dtoList.add(dto1);
        StudentDto dto2 = new StudentDto(2L, "Jane", "Smith");;
        dtoList.add(dto2);
        String expectedJsonString = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"},{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Smith\"}]";

        String jsonString = mapper.toJson(dtoList);

        assertEquals(expectedJsonString, jsonString);
    }

    @Test
    void testToDto() throws JsonProcessingException {
        String jsonString = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        StudentDto expectedDto = new StudentDto(1L, "John", "Doe");

        StudentDto dto = mapper.toDto(jsonString);

        assertTrue(dto.equals(expectedDto));
    }

}