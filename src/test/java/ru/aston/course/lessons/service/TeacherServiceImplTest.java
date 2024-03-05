package ru.aston.course.lessons.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.repository.TeacherEntityRepository;
import ru.aston.course.lessons.service.impl.TeacherServiceImpl;
import ru.aston.course.lessons.servlet.dto.TeacherDto;
import ru.aston.course.lessons.servlet.mapper.TeacherDtoMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {
    @Mock
    private static TeacherEntityRepository repository;

    @Mock
    private static TeacherDtoMapper mapper;
    private static TeacherService service;

    @BeforeEach
    void beforeEach() {
        service = new TeacherServiceImpl(repository, mapper);
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        TeacherEntity entity = new TeacherEntity();
        TeacherDto dto = new TeacherDto();
        when(repository.findById(id)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        TeacherDto actualDto = service.findById(id);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
        assertEquals(dto, actualDto);

    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long invalidId = 100L;
        when(repository.findById(invalidId)).thenReturn(null);
        when(mapper.toDto((TeacherEntity) any())).thenReturn(null);

        TeacherDto actualDto = service.findById(invalidId);

        assertNull(actualDto);
        verify(repository, times(1)).findById(invalidId);
        verify(mapper, times(1)).toDto((TeacherEntity) any());
    }
    @Test
    void testFindByIdWhenIdNull() {
        assertNull(service.findById(null));
    }

    @Test
    void testFindAll() {
        TeacherEntity entity1 = new TeacherEntity();
        TeacherEntity entity2 = new TeacherEntity();
        TeacherDto dto1 = new TeacherDto();
        TeacherDto dto2 = new TeacherDto();

        List<TeacherEntity> entities = List.of(entity1, entity2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(mapper.toDto(entity2)).thenReturn(dto2);

        List<TeacherDto> actualDtos = service.findAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(TeacherEntity.class));
        assertEquals(2, actualDtos.size());
    }

    @Test
    void testSaveWhenDtoNotNull() {
        TeacherDto dto = new TeacherDto();
        TeacherEntity entity = new TeacherEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        service.save(dto);

        verify(mapper, times(1)).toEntity(any(TeacherDto.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toDto(any(TeacherEntity.class));
    }
    @Test
    void testSaveWhenDtoNull() {
        assertNull(service.save(null));
    }
    @Test
    void testUpdateWhenDtoAndIdNotNull() {
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        TeacherEntity entity = new TeacherEntity();
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
        TeacherDto dto = new TeacherDto();
        TeacherEntity entity = new TeacherEntity();

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