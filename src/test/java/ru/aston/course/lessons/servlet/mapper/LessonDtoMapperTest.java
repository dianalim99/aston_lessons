package ru.aston.course.lessons.servlet.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LessonDtoMapperTest {
    private static LessonDtoMapper mapper;
    @BeforeEach
    void beforeEach() {
        mapper = new LessonDtoMapperImpl();
    }

    @Test
    void testToDtoShouldReturnCorrectDto() {
        LessonEntity lessonEntity = createLessonEntity();

        LessonDtoOut lessonDtoOut = mapper.toDto(lessonEntity);

        assertAll(
                () ->  assertNotNull(lessonDtoOut),
                () -> assertEquals(lessonEntity.getId(), lessonDtoOut.getId()),
                () -> assertEquals(lessonEntity.getIdTeacher(), lessonDtoOut.getIdTeacher()),
                () -> assertEquals(lessonEntity.getDate(), lessonDtoOut.getDate()),
                () -> assertEquals(lessonEntity.getStudents().size(), lessonDtoOut.getStudents().size())
        );
    }

    @Test
    void testToDtoShouldReturnNullForNullLesson() {
        LessonDtoOut dto = mapper.toDto((LessonEntity) null);
        assertEquals(null, dto);
    }

    @Test
    void testToDtoListShouldReturnCorrectDtoList() {
        List<LessonEntity> lessons = Arrays.asList(createLessonEntity(), createLessonEntity());

        List<LessonDtoOut> dtoList = mapper.toDto(lessons);
        assertAll(
                () ->  assertNotNull(dtoList),
                () -> assertEquals(lessons.size(), dtoList.size())
           );
        for (int i = 0; i < lessons.size(); i++) {
            LessonEntity lessonEntity = lessons.get(i);
            LessonDtoOut lessonDtoOut = dtoList.get(i);
           assertAll(
                   () -> assertNotNull(lessonDtoOut),
                   () -> assertEquals(lessonEntity.getId(), lessonDtoOut.getId()),
                   () -> assertEquals(lessonEntity.getIdTeacher(), lessonDtoOut.getIdTeacher()),
                   () -> assertEquals(lessonEntity.getDate(), lessonDtoOut.getDate()),
                    () -> assertEquals(lessonEntity.getStudents().size(), lessonDtoOut.getStudents().size())
           );
        }
    }

    @Test
    void testToDtoListShouldReturnNullForNullList() {
        List<LessonDtoOut> dtoList = mapper.toDto((List<LessonEntity>) null);

        assertEquals(null, dtoList);
    }

    @Test
    void testToEntityShouldReturnCorrectEntity() {
        LessonDtoIn dto = createLessonDtoIn();

        LessonEntity lesson = mapper.toEntity(dto);
        assertAll(
                () -> assertNotNull(lesson),
                () -> assertEquals(dto.getId(), lesson.getId()),
                () -> assertEquals(dto.getIdTeacher(), lesson.getIdTeacher()),
                () -> assertEquals(dto.getDate(), lesson.getDate()),
                () -> assertEquals(dto.getStudentIds().size(), lesson.getStudents().size())
        );
    }

    @Test
    void testToEntityShouldReturnNullForNullDto() {
        LessonEntity lesson = mapper.toEntity(null);

        assertEquals(null, lesson);
    }

    private LessonEntity createLessonEntity() {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(1L);
        lessonEntity.setIdTeacher(2L);
        lessonEntity.setDate(new Timestamp(System.currentTimeMillis()));
        List<StudentEntity> students = new ArrayList<>();
        StudentEntity student1 = new StudentEntity();
        student1.setId(101L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        students.add(student1);
        StudentEntity student2 = new StudentEntity();
        student2.setId(102L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        students.add(student2);
        lessonEntity.setStudents(students);
        return lessonEntity;
    }
    private LessonDtoIn createLessonDtoIn() {
        LessonDtoIn lessonDtoIn = new LessonDtoIn();
        lessonDtoIn.setId(1L);
        lessonDtoIn.setIdTeacher(2L);
        lessonDtoIn.setDate(new Timestamp(System.currentTimeMillis()));
        List<Long> studentIds = new ArrayList<>();
        studentIds.add(101L);
        studentIds.add(102L);
        lessonDtoIn.setStudentIds(studentIds);
        return lessonDtoIn;
    }
}