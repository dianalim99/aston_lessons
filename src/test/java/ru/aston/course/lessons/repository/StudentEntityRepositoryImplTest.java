package ru.aston.course.lessons.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.impl.StudentEntityRepositoryImpl;
import ru.aston.course.lessons.repository.mapper.impl.StudentResultSetMapperImpl;
import ru.aston.course.lessons.util.PropertiesUtil;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

//docker run -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres
@Testcontainers
class StudentEntityRepositoryImplTest {
    private static final String TEST_DB_USERNAME = "test";
    private static final String TEST_DB_PASSWORD = "test";
    private static final String TEST_DB_NAME = "lesson";
    private static final String IMAGE_NAME = "postgres";
    private static final String TEST_MIGRATION = "db-migration.SQL";
    private static ConnectionManager connectionManager;
    private static StudentEntityRepository repository;
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
        repository = new StudentEntityRepositoryImpl(connectionManager, new StudentResultSetMapperImpl());

    }

    @Test
    void testFindAll() {
        int expectedSize = 4;
        List<StudentEntity> all = repository.findAll();
        assertEquals(expectedSize, all.size());
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        StudentEntity expected = expectedStudent();
        StudentEntity actual = repository.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long id = 25L;
        StudentEntity actual = repository.findById(id);
        assertNull(actual);
    }


    @Test
    void testSave() {
        StudentEntity expected =  newStudent();
        StudentEntity actual = repository.save(expected);
        assertAll(
                () -> assertNotNull(actual.getId()),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testUpdate() {
        StudentEntity expected = expectedStudent();
        expected.setFirstName("Тест");
        expected.setLastName("Тестов");
        StudentEntity actual = repository.update(expected);
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }
    @Test
    void testDeleteById() {
        StudentEntity student = deleteStudent();
        repository.deleteById(student.getId());
        assertNull(repository.findById(student.getId()));
    }

    private static StudentEntity expectedStudent() {
        StudentEntity student = new StudentEntity();
        student.setId(1L);
        student.setFirstName("Иван");
        student.setLastName("Иванов");
        return student;
    }
    private static StudentEntity deleteStudent() {
        StudentEntity student = new StudentEntity();
        student.setId(4L);
        student.setFirstName("Дмитрий");
        student.setLastName("Дмитриев");
        return student;
    }

    private static StudentEntity newStudent() {
        StudentEntity student = new StudentEntity();
        student.setFirstName("Карим");
        student.setLastName("Каримов");
        return student;
    }

}