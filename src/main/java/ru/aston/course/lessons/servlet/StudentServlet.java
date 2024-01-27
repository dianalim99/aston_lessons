package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.service.impl.StudentServiceImpl;
import ru.aston.course.lessons.servlet.dto.OutgoingStudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentDtoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private final StudentServiceImpl studentService = StudentServiceImpl.getInstance();
    private final StudentDtoMapper studentDtoMapper = StudentDtoMapper.INSTANCE;
    private final ObjectMapper objectMapper = ObjectMapperHolder.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdStr = req.getParameter("id");
        String json;
        if (studentIdStr == null || studentIdStr.isEmpty()) {
            List<StudentEntity> students = studentService.findAll();
            List<OutgoingStudentDto> studentDtos = studentDtoMapper.toDto(students);
            json = objectMapper.writeValueAsString(studentDtos);
        } else {
            long studentId = Long.parseLong(studentIdStr);
            StudentEntity student = studentService.findById(studentId);
            if (student == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OutgoingStudentDto studentDto = studentDtoMapper.toDto(student);
            json = objectMapper.writeValueAsString(studentDto);
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
