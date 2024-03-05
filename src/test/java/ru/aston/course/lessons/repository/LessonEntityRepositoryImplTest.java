package ru.aston.course.lessons.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.impl.LessonEntityRepositoryImpl;
import ru.aston.course.lessons.repository.mapper.impl.LessonResultSetMapperImpl;
import ru.aston.course.lessons.repository.mapper.impl.StudentResultSetMapperImpl;
import ru.aston.course.lessons.util.PropertiesUtil;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

//docker run -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres
@Testcontainers
class LessonEntityRepositoryImplTest {
    private static final String TEST_DB_USERNAME = "test";
    private static final String TEST_DB_PASSWORD = "test";
    private static final String TEST_DB_NAME = "lesson";
    private static final String IMAGE_NAME = "postgres";
    private static final String TEST_MIGRATION = "db-migration.SQL";
    private static ConnectionManager connectionManager;
    private static LessonEntityRepository repository;
    @Container
    static final PostgreSQLContainer<?> CONTAINER =
            new PostgreSQLContainer<>(IMAGE_NAME)
                    .withDatabaseName(TEST_DB_NAME)
                    .withUsername(TEST_DB_USERNAME)
                    .withPassword(TEST_DB_PASSWORD)
                    .withInitScript(TEST_MIGRATION);

    @BeforeAll
    static void beforeAll() {
        Properties properties = PropertiesUtil.getProperties();
        properties.setProperty("db.url", CONTAINER.getJdbcUrl());
        properties.setProperty("db.username", CONTAINER.getUsername());
        properties.setProperty("db.password", CONTAINER.getPassword());
        connectionManager = new ConnectionManagerImpl(properties);
        repository = new LessonEntityRepositoryImpl(connectionManager, new LessonResultSetMapperImpl(), new StudentResultSetMapperImpl());

    }

    @Test
    void testFindAll() {
        int expectedSize = 2;
        List<LessonEntity> all = repository.findAll();
        assertEquals(expectedSize, all.size());
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        LessonEntity expected = expectedLesson();
        LessonEntity actual = repository.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long id = 25L;
        LessonEntity actual = repository.findById(id);
        assertNull(actual);
    }


    @Test
    void testSave() {
        LessonEntity expected =  newLesson();
        LessonEntity actual = repository.save(expected);
        assertAll(
                () -> assertNotNull(actual.getId()),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testUpdate() {
        LessonEntity expectedLesson = expectedLesson();
        expectedLesson.setIdTeacher(2L);
        expectedLesson.setDate(Timestamp.valueOf("2024-03-03 01:18:08"));
        StudentEntity student = new StudentEntity();
        student.setId(2L);
        student.setFirstName("Петр");
        student.setLastName("Петров");
        List<StudentEntity> students = Arrays.asList(student);
        expectedLesson.setStudents(students);

        LessonEntity actual = repository.update(expectedLesson);
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expectedLesson, actual)
        );
    }

    @Test
    void testDeleteById() {
        LessonEntity lesson = deleteLesson();
        repository.deleteById(lesson.getId());
        assertNull(repository.findById(lesson.getId()));
    }

    private static LessonEntity expectedLesson() {
        Long lessonId = 1L;
        LessonEntity expectedLesson = new LessonEntity();
        expectedLesson.setId(lessonId);
        expectedLesson.setIdTeacher(1L);
        expectedLesson.setDate(Timestamp.valueOf("2024-03-03 01:18:08"));

        StudentEntity student1 = new StudentEntity();
        student1.setId(1L);
        student1.setFirstName("Иван");
        student1.setLastName("Иванов");

        StudentEntity student2 = new StudentEntity();
        student2.setId(2L);
        student2.setFirstName("Петр");
        student2.setLastName("Петров");

        List<StudentEntity> students = Arrays.asList(student1, student2);
        expectedLesson.setStudents(students);
        return expectedLesson;
    }
    private static LessonEntity deleteLesson() {
        Long lessonId = 2L;
        LessonEntity deleteLesson = new LessonEntity();
        deleteLesson.setId(lessonId);
        deleteLesson.setIdTeacher(2L);
        deleteLesson.setDate(Timestamp.valueOf("2024-02-01 01:18:08"));

        StudentEntity student = new StudentEntity();
        student.setId(3L);
        student.setFirstName("Александр");
        student.setLastName("Александров");
        List<StudentEntity> students = Arrays.asList(student);
        deleteLesson.setStudents(students);
        return deleteLesson;
    }

    private static LessonEntity newLesson() {
        Long lessonId = 3L;
        LessonEntity newLesson = new LessonEntity();
        newLesson.setId(lessonId);
        newLesson.setIdTeacher(2L);
        newLesson.setDate(Timestamp.valueOf("2024-01-01 01:18:08"));

        StudentEntity student = new StudentEntity();
        student.setId(4L);
        student.setFirstName("Дмитрий");
        student.setLastName("Дмитриев");
        List<StudentEntity> students = Arrays.asList(student);
        newLesson.setStudents(students);
        return newLesson;
    }

}