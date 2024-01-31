package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.model.StudentEntity;
import ru.aston.course.lessons.model.TeacherEntity;
import ru.aston.course.lessons.service.impl.StudentServiceImpl;
import ru.aston.course.lessons.service.impl.TeacherServiceImpl;
import ru.aston.course.lessons.servlet.dto.IncomingTeacherDto;
import ru.aston.course.lessons.servlet.dto.OutgoingStudentDto;
import ru.aston.course.lessons.servlet.dto.OutgoingTeacherDto;
import ru.aston.course.lessons.servlet.mapper.StudentDtoMapper;
import ru.aston.course.lessons.servlet.mapper.TeacherDtoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {
    private final TeacherServiceImpl teacherService = TeacherServiceImpl.getInstance();
    private final TeacherDtoMapper teacherDtoMapper = TeacherDtoMapper.INSTANCE;
    private final ObjectMapper objectMapper = ObjectMapperHolder.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teacherIdStr = req.getParameter("id");
        String json;
        if (teacherIdStr == null || teacherIdStr.isEmpty()) {
            List<TeacherEntity> teachers = teacherService.findAll();
            List<OutgoingTeacherDto> teacherDtos = teacherDtoMapper.toDto(teachers);
            json = objectMapper.writeValueAsString(teacherDtos);
        } else {
            long teacherId = Long.parseLong(teacherIdStr);
            TeacherEntity teacher = teacherService.findById(teacherId);
            if (teacher == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OutgoingTeacherDto teacherDto = teacherDtoMapper.toDto(teacher);
            json = objectMapper.writeValueAsString(teacherDto);
        }
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        IncomingTeacherDto teacherDto = objectMapper.readValue(req.getReader(), IncomingTeacherDto.class);
        TeacherEntity teacher = teacherDtoMapper.toEntity(teacherDto);
        OutgoingTeacherDto teacherDtoOut;
        TeacherEntity savedTeacher;
        if (teacher.getId() == null || teacher.getId() == 0) {
            savedTeacher = (teacherService.save(teacher));
        } else {
            savedTeacher = (teacherService.update(teacher));
        }
        teacherDtoOut = teacherDtoMapper.toDto(savedTeacher);
        json = objectMapper.writeValueAsString(teacherDtoOut);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teacherIdStr = req.getParameter("id");
        if (teacherIdStr == null || teacherIdStr.isEmpty()) {
            return;
        } else {
            long teacherId = Long.parseLong(teacherIdStr);
            boolean teacherDelete = teacherService.deleteById(teacherId);
            if (!teacherDelete) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
