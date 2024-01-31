package ru.aston.course.lessons.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LessonEntity {
    private Long id;
    private Long idTeacher;
    private Timestamp date;
    private List<StudentEntity> students = new ArrayList<>();

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

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }
}
