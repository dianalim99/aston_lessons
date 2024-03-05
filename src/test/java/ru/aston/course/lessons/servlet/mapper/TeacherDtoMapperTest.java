package ru.aston.course.lessons.servlet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.servlet.dto.TeacherDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TeacherDtoMapperTest {
    private static TeacherDtoMapper mapper;
    @BeforeEach
    void beforeEach() {
        mapper = new TeacherDtoMapperImpl();
    }

    @Test
    void testToDtoShouldReturnCorrectDto() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setEmail("jd@test.com");
        teacher.setPassword("johndoe");

        TeacherDto dto = mapper.toDto(teacher);

        assertAll(
                () -> assertEquals(teacher.getId(), dto.getId()),
                () -> assertEquals(teacher.getFirstName(), dto.getFirstName()),
                () -> assertEquals(teacher.getLastName(), dto.getLastName()),
                () -> assertEquals(teacher.getEmail(), dto.getEmail()),
                () -> assertEquals(teacher.getPassword(), dto.getPassword())
        );
    }

    @Test
    void testToDtoShouldReturnNullForNullTeacher() {
        TeacherDto dto = mapper.toDto((TeacherEntity) null);

        assertEquals(null, dto);
    }

    @Test
    void testToDtoListShouldReturnCorrectDtoList() {
        List<TeacherEntity> teachers = new ArrayList<>();
        TeacherEntity teacher1 = new TeacherEntity();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");
        teacher1.setEmail("jd@test.com");
        teacher1.setPassword("johndoe");
        teachers.add(teacher1);

        TeacherEntity teacher2 = new TeacherEntity();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Smith");
        teacher2.setEmail("js@test.com");
        teacher2.setPassword("janesmith");
        teachers.add(teacher2);

        List<TeacherDto> dtoList = mapper.toDto(teachers);
        assertAll(
                () -> assertEquals(2, dtoList.size()),
                () -> assertEquals(teacher1.getId(), dtoList.get(0).getId()),
                () -> assertEquals(teacher1.getFirstName(), dtoList.get(0).getFirstName()),
                () -> assertEquals(teacher1.getLastName(), dtoList.get(0).getLastName()),
                () -> assertEquals(teacher2.getId(), dtoList.get(1).getId()),
                () -> assertEquals(teacher2.getFirstName(), dtoList.get(1).getFirstName()),
                () -> assertEquals(teacher2.getLastName(), dtoList.get(1).getLastName()),
                () -> assertEquals(teacher1.getEmail(), dtoList.get(0).getEmail()),
                () -> assertEquals(teacher1.getPassword(), dtoList.get(0).getPassword()),
                () -> assertEquals(teacher2.getEmail(), dtoList.get(1).getEmail()),
                () -> assertEquals(teacher2.getPassword(), dtoList.get(1).getPassword())

        );
    }

    @Test
    void testToDtoListShouldReturnNullForNullList() {
        List<TeacherDto> dtoList = mapper.toDto((List<TeacherEntity>) null);

        assertEquals(null, dtoList);
    }

    @Test
    void testToEntityShouldReturnCorrectEntity() {
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("jd@test.com");
        dto.setPassword("johndoe");

        TeacherEntity teacher = mapper.toEntity(dto);
        assertAll(
                () -> assertEquals(dto.getId(), teacher.getId()),
                () -> assertEquals(dto.getFirstName(), teacher.getFirstName()),
                () -> assertEquals(dto.getLastName(), teacher.getLastName()),
                () -> assertEquals(dto.getEmail(), teacher.getEmail()),
                () -> assertEquals(dto.getPassword(), teacher.getPassword())
        );

    }

    @Test
    void testToEntityShouldReturnNullForNullDto() {
        TeacherEntity teacher = mapper.toEntity(null);

        assertEquals(null, teacher);
    }
}