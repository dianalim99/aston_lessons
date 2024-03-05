package ru.aston.course.lessons.servlet.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class LessonDtoIn {
    private Long id;
    private Long idTeacher;
    private Timestamp date;
    private List<Long> studentIds;

    public LessonDtoIn() {
    }

    public LessonDtoIn(Long id, Long idTeacher, Timestamp date, List<Long> studentIds) {
        this.id = id;
        this.idTeacher = idTeacher;
        this.date = date;
        this.studentIds = studentIds;
    }

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


    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonDtoIn that = (LessonDtoIn) o;
        return Objects.equals(id, that.id) && Objects.equals(idTeacher, that.idTeacher) && Objects.equals(date, that.date) && Objects.equals(studentIds, that.studentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTeacher, date, studentIds);
    }
}
