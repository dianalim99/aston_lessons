package ru.aston.course.lessons.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.aston.course.lessons.model.LessonEntity;
import ru.aston.course.lessons.service.impl.LessonServiceImpl;
import ru.aston.course.lessons.servlet.dto.IncomingLessonDto;
import ru.aston.course.lessons.servlet.dto.OutgoingLessonDto;
import ru.aston.course.lessons.servlet.mapper.LessonDtoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/lesson")
public class LessonServlet extends HttpServlet {
    private final LessonServiceImpl lessonService = LessonServiceImpl.getInstance();
    private final LessonDtoMapper lessonDtoMapper = LessonDtoMapper.INSTANCE;
    private final ObjectMapper objectMapper = ObjectMapperHolder.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdStr = req.getParameter("id");
        String json;
        if (lessonIdStr == null || lessonIdStr.isEmpty()) {
            List<LessonEntity> lessons = lessonService.findAll();
            List<OutgoingLessonDto> lessonDtos = lessonDtoMapper.toDto(lessons);
            json = objectMapper.writeValueAsString(lessonDtos);
        } else {
            long lessonId = Long.parseLong(lessonIdStr);
            LessonEntity lesson = lessonService.findById(lessonId);
            if (lesson == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OutgoingLessonDto lessonDto = lessonDtoMapper.toDto(lesson);
            json = objectMapper.writeValueAsString(lessonDto);
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
        IncomingLessonDto lessonDto = objectMapper.readValue(req.getReader(), IncomingLessonDto.class);
        LessonEntity lesson = lessonDtoMapper.toEntity(lessonDto);
        OutgoingLessonDto lessonDtoOut;
        LessonEntity savedLesson;
        if (lesson.getId() == null || lesson.getId() == 0) {
            savedLesson = (lessonService.save(lesson));
        } else {
            savedLesson = (lessonService.update(lesson));
        }
        lessonDtoOut = lessonDtoMapper.toDto(savedLesson);
        json = objectMapper.writeValueAsString(lessonDtoOut);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdStr = req.getParameter("id");
        if (lessonIdStr == null || lessonIdStr.isEmpty()) {
            return;
        } else {
            long lessonId = Long.parseLong(lessonIdStr);
            boolean lessonDelete = lessonService.deleteById(lessonId);
            if (!lessonDelete) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
