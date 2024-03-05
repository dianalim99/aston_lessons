package ru.aston.course.lessons.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.service.LessonService;
import ru.aston.course.lessons.service.impl.LessonServiceImpl;
import ru.aston.course.lessons.servlet.dto.LessonDtoIn;
import ru.aston.course.lessons.servlet.dto.LessonDtoOut;
import ru.aston.course.lessons.servlet.mapper.LessonJsonMapper;
import ru.aston.course.lessons.servlet.mapper.impl.LessonJsonMapperImpl;
import ru.aston.course.lessons.util.ServletUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {
    private final LessonService service;
    private final LessonJsonMapper jsonMapper;

    public LessonServlet() {
        this.service = new LessonServiceImpl();
        this.jsonMapper = new LessonJsonMapperImpl();
    }
    public LessonServlet(LessonService service, LessonJsonMapper jsonMapper) {
        this.service = service;
        this.jsonMapper = jsonMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdStr = req.getParameter("id");
        String json;
        if (lessonIdStr == null || lessonIdStr.isEmpty()) {
            List<LessonDtoOut> lessonDtos;
            lessonDtos= service.findAll();
            json = jsonMapper.toJson(lessonDtos);
        } else {

            LessonDtoOut lessonDto = null;
            try {
                long lessonId = Long.parseLong(lessonIdStr);
                lessonDto = service.findById(lessonId);

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            if (lessonDto == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            json = jsonMapper.toJson(lessonDto);
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
            LessonDtoIn lessonDtoIn = jsonMapper.toDto(jsonBody);
            LessonDtoOut lessonDtoOut;
            if (lessonDtoIn.getId() == null || lessonDtoIn == null) {
                lessonDtoOut = (service.save(lessonDtoIn));
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                lessonDtoOut = (service.update(lessonDtoIn));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
            json = jsonMapper.toJson(lessonDtoOut);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdStr = req.getParameter("id");
        if (lessonIdStr == null || lessonIdStr.isEmpty()) {
            return;
        } else {
            try {
                long lessonId = Long.parseLong(lessonIdStr);
                boolean lessonDelete = service.deleteById(lessonId);
                if (!lessonDelete) {
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
