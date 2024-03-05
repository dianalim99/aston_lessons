package ru.aston.course.lessons.servlet.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.impl.LessonJsonMapperImpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class LessonJsonMapperImplTest {

    private LessonJsonMapperImpl mapper;

    @BeforeEach
    public void setup() {
        mapper = new LessonJsonMapperImpl();
    }

    @Test
    void testToJsonWithSingleDto() throws JsonProcessingException {
        LessonDtoOut dto = new LessonDtoOut();
        dto.setId(1L);
        dto.setIdTeacher(2L);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        dto.setDate(date);
        List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto(1L, "John", "Doe"));
        students.add(new StudentDto(2L, "Jane", "Smith"));
        dto.setStudents(students);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(date);
        String expectedJsonString = "{\"id\":1,\"idTeacher\":2,\"date\":\"" + formattedTimestamp +
                "\",\"students\":[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}," +
                "{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Smith\"}]}";
        String json = mapper.toJson(dto);

        assertNotNull(json);
        assertEquals(expectedJsonString, json);
    }

    @Test
    void testToJsonWithDtoList() throws JsonProcessingException {
        LessonDtoOut dto1 = new LessonDtoOut();
        dto1.setId(1L);
        dto1.setIdTeacher(2L);
        Timestamp date = new Timestamp(System.currentTimeMillis());
        dto1.setDate(date);
        List<StudentDto> students = new ArrayList<>();
        students.add(new StudentDto(1L, "John", "Doe"));
        students.add(new StudentDto(2L, "Jane", "Smith"));
        dto1.setStudents(students);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(date);

        LessonDtoOut dto2 = new LessonDtoOut();
        dto2.setId(2L);
        dto2.setIdTeacher(3L);
        Timestamp date2 = new Timestamp(System.currentTimeMillis());
        dto2.setDate(date2);
        List<StudentDto> students2 = new ArrayList<>();
        students2.add(new StudentDto(2L, "Jane", "Smith"));
        students2.add(new StudentDto(3L, "Mark", "Grant"));
        dto2.setStudents(students2);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp2 = sdf2.format(date2);

        List<LessonDtoOut> dtoList = new ArrayList<>();
        dtoList.add(dto1);
        dtoList.add(dto2);

        String expectedJsonString = "[{\"id\":1,\"idTeacher\":2,\"date\":\"" + formattedTimestamp +
                "\",\"students\":[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}," +
                "{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Smith\"}]}," +
                "{\"id\":2,\"idTeacher\":3,\"date\":\"" + formattedTimestamp2 +
                "\",\"students\":[{\"id\":2,\"firstName\":\"Jane\",\"lastName\":\"Smith\"}," +
                "{\"id\":3,\"firstName\":\"Mark\",\"lastName\":\"Grant\"}]}]";

        String jsonString = mapper.toJson(dtoList);
        assertNotNull(jsonString);
        assertEquals(expectedJsonString, jsonString);

    }

}