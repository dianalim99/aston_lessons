package ru.aston.course.lessons.servlet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.service.StudentService;
import ru.aston.course.lessons.service.impl.StudentServiceImpl;
import ru.aston.course.lessons.servlet.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StudentDtoMapperTest {
    private static StudentDtoMapper mapper;
    @BeforeEach
    void beforeEach() {
        mapper = new StudentDtoMapperImpl();
    }

    @Test
    void testToDtoShouldReturnCorrectDto() {
        StudentEntity student = new StudentEntity();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");

        StudentDto dto = mapper.toDto(student);

        assertAll(
                () -> assertEquals(student.getId(), dto.getId()),
                () -> assertEquals(student.getFirstName(), dto.getFirstName()),
                () -> assertEquals(student.getLastName(), dto.getLastName())
        );
    }

    @Test
    void testToDtoShouldReturnNullForNullStudent() {
        StudentDto dto = mapper.toDto((StudentEntity) null);

        assertEquals(null, dto);
    }

    @Test
    void testToDtoListShouldReturnCorrectDtoList() {
        List<StudentEntity> students = new ArrayList<>();
        StudentEntity student1 = new StudentEntity();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        students.add(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        students.add(student2);

        List<StudentDto> dtoList = mapper.toDto(students);
        assertAll(
                () -> assertEquals(2, dtoList.size()),
                () -> assertEquals(student1.getId(), dtoList.get(0).getId()),
                () -> assertEquals(student1.getFirstName(), dtoList.get(0).getFirstName()),
                () -> assertEquals(student1.getLastName(), dtoList.get(0).getLastName()),
                () -> assertEquals(student2.getId(), dtoList.get(1).getId()),
                () -> assertEquals(student2.getFirstName(), dtoList.get(1).getFirstName()),
                () -> assertEquals(student2.getLastName(), dtoList.get(1).getLastName())

        );
    }

    @Test
    void testToDtoListShouldReturnNullForNullList() {
        List<StudentDto> dtoList = mapper.toDto((List<StudentEntity>) null);

        assertEquals(null, dtoList);
    }

    @Test
    void testToEntityShouldReturnCorrectEntity() {
        StudentDto dto = new StudentDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Doe");

        StudentEntity student = mapper.toEntity(dto);
        assertAll(
                () -> assertEquals(dto.getId(), student.getId()),
                () -> assertEquals(dto.getFirstName(), student.getFirstName()),
                () -> assertEquals(dto.getLastName(), student.getLastName())
        );

    }

    @Test
    void testToEntityShouldReturnNullForNullDto() {
        StudentEntity student = mapper.toEntity(null);

        assertEquals(null, student);
    }
}