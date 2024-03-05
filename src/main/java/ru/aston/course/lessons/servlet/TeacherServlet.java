package ru.aston.course.lessons.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.service.TeacherService;
import ru.aston.course.lessons.service.impl.TeacherServiceImpl;
import ru.aston.course.lessons.servlet.dto.TeacherDto;
import ru.aston.course.lessons.servlet.mapper.TeacherJsonMapper;
import ru.aston.course.lessons.servlet.mapper.impl.TeacherJsonMapperImpl;
import ru.aston.course.lessons.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {
    private final TeacherService service;
    private final TeacherJsonMapper jsonMapper;

    public TeacherServlet() {
        this.service = new TeacherServiceImpl();
        this.jsonMapper = new TeacherJsonMapperImpl();
    }
    public TeacherServlet(TeacherService service, TeacherJsonMapper jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teacherIdStr = req.getParameter("id");
        String json;
        if (teacherIdStr == null || teacherIdStr.isEmpty()) {
            List<TeacherDto> teacherDtos;
            teacherDtos= service.findAll();
            json = jsonMapper.toJson(teacherDtos);
        } else {

            TeacherDto teacherDto = null;
            try {
                long teacherId = Long.parseLong(teacherIdStr);
                teacherDto = service.findById(teacherId);

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (teacherDto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            json = jsonMapper.toJson(teacherDto);
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
            TeacherDto teacherDtoIn = jsonMapper.toDto(jsonBody);
            TeacherDto teacherDtoOut;
            if (teacherDtoIn.getId() == null || teacherDtoIn == null) {
                teacherDtoOut = (service.save(teacherDtoIn));
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                teacherDtoOut = (service.update(teacherDtoIn));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            json = jsonMapper.toJson(teacherDtoOut);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String teacherIdStr = req.getParameter("id");
        if (teacherIdStr == null || teacherIdStr.isEmpty()) {
            return;
        } else {
            try {
                long teacherId = Long.parseLong(teacherIdStr);
                boolean teacherDelete = service.deleteById(teacherId);
                if (!teacherDelete) {
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
