package ru.aston.course.lessons.servlet.dto;

import java.sql.Timestamp;

public class IncomingLessonDto {
    private Long id;
    private Long idTeacher;
    private Timestamp date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

}
