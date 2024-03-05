package ru.aston.course.lessons.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.mapper.impl.TeacherResultSetMapperImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherResultSetMapperImplTest {
    @Mock
    ResultSet resultSet;
    TeacherResultSetMapperImpl mapper;
    @BeforeEach
    public void setup() {
        mapper = new TeacherResultSetMapperImpl();
    }
    @Test
    void testMapShouldReturnCorrectTeacherEntity() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getString("email")).thenReturn("jd@test.com");
        when(resultSet.getString("password")).thenReturn("johndoe");

        TeacherEntity teacherEntity = mapper.map(resultSet);
        assertAll(
                () -> assertEquals(1L, teacherEntity.getId()),
                () -> assertEquals("John", teacherEntity.getFirstName()),
                () -> assertEquals("Doe", teacherEntity.getLastName()),
                () -> assertEquals("jd@test.com", teacherEntity.getEmail()),
                () -> assertEquals("johndoe", teacherEntity.getPassword())
        );

    }

    @Test
    void testMapShouldThrowRuntimeExceptionIfSQLExceptionOccurs() throws SQLException {
        when(resultSet.getLong("id")).thenThrow(new SQLException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mapper.map(resultSet);
        });

        assertEquals(SQLException.class, exception.getCause().getClass());
    }
}