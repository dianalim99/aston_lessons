package ru.aston.course.lessons.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.repository.LessonEntityRepository;
import ru.aston.course.lessons.service.impl.LessonServiceImpl;
import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;
import ru.aston.course.lessons.servlet.mapper.LessonDtoMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private static LessonEntityRepository repository;

    @Mock
    private static LessonDtoMapper mapper;
    private static LessonService service;

    @BeforeEach
    void beforeEach() {
        service = new LessonServiceImpl(repository, mapper);
    }

    @Test
    void testFindByIdWhenIdValid() {
        Long id = 1L;
        LessonEntity entity = new LessonEntity();
        LessonDtoOut dto = new LessonDtoOut();
        when(repository.findById(id)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        LessonDtoOut actualDto = service.findById(id);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
        assertEquals(dto, actualDto);

    }

    @Test
    void testFindByIdWhenIdInvalid() {
        Long invalidId = 100L;
        when(repository.findById(invalidId)).thenReturn(null);
        when(mapper.toDto((LessonEntity) any())).thenReturn(null);

        LessonDtoOut actualDto = service.findById(invalidId);

        assertNull(actualDto);
        verify(repository, times(1)).findById(invalidId);
        verify(mapper, times(1)).toDto((LessonEntity) any());
    }
    @Test
    void testFindByIdWhenIdNull() {
        assertNull(service.findById(null));
    }

    @Test
    void testFindAll() {
        LessonEntity entity1 = new LessonEntity();
        LessonEntity entity2 = new LessonEntity();
        LessonDtoOut dto1 = new LessonDtoOut();
        LessonDtoOut dto2 = new LessonDtoOut();

        List<LessonEntity> entities = List.of(entity1, entity2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(mapper.toDto(entity2)).thenReturn(dto2);

        List<LessonDtoOut> actualDtos = service.findAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toDto(any(LessonEntity.class));
        assertEquals(2, actualDtos.size());
    }

    @Test
    void testSaveWhenDtoNotNull() {
        LessonDtoIn dtoIn = new LessonDtoIn();
        LessonEntity entity = new LessonEntity();
        LessonDtoOut dtoOut = new LessonDtoOut();

        when(mapper.toEntity(dtoIn)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dtoOut);

        service.save(dtoIn);

        verify(mapper, times(1)).toEntity(any(LessonDtoIn.class));
        verify(repository, times(1)).save(any());
        verify(mapper, times(1)).toDto(any(LessonEntity.class));
    }

    @Test
    void testSaveWhenDtoNull() {
        assertNull(service.save(null));
    }
    @Test
    void testUpdateWhenDtoAndIdNotNull() {
        LessonDtoIn dtoIn = new LessonDtoIn();
        dtoIn.setId(1L);
        LessonEntity entity = new LessonEntity();
        entity.setId(1L);
        LessonDtoOut dtoOut = new LessonDtoOut();
        dtoOut.setId(1L);

        when(mapper.toEntity(dtoIn)).thenReturn(entity);
        when(repository.update(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dtoOut);

        service.update(dtoIn);

        verify(mapper, times(1)).toEntity(dtoIn);
        verify(repository, times(1)).update(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    void testUpdateWhenDtoNotNullAndIdNull() {
        LessonDtoIn dtoIn = new LessonDtoIn();
        LessonEntity entity = new LessonEntity();
        LessonDtoOut dtoOut = new LessonDtoOut();

        when(mapper.toEntity(dtoIn)).thenReturn(entity);
        when(repository.update(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dtoOut);

        service.update(dtoIn);

        verify(mapper, times(1)).toEntity(dtoIn);
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