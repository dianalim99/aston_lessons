package ru.aston.course.lessons.servlet.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.servlet.dto.TeacherDto;
import ru.aston.course.lessons.servlet.mapper.impl.TeacherJsonMapperImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

class TeacherJsonMapperImplTest {

    private TeacherJsonMapperImpl mapper;

    @BeforeEach
    public void setup() {
        mapper = new TeacherJsonMapperImpl();
    }

    @Test
    void testToJsonWithSingleDto() throws JsonProcessingException {
        TeacherDto dto = new TeacherDto(1L, "John", "Doe","jd@test.com", "johndoe");
        String expectedJsonString = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"jd@test.com\",\"password\":\"johndoe\"}";

        String jsonString = mapper.toJson(dto);

        assertEquals(expectedJsonString, jsonString);
    }

    @Test
    void testToJsonWithDtoList() throws JsonProcessingException {
        List<TeacherDto> dtoList = new ArrayList<>();
        TeacherDto dto1 = new TeacherDto(1L, "John", "Doe","jd@test.com", "johndoe");;
        dtoList.add(dto1);
        TeacherDto dto2 = new TeacherDto(2L, "Jane", "Smith","js@test.com","janesmith");;
        dtoList.add(dto2);
        String expectedJsonString = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"jd@test.com\",\"password\":\"johndoe\"}," +
                        "{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"js@test.com\",\"password\":\"janesmith\"}]";

        String jsonString = mapper.toJson(dtoList);

        assertEquals(expectedJsonString, jsonString);
    }

    @Test
    void testToDto() throws JsonProcessingException {
        String jsonString = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"jd@test.com\",\"password\":\"johndoe\"}";
        TeacherDto expectedDto = new TeacherDto(1L, "John", "Doe","jd@test.com", "johndoe");

        TeacherDto dto = mapper.toDto(jsonString);

        assertTrue(dto.equals(expectedDto));
    }

}