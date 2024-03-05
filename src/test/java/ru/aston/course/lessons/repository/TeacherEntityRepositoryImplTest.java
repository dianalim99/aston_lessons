package ru.aston.course.lessons.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.course.lessons.db.ConnectionManager;
import ru.aston.course.lessons.db.ConnectionManagerImpl;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.impl.TeacherEntityRepositoryImpl;
import ru.aston.course.lessons.repository.mapper.impl.TeacherResultSetMapperImpl;
import ru.aston.course.lessons.util.PropertiesUtil;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

//docker run -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres
@Testcontainers
class TeacherEntityRepositoryImplTest {
    private static final String TEST_DB_USERNAME = "test";
    private static final String TEST_DB_PASSWORD = "test";
    private static final String TEST_DB_NAME = "lesson";
    private static final String IMAGE_NAME = "postgres";
    private static final String TEST_MIGRATION = "db-migration.SQL";
    private static ConnectionManager connectionManager;
    private static TeacherEntityRepository repository;
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
        repository = new TeacherEntityRepositoryImpl(connectionManager, new TeacherResultSetMapperImpl());

    }

    @Test
    void testFindAll() {
        int expectedSize = 4;
        List<TeacherEntity> all = repository.findAll();
        assertEquals(expectedSize, all.size());
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        TeacherEntity expected = expectedTeacher();
        TeacherEntity actual = repository.findById(id);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long id = 25L;
        TeacherEntity actual = repository.findById(id);
        assertNull(actual);
    }


    @Test
    void testSave() {
        TeacherEntity expected =  newTeacher();
        TeacherEntity actual = repository.save(expected);
        assertAll(
                () -> assertNotNull(actual.getId()),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    void testUpdate() {
        TeacherEntity expected = expectedTeacher();
        expected.setFirstName("Тест");
        expected.setLastName("Тестов");
        TeacherEntity actual = repository.update(expected);
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }
    @Test
    void testDeleteById() {
        TeacherEntity teacher = deleteTeacher();
        repository.deleteById(teacher.getId());
        assertNull(repository.findById(teacher.getId()));
    }

    private static TeacherEntity expectedTeacher() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");
        teacher.setEmail("ivan@test.com");
        teacher.setPassword("ivan");
        return teacher;
    }
    private static TeacherEntity deleteTeacher() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId(4L);
        teacher.setFirstName("Дмитрий");
        teacher.setLastName("Дмитриев");
        teacher.setEmail("dima@test.com");
        teacher.setPassword("dima");
        return teacher;
    }

    private static TeacherEntity newTeacher() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setFirstName("Карим");
        teacher.setLastName("Каримов");
        teacher.setEmail("karim@test.com");
        teacher.setPassword("karim");
        return teacher;
    }

}