package ru.aston.course.lessons.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.service.StudentService;
import ru.aston.course.lessons.service.impl.StudentServiceImpl;
import ru.aston.course.lessons.servlet.dto.StudentDto;
import ru.aston.course.lessons.servlet.mapper.StudentJsonMapper;
import ru.aston.course.lessons.servlet.mapper.impl.StudentJsonMapperImpl;
import ru.aston.course.lessons.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private final StudentService service;
    private final StudentJsonMapper jsonMapper;

    public StudentServlet() {
        this.service = new StudentServiceImpl();
        this.jsonMapper = new StudentJsonMapperImpl();
    }
    public StudentServlet(StudentService service, StudentJsonMapper jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdStr = req.getParameter("id");
        String json;
        if (studentIdStr == null || studentIdStr.isEmpty()) {
            List<StudentDto> studentDtos;
            studentDtos= service.findAll();
            json = jsonMapper.toJson(studentDtos);
        } else {

            StudentDto studentDto = null;
            try {
                long studentId = Long.parseLong(studentIdStr);
                studentDto = service.findById(studentId);

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (studentDto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            json = jsonMapper.toJson(studentDto);
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json;
        String jsonBody = ServletUtil.getJsonBody(req);
        if (jsonBody.isEmpty() || jsonBody == null ){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            StudentDto studentDtoIn = jsonMapper.toDto(jsonBody);
            StudentDto studentDtoOut;
            if (studentDtoIn.getId() == null || studentDtoIn == null) {
                studentDtoOut = (service.save(studentDtoIn));
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                studentDtoOut = (service.update(studentDtoIn));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            json = jsonMapper.toJson(studentDtoOut);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentIdStr = req.getParameter("id");
        if (studentIdStr == null || studentIdStr.isEmpty()) {
            return;
        } else {
            try {
                long studentId = Long.parseLong(studentIdStr);
                boolean studentDelete = service.deleteById(studentId);
                if (!studentDelete) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
