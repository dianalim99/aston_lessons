package ru.aston.course.lessons.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.repository.StudentEntityRepository;
import ru.aston.course.lessons.service.impl.StudentServiceImpl;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentDtoMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private static StudentEntityRepository repository;

    @Mock
    private static StudentDtoMapper mapper;
    private static StudentService service;

    @BeforeEach
    void beforeEach() {
        service = new StudentServiceImpl(repository, mapper);
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        StudentEntity entity = new StudentEntity();
        StudentDto dto = new StudentDto();
        when(repository.findById(id)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        StudentDto actualDto = service.findById(id);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
        assertEquals(dto, actualDto);

    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long invalidId = 100L;
        when(repository.findById(invalidId)).thenReturn(null);
        when(mapper.toDto((StudentEntity) any())).thenReturn(null);

        StudentDto actualDto = service.findById(invalidId);

        assertNull(actualDto);
        verify(repository, times(1)).findById(invalidId);
        verify(mapper, times(1)).toDto((StudentEntity) any());
    }
    @Test
    void testFindByIdWhenIdNull() {
        assertNull(service.findById(null));
    }

    @Test
    void testFindAll() {
        StudentEntity entity1 = new StudentEntity();
        StudentEntity entity2 = new StudentEntity();
        StudentDto dto1 = new StudentDto();
        StudentDto dto2 = new StudentDto();

        List<StudentEntity> entities = List.of(entity1, entity2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(mapper.toDto(entity2)).thenReturn(dto2);

        List<StudentDto> actualDtos = service.findAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(StudentEntity.class));
        assertEquals(2, actualDtos.size());
    }

    @Test
    void testSaveWhenDtoNotNull() {
        StudentDto dto = new StudentDto();
        StudentEntity entity = new StudentEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        service.save(dto);

        verify(mapper, times(1)).toEntity(any(StudentDto.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toDto(any(StudentEntity.class));
    }
    @Test
    void testSaveWhenDtoNull() {
        assertNull(service.save(null));
    }
    @Test
    void testUpdateWhenDtoAndIdNotNull() {
        StudentDto dto = new StudentDto();
        dto.setId(1L);
        StudentEntity entity = new StudentEntity();
        entity.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.update(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        service.update(dto);

        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).update(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void testUpdateWhenDtoNotNullAndIdNull() {
        StudentDto dto = new StudentDto();
        StudentEntity entity = new StudentEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.update(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        service.update(dto);

        verify(mapper, times(1)).toEntity(dto);
        verify(repository, times(1)).update(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void testUpdateWhenDtoNull() {
        assertNull(service.update(null));
    }
    @Test
    void testDeleteWhenExistAndIdNotNull() {
        Long id = 1L;
        when(repository.deleteById(id)).thenReturn(true);

        boolean delete = service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
        assertTrue(delete);
    }

    @Test
    void testDeleteWhenExistAndIdNullThenFalse() {
        assertFalse(service.deleteById(null));
    }
}