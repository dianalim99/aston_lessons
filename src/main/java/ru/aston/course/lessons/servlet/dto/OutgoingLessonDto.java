package ru.aston.course.lessons.servlet.dto;

import java.sql.Timestamp;
import java.util.List;

public class OutgoingLessonDto {
    private Long id;
    private Long idTeacher;
    private Timestamp date;

    private List<OutgoingStudentDto> students;

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

    public List<OutgoingStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<OutgoingStudentDto> students) {
        this.students = students;
    }
}
