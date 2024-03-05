package ru.aston.course.lessons.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.mapper.impl.StudentResultSetMapperImpl;
import ru.aston.course.lessons.servlet.mapper.impl.StudentJsonMapperImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class StudentResultSetMapperImplTest {
    @Mock
    ResultSet resultSet;
    StudentResultSetMapperImpl mapper;
    @BeforeEach
    public void setup() {
        mapper = new StudentResultSetMapperImpl();
    }
    @Test
    void testMapShouldReturnCorrectStudentEntity() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");

        StudentEntity studentEntity = mapper.map(resultSet);

        assertAll(
                () -> assertEquals(1L, studentEntity.getId()),
                () -> assertEquals("John", studentEntity.getFirstName()),
                () -> assertEquals("Doe", studentEntity.getLastName())
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