package ru.aston.course.lessons.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.course.lessons.service.StudentService;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentJsonMapper;
import ru.aston.course.lessons.util.ServletUtil;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudentServletTest {
    @Mock
    private StudentService service;
    @Mock
    private StudentJsonMapper jsonMapper;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private StudentServlet servlet;

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String PARAMETER_ID = "id";
    private static final String JSON_CHARACTER_ENCODING = "UTF-8";
    private static final String JSON_TEST = "test";
    private static final Long LONG_ID = 1L;

    @BeforeEach
    void setup() {
        servlet = new StudentServlet(service, jsonMapper);
    }

    @Test
    void testDoGetWithNoIdParameter() throws Exception {

        List<StudentDto> studentDtos = Arrays.asList(new StudentDto(), new StudentDto());
        PrintWriter mockWriter = mock(PrintWriter.class);
        when(request.getParameter(PARAMETER_ID)).thenReturn(null);
        when(service.findAll()).thenReturn(studentDtos);
        when(jsonMapper.toJson(studentDtos)).thenReturn(JSON_TEST);
        when(response.getWriter()).thenReturn(mockWriter);

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findAll();
        verify(jsonMapper, times(1)).toJson(studentDtos);
        verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
        verify(response, times(1)).setCharacterEncoding(JSON_CHARACTER_ENCODING);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(1)).getWriter();
        verify(mockWriter, times(1)).print(anyString());
        verify(response.getWriter(), times(1)).flush();
    }

    @Test
    void testDoGetWithIdParameter() throws Exception {
        StudentDto dto = new StudentDto();
        PrintWriter mockWriter = mock(PrintWriter.class);

        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.findById(LONG_ID)).thenReturn(dto);
        when(jsonMapper.toJson(dto)).thenReturn(JSON_TEST);
        when(response.getWriter()).thenReturn(mockWriter);

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findById(LONG_ID);
        verify(jsonMapper, times(1)).toJson(dto);
        verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
        verify(response, times(1)).setCharacterEncoding(JSON_CHARACTER_ENCODING);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(1)).getWriter();
        verify(mockWriter, times(1)).print(anyString());
        verify(response.getWriter(), times(1)).flush();
    }

    @Test
    void testDoGetWithNonexistentIdParameter() throws Exception {
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.findById(LONG_ID)).thenReturn(null);

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoGetWhenParameterInvalid() throws Exception{

        when(request.getParameter(PARAMETER_ID)).thenReturn("invalidId");

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoGetWhenEmptyList() throws Exception{
        List<StudentDto> studentDtos = new ArrayList<>();
        PrintWriter mockWriter = mock(PrintWriter.class);
        when(request.getParameter(PARAMETER_ID)).thenReturn(null);
        when(service.findAll()).thenReturn(studentDtos);
        when(jsonMapper.toJson(studentDtos)).thenReturn("");
        when(response.getWriter()).thenReturn(mockWriter);

        servlet.doGet(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).findAll();
        verify(jsonMapper, times(1)).toJson(studentDtos);
        verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
        verify(response, times(1)).setCharacterEncoding(JSON_CHARACTER_ENCODING);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(1)).getWriter();
        verify(mockWriter, times(1)).print("");
        verify(response.getWriter(), times(1)).flush();
    }

    @Test
    void testDoPostWithNullIdWhenBodyPresent() throws Exception {
        String jsonBody = "test";
        StudentDto dto = new StudentDto();
        StudentDto dtoSaved = new StudentDto();
        PrintWriter mockWriter = mock(PrintWriter.class);
        dtoSaved.setId(1L);

        try(MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonMapper.toDto(jsonBody)).thenReturn(dto);
            when(service.save(dto)).thenReturn(dtoSaved);
            when(jsonMapper.toJson(dtoSaved)).thenReturn(jsonBody);
            when(response.getWriter()).thenReturn(mockWriter);

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonMapper, times(1)).toDto(anyString());
            verify(service, times(1)).save(any(StudentDto.class));
            verify(jsonMapper, times(1)).toJson(any(StudentDto.class));
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setCharacterEncoding(JSON_CHARACTER_ENCODING);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
            verify(response, times(1)).getWriter();
            verify(mockWriter, times(1)).print(anyString());
            verify(response.getWriter(), times(1)).flush();
        }
    }

    @Test
    void testDoPostWithNullIdWhenBodyNotPresent() throws Exception {
        String jsonBody = "";
        try(MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoPostWithIdWhenBodyPresent() throws Exception {
        String jsonBody = "test";
        StudentDto dto = new StudentDto();
        StudentDto dtoUpdate = new StudentDto();
        PrintWriter mockWriter = mock(PrintWriter.class);
        dto.setId(1L);
        dtoUpdate.setId(1L);

        try(MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            when(jsonMapper.toDto(jsonBody)).thenReturn(dto);
            when(service.update(dto)).thenReturn(dtoUpdate);
            when(jsonMapper.toJson(dtoUpdate)).thenReturn(jsonBody);
            when(response.getWriter()).thenReturn(mockWriter);

            servlet.doPost(request, response);

            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(jsonMapper, times(1)).toDto(anyString());
            verify(service, times(1)).update(any(StudentDto.class));
            verify(jsonMapper, times(1)).toJson(any(StudentDto.class));
            verify(response, times(1)).setContentType(JSON_CONTENT_TYPE);
            verify(response, times(1)).setCharacterEncoding(JSON_CHARACTER_ENCODING);
            verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
            verify(response, times(1)).getWriter();
            verify(mockWriter, times(1)).print(anyString());
            verify(response.getWriter(), times(1)).flush();
        }
    }

    @Test
    void testDoPostWithIdWhenBodyNotPresent() throws Exception {
        String jsonBody = "";
        try(MockedStatic<ServletUtil> mockedStatic = mockStatic(ServletUtil.class)) {
            mockedStatic.when(() -> ServletUtil.getJsonBody(request)).thenReturn(jsonBody);
            servlet.doPost(request, response);
            mockedStatic.verify(() -> ServletUtil.getJsonBody(request), times(1));
            verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Test
    void testDoDeleteWithId() throws Exception{
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(true);
        servlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoDeleteWithInvalidId() throws Exception{
        when(request.getParameter(PARAMETER_ID)).thenReturn("invalidId");
        servlet.doDelete(request, response);
        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoDeleteWithNoFoundId() throws Exception{
        when(request.getParameter(PARAMETER_ID)).thenReturn(String.valueOf(LONG_ID));
        when(service.deleteById(LONG_ID)).thenReturn(false);
        servlet.doDelete(request, response);

        verify(request, times(1)).getParameter(PARAMETER_ID);
        verify(service, times(1)).deleteById(LONG_ID);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}