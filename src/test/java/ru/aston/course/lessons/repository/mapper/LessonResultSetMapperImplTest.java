package ru.aston.course.lessons.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.repository.mapper.impl.LessonResultSetMapperImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonResultSetMapperImplTest {
    @Mock
    ResultSet resultSet;
    LessonResultSetMapperImpl mapper;
    @BeforeEach
    public void setup() {
        mapper = new LessonResultSetMapperImpl();
    }
    @Test
    void testMapShouldReturnCorrectLessonEntity() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getLong("id_teacher")).thenReturn(2L);
        when(resultSet.getTimestamp("date")).thenReturn(new Timestamp(System.currentTimeMillis()));
        LessonEntity lessonEntity = mapper.map(resultSet);
        assertAll(
                () -> assertEquals(1L, lessonEntity.getId()),
                () -> assertEquals(2L, lessonEntity.getIdTeacher()),
                () -> assertNotNull(lessonEntity.getDate())
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